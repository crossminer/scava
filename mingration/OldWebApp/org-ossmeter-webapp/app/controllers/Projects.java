package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import model.QualityModel;
import model.User;
import models.ProjectImport;

import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.vcs.svn.SvnRepository;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import auth.MongoAuthenticator;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.mongodb.DB;

@With(LogAction.class)
public class Projects extends Controller {
	
	private static final String jsonUrl = play.Play.application().configuration().getString("ossmeter.api");
	
	public static List<Project> getProjects() throws Exception{
		final Promise<List<Project>> resultPromise = WS.url(jsonUrl+"/projects").get().map(
	            new Function<WSResponse, List<Project>>() {
	                public List<Project> apply(WSResponse response) {
	                	JsonNode json = response.asJson();
	                	ObjectMapper mapper = new ObjectMapper();

	                	try {
		                	return mapper.readValue(json.toString(), TypeFactory.defaultInstance().constructCollectionType(List.class, Project.class));
		                } catch (Exception e) {
		                	e.printStackTrace();
		                	return null;
		                }
	                }
	            }
	    ).recover(
		    new Function<Throwable, List<Project>>() {
		    	@Override
		    	public List<Project> apply(Throwable throwable) throws Throwable {
		    		throw throwable;
		    	}
		    }
	    );
		return resultPromise.get(120000);
	}
	
	public static Project getProject(String shortName){
		System.out.println("requesting: " + jsonUrl+"/projects/p/"+shortName);
		final Promise<Project> resultPromise = WS.url(jsonUrl+"/projects/p/"+shortName).get().map(
	            new Function<WSResponse, Project>() {
	                public Project apply(WSResponse response) {
	                	JsonNode json = response.asJson();

	                	// TODO : Check the result is valid

	                	// We're good! Let's show them the project.
	                    ObjectMapper mapper = new ObjectMapper();
	                	try {
	                    	Project project = mapper.readValue(json.toString(), Project.class);
	                    	return project;
	                    } catch (Exception e) {
	                    	e.printStackTrace();
							return null; // FIXME
	                    }
	                }
	            }
	    );
		return resultPromise.get(120000);
	}
	
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result projects() {
		try {
			// TODO: This should be limited
			User user = Application.getLocalUser(session());
			List<model.Project> analysedProjects = new ArrayList<>();
			List<model.Project> otherProjects = new ArrayList<>();
		    DB db = MongoAuthenticator.getUsersDb();
            model.Users users = new model.Users(db);
            
            if(user.isAdmin()){
            	for (model.Project p : users.getProjects()) {
            		if(p.getAnalysed() == false){
            			otherProjects.add(p);
            		}
               	}
            }

        	for (model.Project p : users.getProjects().findAnalysed()) {
        		analysedProjects.add(p);
           	}
			
	      
			db.getMongo().close();

		    return ok(views.html.projects.projects.render(analysedProjects, otherProjects,MongoAuthenticator.getStatistics()));
			
		} catch (Exception e) {
			e.printStackTrace();
			flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");//TODO move to Messages.
			return redirect(routes.Application.index());
		}
	}
	
	// @Restrict(@Group(MongoAuthenticator.USER_ROLE))
	// public static Result viewTmp(String id, String qm, boolean summary) {

	// 	System.err.println("selected qm:" + qm);

	// 	Project project = getProject(id);
	// 	if (project == null) {
	// 		flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");//TODO move to Messages.
	// 		return redirect(routes.Application.index());
	// 	}

	// 	model.Project iProject = MongoAuthenticator.findProjectById(project.getShortName());

	// 	QualityModel qualityModel = Application.getQualityModelById(qm);
	// 	if (qualityModel == null) qualityModel = Application.getInformationSourceModel();

	// 	return ok(views.html.projects.view_project.render(project, iProject, qualityModel, summary));
	// }

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result view(String id) {
		return view(id, true);
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result viewDetail(String id) {
		return view(id, false);
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result view(String id, boolean summary) {
		QualityModel qm = null;
		User user = Application.getLocalUser(session());
		
		if (user == null || user.getSelectedQualityModel() == null) {
			qm = MongoAuthenticator.getPlatformQualityModel("info");
		} else {
			String selected = user.getSelectedQualityModel();
			if (selected.equals("info") || selected.equals("quality")) {
				qm = MongoAuthenticator.getPlatformQualityModel(selected);
			} else if(selected.equals("custom")) {	
				qm = user.getQualityModel();	
			} else {
				qm = MongoAuthenticator.getPlatformQualityModel("info");
			}
		}
		
		Project project = getProject(id);
		boolean isAdmin = false;
		if(user.isAdmin()){
			isAdmin = true;
		}
		
		model.Project iProject = MongoAuthenticator.findProjectById(project.getShortName());

		return ok(views.html.projects.view_project.render(project, iProject, qm, summary, isAdmin));
	}
	
	// @Restrict(@Group(MongoAuthenticator.USER_ROLE))
	// public static Result viewAspect(String projectId, String qmId, String aspectId, boolean summary) {
	// 	if (aspectId.equals("null")) aspectId = qmId; // Workaround for routing issue

	// 	Project project = getProject(projectId);

	// 	if (project == null) {
	// 		flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");//TODO move to Messages.
	// 		return redirect(routes.Application.index());
	// 	}

	// 	model.Project iProject = MongoAuthenticator.findProjectById(project.getShortName());

	// 	QualityModel qm = Application.getQualityModelById(qmId);
	// 	if (qm == null) qm = Application.getInformationSourceModel();

	// 	// Lookup aspect
	// 	// QualityAspect aspect = findAspect(qm, aspectId);

	// 	return ok(views.html.projects.view_project.render(project, iProject, qm, summary));
	// }

	// protected static QualityAspect findAspect(QualityAspect aspect, String aspectId) {

	// 	if (aspect.id.equals(aspectId)) {
	// 		return aspect;
	// 	}

	// 	for (QualityAspect a : aspect.aspects) {
	// 		QualityAspect as = findAspect(a, aspectId);
	// 		if (as != null) return as;
	// 	}

	// 	return null;
	// }
	
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result create() {
		return ok(views.html.projects.form.render(form(Project.class), form(ProjectImport.class)));
	}
	
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result createProject() {

		// Project project = new Project();
		// project.setName("hello");

		// project.getVcsRepositories().add(new SvnRepository());
		// project.getVcsRepositories().add(new GitRepository());

		// Form<Project> form = form(Project.class);
		// form.fill(project);

		// FIXME: Cannot get the form working with POJOs. Just translate
		// Jimi's JSON into a Project object and send it.
		// return ok(views.html.projects.createProject.render(form));
		return ok(views.html.projects.addProjectNew.render(new DynamicForm()));
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result postCreateProject() {

		// TODO: Do a quick lookup to check that the project hasn't 
		// already been registered

		final DynamicForm form = form().bindFromRequest();

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode project = mapper.createObjectNode();
		project.put("name", form.get("name"));
		project.put("description", form.get("description"));
		project.put("homepage", form.get("homepage"));

		ArrayNode repos = mapper.createArrayNode();
		ArrayNode btss = mapper.createArrayNode();
		ArrayNode ccs = mapper.createArrayNode();

		project.put("vcs", repos);
		project.put("communication_channels", ccs);
		project.put("bts", btss);

		// FIXME: Horribly hacky (Play seemingly can't handle the fact that VcsRepository is abstract - not 
		// sure how to tell it to use the _type field to select the class to instantiate)
		for (String key : form.data().keySet()) {
			System.out.println(key + "->" + form.data().get(key));
			if (key.startsWith("vcsRepositories[")) {
				ObjectNode repo = mapper.createObjectNode();

				System.out.println(key.split("\\[")[1]);
				int id = Integer.valueOf(key.split("\\[")[1].substring(0, 1));

				// Only check types. Assume all other fields exist
				if (key.contains("]._type")) {
					String rType = form.data().get(key);
					if ("GIT".equals(rType)) {
						repo.put("type", "git");
					} else if("SVN".equals(rType)) {
						repo.put("type", "svn");
					} else {
						// FIXME: This is an error
						System.err.println("Unexpected repo type: " + rType);
						continue;
					}

					System.out.println("vcsRepositories["+id+"].url");
					repo.put("url", form.data().get("vcsRepositories["+id+"].url"));
					repos.add(repo);
				} 
			} else if (key.startsWith("communicationChannels[")) {
				int id = Integer.valueOf(key.split("\\[")[1].substring(0, 1));
				ObjectNode cc = mapper.createObjectNode();

				// Only check types. Assume all other fields exist
				if (key.contains("]._type")) {
					String cType = form.data().get(key);
					if ("NNTP".equals(cType)) {
						cc.put("type", "nntp");
						cc.put("name", "NNTP");
					} else {
						// FIXME: This is an error
						System.err.println("Unexpected cc type: " + cType);
						continue;						
					}

					// Common properties
					cc.put("url", form.data().get("communicationChannels["+id+"].url"));
					ccs.add(cc);
				}

			} else if (key.startsWith("bugTrackingSystems[")) {
				int id = Integer.valueOf(key.split("\\[")[1].substring(0, 1));
				ObjectNode bts = mapper.createObjectNode();

				if (key.contains("]._type")) {
					String bType = form.data().get(key);
					if ("BUGZILLA".equals(bType)) {
						bts.put("type", "bugzilla");
						bts.put("product", form.data().get("bugTrackingSystems["+id+"].product"));
						bts.put("component", form.data().get("bugTrackingSystems["+id+"].component"));
					// } else if ("JIRA".equals(bType)) {
					// 	JiraBugTrackingSystem b = new JiraBugTrackingSystem();
					// 	b.setProject(form.data().get("bugTrackingSystems["+id+"].project"));
					// 	bts = b;
					} else if ("SOURCEFORGE".equals(bType)) {
						bts.put("type", "sourceforge");
					} else if ("REDMINE".equals(bType)) {
						bts.put("type", "redmine");
						bts.put("name", form.data().get("bugTrackingSystems["+id+"].name"));
						bts.put("project", form.data().get("bugTrackingSystems["+id+"].project"));
					} else {
						// FIXME: This is an error
						System.err.println("Unexpected bts type: " + bType);
						continue;							
					}

					// Common properties
					bts.put("url", form.data().get("bugTrackingSystems["+id+"].url"));
					btss.add(bts);
				}
			}
		}

		System.out.println(project.toString());
	
		final Promise<Result> resultPromise = WS.url(play.Play.application().configuration().getString("ossmeter.api") + "/projects/create").post(project.toString())
			.map(new Function<WSResponse, Result>() {
				public Result apply(WSResponse response) {
					if (response.getStatus() == 400) {
						flash(Application.FLASH_ERROR_KEY, "Looks like there's been a problem. TODO: add error message here");
						return badRequest(views.html.projects.addProjectNew.render(form));
					} else if (response.getStatus() == 500) {
						flash(Application.FLASH_ERROR_KEY, "Looks like there's been a problem with our servers. We're looking into it.");
						return badRequest(views.html.projects.addProjectNew.render(form));
					}

                	try {
                		// Attach the project to the user
						final model.User localUser = Application.getLocalUser(session());
						
						// Get response
						JsonNode json = response.asJson();	

						ObjectMapper mapper = new ObjectMapper();
                    	Project project = mapper.readValue(json.toString(), Project.class);
                    	
                    	model.Project mProject = new model.Project();
                    	mProject.setName(project.getName());
                    	mProject.setId(project.getShortName());
                    	mProject.setDescription(project.getDescription());
                    	mProject.setCreatedBy(localUser.getEmail());

                    	MongoAuthenticator.assignProjectToUser(localUser, mProject);
                    } catch (Exception e) {
                    	e.printStackTrace();
                    }

					flash(Application.FLASH_MESSAGE_KEY, "Success! Your project has been registered. Check again in a few days to see the analysis.");
					return redirect(routes.Projects.projects());
				}
			});
		return resultPromise.get(120000);	
	}	
	
	public static SvnRepository createSvnRepository(DynamicForm postData) {
		SvnRepository svnRepos = new SvnRepository();
		svnRepos.setUrl(postData.get("vcs_url"));
		return svnRepos;
	}
	

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result importProject() {
	    final Form<ProjectImport> form = form(ProjectImport.class).bindFromRequest();
	    
	    if (form.hasErrors()) {
	    	flash(Application.FLASH_ERROR_KEY, "Invalid URL.");
	    	return badRequest(views.html.projects.form.render(form(Project.class), form));
	    }

		ProjectImport imp = form.get();
	    System.out.println(imp.url);

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("url", imp.url);


		final Promise<Result> resultPromise = WS.url(play.Play.application().configuration().getString("ossmeter.api") + "/projects/import").post(node)
				.map(new Function<WSResponse, Result>() {
					public Result apply(WSResponse response) {
						
						System.err.println("response body: " + response.getBody());

						JsonNode node = response.asJson();

						System.out.println(node);

						if (node.has("status") && "error".equals(node.get("status").asText())) {
							flash(Application.FLASH_ERROR_KEY, node.get("msg").asText());
							return badRequest(views.html.projects.form.render(form(Project.class), form));
						}

						// TODO check for errors, etc.
						ObjectMapper mapper = new ObjectMapper();

						try {
							// Attach the project to the user
							final model.User localUser = Application.getLocalUser(session());
						
							Project project = mapper.readValue(node.toString(), Project.class);
	                        // project.id = node.get("shortName").asLong();
	                        // project.name = node.get("name").asText();
	                        // project.shortName = node.get("shortName").asText();
	                        // project.url = node.get("url").asText();
	                        // project.desc = node.get("desc").asText();

							model.Project mProject = new model.Project();
		                	mProject.setName(project.getName());
		                	mProject.setId(project.getShortName());
		                	mProject.setDescription(project.getDescription());
		                	mProject.setCreatedBy(localUser.getEmail());

		                	// Insert (only if it doesn't exist already)
							MongoAuthenticator.assignProjectToUser(localUser, mProject);

							return redirect(routes.Projects.view(project.getShortName()));//views.html.projects.view_item.render(project));
							// return view(project.shortName);
						} catch (Exception e) {
							e.printStackTrace(); //FIXME: handle better
							return internalServerError(e.getMessage());
						}
					}
				});
		return resultPromise.get(120000);	
	}
}
