package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.eclipse.scava.repository.model.Project;

import play.Routes;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import play.mvc.Result;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;
import play.libs.ws.*;
import play.libs.F.Function;
import play.libs.F.Promise;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static play.data.Form.*;
import auth.MongoAuthenticator;

import com.mongodb.Mongo;
import com.mongodb.DB;

import model.Users;
import model.MailingListItem;

public class DemoApplication extends Controller {

	private static final Form<MailingListItem> MAILING_LIST_FORM = form(MailingListItem.class);

	private static String[] demoProjects = new String[]{
		"modeling-mmt-atl","modeling-emf", "modeling-incquery"
	};

	public static Result index() {
		return ok(views.html.demo.index.render(MAILING_LIST_FORM));
	}

	public static Result compare() {
		return ok(views.html.demo.compare.render(Application.getInformationSourceModel()));
	}

	public static Result viewProject(String id, boolean summary) {
		if (Arrays.asList(demoProjects).contains(id)) {
			Project project = Projects.getProject(id);
			if (project == null) {
				flash(Application.FLASH_ERROR_KEY, "An unexpected error has occurred. We are looking into it.");
				return ok(views.html.demo.index.render(MAILING_LIST_FORM));
			}

			model.Project iProject = MongoAuthenticator.findProjectById(project.getShortName());

			return ok(views.html.demo.view_project.render(project, iProject, Application.getInformationSourceModel(), summary));

		} else {
			flash(Application.FLASH_ERROR_KEY, "Invalid project identifier.");
			return redirect(routes.DemoApplication.index());
		}
	}

//http://stackoverflow.com/questions/153716/verify-email-in-java
	private static final Pattern rfc2822 = Pattern.compile(
        "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
		);

		

	public static Result addToMailingList() {
		final Form<MailingListItem> filledForm = MAILING_LIST_FORM.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.demo.index.render(MAILING_LIST_FORM));
		} else {
			MailingListItem mli = filledForm.get();

			if (!rfc2822.matcher(mli.getEmail()).matches()) {
				flash(Application.FLASH_ERROR_KEY, "That doesn't appear to be a valid email! Please try again, or get in touch.");    
				return badRequest(views.html.demo.index.render(MAILING_LIST_FORM));
			}

			DB db = MongoAuthenticator.getUsersDb();
            Users users = new Users(db);

            if (users.getMailingList().findOneByEmail(mli.getEmail()) == null) {
            	mli.setDate(new Date());
            	users.getMailingList().add(mli);
            	users.getMailingList().sync();
				flash(Application.FLASH_MESSAGE_KEY, "Thanks for your interest!");
            } else {
            	// Already in the DB
            	flash(Application.FLASH_MESSAGE_KEY, "Looks like you're already on our list! Thanks for your interest!");
            }

			db.getMongo().close();

			return redirect(routes.Application.index());
		}
	}
}
