package controllers;

import java.util.*;
import java.io.*;

import model.*;
import model.Users;
import models.*;
import model.QualityModel;
import play.Routes;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import play.mvc.Result;
import play.mvc.Http.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import views.html.*;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import auth.MongoAuthenticator;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;

import org.apache.commons.mail.*;

import play.libs.ws.*;
import play.libs.F.Function;
import play.libs.F.Promise;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Admin extends Controller {

	public static void logRequest(Request request) {
		final User localUser = Application.getLocalUser(session());

		if (request.uri().startsWith("/assets")) return;
		if (request.uri().startsWith("/api")) return;

		System.out.println(request.cookies());
	    System.out.println(request.host());
	    System.out.println("username: " + request.username());
	    System.out.println(request.uri());
	    System.out.println("---");
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result index() {
		final User localUser = Application.getLocalUser(session());
		return ok(views.html.admin.index.render(localUser));
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result requests() {

		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

		List<InvitationRequest> invites = new ArrayList<>();
        Iterator<InvitationRequest> it = users.getInvites().iterator();
        while (it.hasNext()) {
        	invites.add(it.next());
        }

		return ok(views.html.admin.requests.render(invites));
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result status() {
		return ok(views.html.admin.status.render());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result errors() {
		return ok(views.html.admin.errors.render());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result offerInvite(String email) {

		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        InvitationRequest inv = users.getInvites().findOneByEmail(email);
        if (inv == null) {
        	db.getMongo().close();
			flash(Application.FLASH_ERROR_KEY, "Something went wrong. DB lookup for email failed.");
			return redirect(routes.Admin.requests());        	
        }

        // TODO: Send email
        try {
	        HtmlEmail e = new HtmlEmail();
			e.addTo(email);
			// e.addTo("jamesrobertwilliams@gmail.com");
			e.setFrom(play.Play.application().configuration().getString("smtp.user"), "OSSMETER");
			e.setSubject("OSSMETER Invitation");
			e.setHtmlMsg(views.html.account.email.invitation.render(play.Play.application().configuration().getString("application.domain"), 
				routes.Invitation.acceptInvitation(inv.getToken()).toString()).body());
			e.setTextMsg(views.txt.account.email.invitation.render(play.Play.application().configuration().getString("application.domain"), 
				routes.Invitation.acceptInvitation(inv.getToken()).toString()).body());

			e.setHostName(play.Play.application().configuration().getString("smtp.host"));
			e.setSmtpPort(play.Play.application().configuration().getInt("smtp.port"));
			e.setAuthentication(play.Play.application().configuration().getString("smtp.user"), play.Play.application().configuration().getString("smtp.password"));
			e.setStartTLSEnabled(play.Play.application().configuration().getBoolean("smtp.ssl"));
			
			e.send();
		} catch (Exception e) {
			play.Logger.error("Unable to send invite.", e);
			db.getMongo().close();
			flash(Application.FLASH_ERROR_KEY, "Unable to send invite. " + e.getMessage());
			return redirect(routes.Admin.requests());
		}

        inv.setStatus("SENT");
        inv.setOfferedAt(new Date());
        users.getInvites().sync();
        db.getMongo().close();

		flash(Application.FLASH_MESSAGE_KEY, "Invite sent: " + routes.Invitation.acceptInvitation(inv.getToken()));
		return redirect(routes.Admin.requests());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result deleteInvite(String email) {
		
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        InvitationRequest inv = users.getInvites().findOneByEmail(email);

        if (inv != null) {
        	users.getInvites().remove(inv);
        }

        users.getInvites().sync();
        db.getMongo().close();

		flash(Application.FLASH_MESSAGE_KEY, "Invite deleted.");
		return redirect(routes.Admin.requests());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result getWebAppErrors() {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

		final DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode res = mapper.createArrayNode();
        for (model.Error e :  users.getErrors()){
        	ObjectNode entry = mapper.createObjectNode();
        	entry.put("Date", dtf.format(e.getDate()));
        	entry.put("URI", e.getUri());
        	entry.put("Method", e.getMethod());
        	entry.put("Remote Address", e.getRemoteAddress());
        	entry.put("Message", e.getMessage());
        	res.add(entry);
        }
        db.getMongo().close();
        return ok(res.toString()).as("application/json");
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result getUsagePlot(String email) {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        Iterable<Log> logs = users.getLogs().findByUser(email);

        // TODO: map to MetVis
        String result = "{\"status\" : \"unimplemented\"}";
        ObjectMapper mapper = new ObjectMapper();

		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		final DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        ObjectNode vis = mapper.createObjectNode();
        vis.put("id", "user-stats");
        vis.put("name", "User stats for " + email);
        vis.put("description", "The activity of the user " + email);
        vis.put("type", "BarChart");
        vis.put("timeSeries", true);
        vis.put("x", "Date");
        vis.put("y", "Quantity");

        ArrayNode data = mapper.createArrayNode();
        vis.put("datatable", data);

        for (Log log : users.getLogs().findByUser(email)) {
        	ObjectNode entry = mapper.createObjectNode();
        	entry.put("Date",  df.format(log.getDate()));
        	entry.put("Datetime",  dtf.format(log.getDate()));
        	entry.put("Quantity", 1);
        	entry.put("URI", log.getUri());
        	entry.put("User", log.getUser());
        	data.add(entry);
        }	

        db.getMongo().close();
        return ok(vis.toString()).as("application/json");
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result viewMailingList() {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        List<MailingListItem> members = new ArrayList<MailingListItem>();

        for (MailingListItem m : users.getMailingList()) {
        	members.add(m);
        }

        db.getMongo().close();

        return ok(views.html.admin.mailinglist.render(members));
	}

	/**
	*
	*	@param type: either ALL, ANON, REGISTERED
	*/
	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result getPageViewPlot(String type) {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        ObjectMapper mapper = new ObjectMapper();

		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		final DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        ObjectNode vis = mapper.createObjectNode();
        vis.put("id", "page-views");
        vis.put("name", "Page Views");
        vis.put("description", "The page views over time");
        vis.put("type", "BarChart");
        vis.put("timeSeries", true);
        vis.put("x", "Date");
        vis.put("y", "Quantity");

        ArrayNode data = mapper.createArrayNode();
        vis.put("datatable", data);

        DBObject query = new BasicDBObject();

        for (Log log : users.getLogs()) {

        	if (type.equals("ANON") && !log.getUser().equals("anonymous")) continue;
        	if (type.equals("REGISTERED") && log.getUser().equals("anonymous")) continue;

        	ObjectNode entry = mapper.createObjectNode();
        	entry.put("Date",  df.format(log.getDate()));
        	entry.put("Datetime",  dtf.format(log.getDate()));
        	entry.put("Quantity", 1);
        	entry.put("URI", log.getUri());
        	entry.put("User", log.getUser());
        	data.add(entry);
        }	

        db.getMongo().close();
        return ok(vis.toString()).as("application/json");
	}
	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result getPageViewStats() {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        ObjectMapper mapper = new ObjectMapper();

		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		final DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        ObjectNode vis = mapper.createObjectNode();
        vis.put("id", "page-view-stats");
        vis.put("name", "Page View Statistics");
        vis.put("type", "Table");

        ArrayNode data = mapper.createArrayNode();
        vis.put("datatable", data);

        Date today = new Date();
        Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, -30);
		Date dateBefore30Days = cal.getTime();

		cal.setTime(today);
		cal.add(Calendar.DATE, -365);
		Date dateBefore365Days = cal.getTime();

		cal.setTime(today);
		cal.add(Calendar.DATE, -1);
		Date dateBefore1Day = cal.getTime();

        long total = users.getLogs().getDbCollection().count();
        long anon = users.getLogs().getDbCollection().count(new BasicDBObject("user", "anonymous"));
        long lastday = users.getLogs().getDbCollection().count(new BasicDBObject("date", new BasicDBObject("$gte", dateBefore1Day)));
        long last30days = users.getLogs().getDbCollection().count(new BasicDBObject("date", new BasicDBObject("$gte", dateBefore30Days)));
        long last12months = users.getLogs().getDbCollection().count(new BasicDBObject("date", new BasicDBObject("$gte", dateBefore365Days)));

        ObjectNode s = mapper.createObjectNode();
        s.put("Stat", "Total Page Views");
        s.put("Value", total);
        data.add(s);

        s = mapper.createObjectNode();
        s.put("Stat", "Anonymous Page Views");
        s.put("Value", anon);
        data.add(s);

        s = mapper.createObjectNode();
        s.put("Stat", "Last Day");
        s.put("Value", lastday);
        data.add(s);

        s = mapper.createObjectNode();
        s.put("Stat", "Last 30 Days");
        s.put("Value", last30days);
        data.add(s);

        s = mapper.createObjectNode();
        s.put("Stat", "Last 12 Months");
        s.put("Value", last12months);
        data.add(s);

        db.getMongo().close();
        return ok(vis.toString()).as("application/json");
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result adminApi(String path) {

		if (!path.startsWith("/")){
			path = "/" + path;
		}
		String url = play.Play.application().configuration().getString("ossmeter.adminapi.path") + path; 
		String username = play.Play.application().configuration().getString("ossmeter.adminapi.username"); 
		String password = play.Play.application().configuration().getString("ossmeter.adminapi.password"); 

		Promise<Result> promise = WS.url(url).setAuth(username, password, WSAuthScheme.BASIC).get().map(
		    new Function<WSResponse, Result>() {
		        public Result apply(WSResponse response) {
		        	List<String> contentTypes = response.getAllHeaders().get("Content-Type");
		        	if (contentTypes.size() > 0) {
		        		String type = contentTypes.get(0);
		        		if (type.contains("application/json")) {
		        			return ok(response.asJson());
		        		} else if(type.equals("image/png")) {
		        			return ok(response.getBodyAsStream());
		        		} else {
		        			System.err.println("Unrecognised Content-Type.");
		        			System.err.println(response);
			        		return ok();	
		        		}
		        	} else {
		        		System.err.println("No Content-Type set on response.");
		        		return ok();
		        	}
		        }
		    }
		);

		return promise.get(120000);
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result makeAdmin(String email) {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        User u = users.getUsers().findOneByEmail(email);

        if (u == null) {
        	flash(Application.FLASH_ERROR_KEY, "That doesn't appear to be a valid user!");
        	return redirect(routes.Users.users());
        }

        Role r = new Role();
        r.setName(MongoAuthenticator.ADMIN_ROLE);
        u.getRoles().add(r);
        users.getUsers().sync();

        db.getMongo().close();
        return redirect(routes.Users.users());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result revokeAdmin(String email) {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        User u = users.getUsers().findOneByEmail(email);

        if (u == null) {
        	flash(Application.FLASH_ERROR_KEY, "That doesn't appear to be a valid user!");
        	List<User> userList = MongoAuthenticator.findAllUsers();
		    return badRequest(views.html.users.users.render(userList));
        }

        for (Role r : u.getRoles()) {
        	if (r.getName().equals(MongoAuthenticator.ADMIN_ROLE)) {
				u.getRoles().remove(r);
        		break;
        	}
        }
		users.getUsers().sync();

        db.getMongo().close();	
        return redirect(routes.Users.users());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result qualityModels() {
		
        return ok(views.html.admin.qualityModels.render());
	}

	public static String readFile(File f) {
		
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(f));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        br.close();
	        return sb.toString();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return null;
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result editQualityModel(String id) {
		// Lookup QM
		QualityModel qm = MongoAuthenticator.getPlatformQualityModel(id);
		
		//System.out.println(qm.getIdentifier() + " " + id);

		if (qm == null) {
			// File f = play.Play.application().getFile("conf/quality/qualitymodel.json");
			// String c = readFile(f);
			// MongoAuthenticator.insertOrUpdateAdminQualityModel("quality", c);

			flash(Application.FLASH_ERROR_KEY, "No quality model with that ID");
			return badRequest(views.html.admin.qualityModels.render());
		}
		return ok(views.html.admin.editQualityModel.render(qm));
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result saveQualityModel(String id) {
		// // Lookup QM
		// QualityModel qm = MongoAuthenticator.getPlatformQualityModel(id);
		
		// //System.out.println(qm.getIdentifier() + " " + id);

		// if (qm == null) {
		// File f = play.Play.application().getFile("conf/quality/infosourcemodel.json");
		// String c = readFile(f);
		// MongoAuthenticator.insertOrUpdateAdminQualityModel("info", c);

		// 	flash(Application.FLASH_ERROR_KEY, "No quality model with that ID");
		// 	return badRequest(views.html.admin.qualityModels.render());
		// }

		DynamicForm form = Form.form().bindFromRequest();

		MongoAuthenticator.insertOrUpdateAdminQualityModel(id, form.get("json"));

		return ok();
	}

	public static Result jsRoutes() {
		return ok(
				Routes.javascriptRouter("adminJSRoutes",
					controllers.routes.javascript.Admin.getUsagePlot(),
					controllers.routes.javascript.Admin.getPageViewStats(),
					controllers.routes.javascript.Admin.getPageViewPlot(),
					controllers.routes.javascript.Admin.adminApi(),
					controllers.routes.javascript.Admin.getWebAppErrors(),
					controllers.routes.javascript.Admin.saveQualityModel()
					)
				)
				.as("text/javascript");
	}
}