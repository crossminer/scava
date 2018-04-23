import static play.mvc.Results.internalServerError;
import static play.mvc.Results.notFound;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.Project;
import model.Statistics;
import model.Users;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Call;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import auth.MongoAuthenticator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;
import com.mongodb.DB;

import controllers.routes;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		// Configure authentication server
		PlayAuthenticate.setResolver(new Resolver() {

			@Override
			public Call login() {
				// Your login page
				return routes.Application.login();
			}

			@Override
			public Call afterAuth() {
				// The user will be redirected to this page after authentication
				// if no original URL was saved
				return routes.Application.index();
			}

			@Override
			public Call afterLogout() {
				return routes.Application.index();
			}

			@Override
			public Call auth(final String provider) {
				// You can provide your own authentication implementation,
				// however the default should be sufficient for most cases
				return com.feth.play.module.pa.controllers.routes.Authenticate
						.authenticate(provider);
			}

			@Override
			public Call askMerge() {
				return routes.Account.askMerge();
			}

			@Override
			public Call askLink() {
				return routes.Account.askLink();
			}

			@Override
			public Call onException(final AuthException e) {
				if (e instanceof AccessDeniedException) {
					return routes.Signup
							.oAuthDenied(((AccessDeniedException) e)
									.getProviderKey());
				}

				// more custom problem handling here...
				return super.onException(e);
			}
		});

		// Start cron jobs
		Akka.system().scheduler().schedule(
			Duration.create(1, TimeUnit.SECONDS), // Initial delay
			Duration.create(1, TimeUnit.DAYS), // Frequency
			new Runnable() {
				public void run() {
					System.out.println("scheduled task is running :)");

					try {
						final DB db = MongoAuthenticator.getUsersDb();
						final Users users = new Users(db);

						// Iterate all projects TODO:Paging
						Promise<List<Project>> projects = WS.url(play.Play.application().configuration().getString("ossmeter.api") + "/projects?size=300").get().map( 
							new Function<WSResponse, List<Project>>() {

								public List<Project> apply(WSResponse response) {
									ArrayNode pList = (ArrayNode)response.asJson();

									List<Project> ps = new ArrayList<>();
									ObjectMapper mapper = new ObjectMapper();

									Statistics stats = new Statistics();
									stats.setDate(new Date());


									int vcs = 0, bts = 0, cc = 0;

									for (JsonNode p : pList) {
										ObjectNode node = ((ObjectNode)p);
										try {
											String pId = node.get("shortName").asText();
											
											// Add if it doesn't exist
											Project proj = users.getProjects().findOneByIdentifier(pId);
											if (proj == null){
												proj = new Project();
												proj.setId(pId);
												proj.setName(node.get("name").asText());
												users.getProjects().add(proj);	
											}

											// Update details
											if (node.has("analysed")) {
												proj.setAnalysed(node.get("analysed").asBoolean());
											} else {
												proj.setAnalysed(false);
											}
											if (node.has("description")) proj.setDescription(node.get("description").asText());
											users.getProjects().sync();

											// Now do info source stats
											vcs += ((ArrayNode)node.get("vcsRepositories")).size();
											cc += ((ArrayNode)node.get("communicationChannels")).size();
											bts += ((ArrayNode)node.get("bugTrackingSystems")).size();
										
											// Now do bugs, messages, lines


										} catch (Exception e) {
											e.printStackTrace();
										}
									}

									stats.setNumberOfVcsRepositories(vcs);
									stats.setNumberOfBugTrackers(bts);
									stats.setNumberOfCommunicationChannels(cc);
									stats.setNumberOfProjects((int)users.getProjects().getDbCollection().count());
									stats.setNumberOfUsers((int)users.getUsers().getDbCollection().count());

									users.getStatistics().add(stats);
									users.getStatistics().sync();

									db.getMongo().close();
									return ps;
								}
							}
						);

						//

					} catch (Exception e) {
						e.printStackTrace();
						
					} 

				}	
			},
			Akka.system().dispatcher()
			);
	}

	@Override
	public Promise<Result> onError(RequestHeader request, Throwable t) {
		DB db = MongoAuthenticator.getUsersDb();
    	Users users = new Users(db);

    	model.Error e = new model.Error();
    	e.setDate(new java.util.Date());
    	e.setUri(request.uri());
    	e.setRemoteAddress(request.remoteAddress());
    	e.setMethod(request.method());

    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	t.printStackTrace(pw);
    	e.setMessage(sw.toString());

    	users.getErrors().add(e);
    	users.getErrors().sync();
    	db.getMongo().close();

		return Promise.<Result>pure(
			internalServerError(views.html.error.render())
		);
	}

	@Override
	public Promise<Result> onHandlerNotFound(RequestHeader request) {
		return Promise.<Result>pure(
			notFound(views.html.notFound404.render())
		);
	}
}