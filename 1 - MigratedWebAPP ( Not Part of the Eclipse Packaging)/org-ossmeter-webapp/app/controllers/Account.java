package controllers;

import java.util.*;

import model.*;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.SubjectPresent;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;

import play.data.Form;
import play.data.DynamicForm;
import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthUser;
import views.html.account.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static play.data.Form.form;

import auth.MongoAuthenticator;

@With(LogAction.class)
public class Account extends Controller {

	@SubjectPresent
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result updateQualityModelSelection(String id) {
		User user = Application.getLocalUser(session());

		if (user == null) {
			// Not logged in
			return badRequest("Sorry, you need to be logged in to do that.");
		}

		// TODO this is hardcoded due to time constraints, but really
		// should be more general to allow multiple QMs
		if (id.equals("info") || id.equals("quality") || id.equals("custom")) {

			MongoAuthenticator.updateUserQualityModelSelection(user, id);

			return ok();
		} else {
			return badRequest("Invalid quality model ID");
		}		
	}

	@SubjectPresent
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result editQualityModel() {
		User user = Application.getLocalUser(session());

		if (user == null) {
			// Not logged in
			return badRequest("Sorry, you need to be logged in to do that.");
		}

		return ok(views.html.account.editQualityModel.render(user.getQualityModel()));
	}

	@SubjectPresent
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result saveQualityModel() {
		User user = Application.getLocalUser(session());

		if (user == null) {
			// Not logged in
			return badRequest("Sorry, you need to be logged in to do that.");
		}

		DynamicForm form = Form.form().bindFromRequest();
		MongoAuthenticator.insertOrUpdateUserQualityModel(user, form.get("json"));

		return ok();
	}


	@SubjectPresent
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result watchSpark(String projectid, String metricid, String projectName, String metricName) {
		User user = Application.getLocalUser(session());

		if (user == null) {
			// Not logged in
			return ok("Sorry, you need to be logged in to do that.");
		}

		MongoAuthenticator.toggleSparkGrid(user, projectid, projectName, metricid, metricName);

		System.out.println("Looks like it worked!" + request().getQueryString("projectid") + " " + request().getQueryString("metricid"));
		
		return ok("");
	}

	@SubjectPresent
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result deleteGridObject(String id) {
		User user = Application.getLocalUser(session());

		if (user == null) {
			// Not logged in
			return ok("Sorry, you need to be logged in to do that.");
		}

		MongoAuthenticator.deleteGridObject(user, id);
		return redirect(routes.Application.profile());
	}

	@SubjectPresent
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result updateNotification(String projectid, String metricid, double value, boolean aboveThreshold) {
		User user = Application.getLocalUser(session());

		if (user == null) {
			// Not logged in
			return ok("Sorry, you need to be logged in to do that.");
		}

		MongoAuthenticator.updateNotification(user, projectid, metricid, value, aboveThreshold);
		
		return ok(""); // TODO?
	}

	@SubjectPresent
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result updateGridLocations(String serializedLocations) {
		try {
			User user = Application.getLocalUser(session());

			if (user == null) {
				// Not logged in
				return ok("Sorry, you need to be logged in to do that.");
			}

			ObjectMapper mapper = new ObjectMapper();
			ArrayNode loc = (ArrayNode)mapper.readTree(serializedLocations);

			MongoAuthenticator.updateGridLocations(user, loc);

			return ok();
		} catch (Exception e) {
			return badRequest();
		}
	}	

	public static class Accept {

		@Required
		@NonEmpty
		public Boolean accept;

		public Boolean getAccept() {
			return accept;
		}

		public void setAccept(Boolean accept) {
			this.accept = accept;
		}
	}

	public static class PasswordChange {
		@MinLength(5)
		@Required
		public String password;

		@MinLength(5)
		@Required
		public String repeatPassword;

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRepeatPassword() {
			return repeatPassword;
		}

		public void setRepeatPassword(String repeatPassword) {
			this.repeatPassword = repeatPassword;
		}

		public String validate() {
			if (password == null || !password.equals(repeatPassword)) {
				return Messages
						.get("ossmeter.change_password.error.passwords_not_same");
			}
			return null;
		}
	}

	private static final Form<Accept> ACCEPT_FORM = form(Accept.class);
	private static final Form<Account.PasswordChange> PASSWORD_CHANGE_FORM = form(Account.PasswordChange.class);

	@SubjectPresent
	public static Result link() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		return ok(link.render());
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result verifyEmail() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final User user = Application.getLocalUser(session());
		if (user.getEmailValidated()) {
			// E-Mail has been validated already
			flash(Application.FLASH_MESSAGE_KEY,
					Messages.get("ossmeter.verify_email.error.already_validated"));
		} else if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
			flash(Application.FLASH_MESSAGE_KEY, Messages.get(
					"ossmeter.verify_email.message.instructions_sent",
					user.getEmail()));
			MyUsernamePasswordAuthProvider.getProvider()
					.sendVerifyEmailMailingAfterSignup(user, ctx());
		} else {
			flash(Application.FLASH_MESSAGE_KEY, Messages.get(
					"ossmeter.verify_email.error.set_email_first",
					user.getEmail()));
		}
		return redirect(routes.Application.profile());
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result changePassword() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final User u = Application.getLocalUser(session());

		if (!u.getEmailValidated()) {
			return ok(unverified.render());
		} else {
			return ok(password_change.render(PASSWORD_CHANGE_FORM));
		}
	}

	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result doChangePassword() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<Account.PasswordChange> filledForm = PASSWORD_CHANGE_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not select whether to link or not link
			return badRequest(password_change.render(filledForm));
		} else {
			final User user = Application.getLocalUser(session());
			final String newPassword = filledForm.get().password;
			MongoAuthenticator.changeUserPassword(new MyUsernamePasswordAuthUser(newPassword, user.getEmail()),
					true);
			flash(Application.FLASH_MESSAGE_KEY,
					Messages.get("ossmeter.change_password.success"));
			return redirect(routes.Application.profile());
		}
	}

	@SubjectPresent
	public static Result askLink() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final AuthUser u = PlayAuthenticate.getLinkUser(session());
		if (u == null) {
			// account to link could not be found, silently redirect to login
			return redirect(routes.Application.index());
		}
		return ok(ask_link.render(ACCEPT_FORM, u));
	}

	@SubjectPresent
	public static Result doLink() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final AuthUser u = PlayAuthenticate.getLinkUser(session());
		if (u == null) {
			// account to link could not be found, silently redirect to login
			return redirect(routes.Application.index());
		}

		final Form<Accept> filledForm = ACCEPT_FORM.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not select whether to link or not link
			return badRequest(ask_link.render(filledForm, u));
		} else {
			// User made a choice :)
			final boolean link = filledForm.get().accept;
			if (link) {
				flash(Application.FLASH_MESSAGE_KEY,
						Messages.get("ossmeter.accounts.link.success"));
			}
			return PlayAuthenticate.link(ctx(), link);
		}
	}

	@SubjectPresent
	public static Result askMerge() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		// this is the currently logged in user
		final AuthUser aUser = PlayAuthenticate.getUser(session());

		// this is the user that was selected for a login
		final AuthUser bUser = PlayAuthenticate.getMergeUser(session());
		if (bUser == null) {
			// user to merge with could not be found, silently redirect to login
			return redirect(routes.Application.index());
		}

		// You could also get the local user object here via
		// User.findByAuthUserIdentity(newUser)
		return ok(ask_merge.render(ACCEPT_FORM, aUser, bUser));
	}

	@SubjectPresent
	public static Result doMerge() {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		// this is the currently logged in user
		final AuthUser aUser = PlayAuthenticate.getUser(session());

		// this is the user that was selected for a login
		final AuthUser bUser = PlayAuthenticate.getMergeUser(session());
		if (bUser == null) {
			// user to merge with could not be found, silently redirect to login
			return redirect(routes.Application.index());
		}

		final Form<Accept> filledForm = ACCEPT_FORM.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not select whether to merge or not merge
			return badRequest(ask_merge.render(filledForm, aUser, bUser));
		} else {
			// User made a choice :)
			final boolean merge = filledForm.get().accept;
			if (merge) {
				flash(Application.FLASH_MESSAGE_KEY,
						Messages.get("ossmeter.accounts.merge.success"));
			}
			return PlayAuthenticate.merge(ctx(), merge);
		}
	}

	@SubjectPresent
	public static Result createNotification() {
		// this is the currently logged in user
		final User user = Application.getLocalUser(session());

		final Form<Notification> form = form(Notification.class).bindFromRequest();

		// if (form.hasErrors()) {
		// 	flash("error", Messages.get("ossmeter.profile.notifications.creation.error"));
		// 	return badRequest(views.html.setupnotification.render(user, form));
		// }

		if (form.hasErrors()) {
			return badRequest("Form had errors" + form.errorsAsJson());
		}

		Notification noti = form.get();
		MongoAuthenticator.insertNotification(user, noti);

		System.out.println(noti.getDbObject());

		// flash(Application.FLASH_MESSAGE_KEY, Messages.get("ossmeter.profile.notifications.creation.success"));
		return ok("yarp");//redirect(routes.Application.profile());
	}

	@SubjectPresent
	public static Result loadEventGroupForm() {

		Form<EventGroup> form = form(EventGroup.class);

		return ok(views.html.account._eventGroupForm.render(form));
	}

	@SubjectPresent
	public static Result editEventGroup(String id) {

		final User user = Application.getLocalUser(session());
		EventGroup eg = MongoAuthenticator.getEventGroupById(user, id);

		Form<EventGroup> form = form(EventGroup.class);
		form.fill(eg);

		return ok(views.html.account._eventGroupForm.render(form));
	}

	@SubjectPresent
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result starProject(String projectId) {
		final User user = Application.getLocalUser(session());
		MongoAuthenticator.starProject(user, projectId);
		return ok(); //TODO: probably want to some checking of the result
	}

	@SubjectPresent
	@Restrict(@Group(MongoAuthenticator.USER_ROLE))
	public static Result unstarProject(String projectId) {
		final User user = Application.getLocalUser(session());
		MongoAuthenticator.unstarProject(user, projectId);
		return ok(); //TODO: probably want to some checking of the result
	}

	@SubjectPresent
	public static Result createEventGroup() {
		// this is the currently logged in user
		final User user = Application.getLocalUser(session());

		final Form<EventGroup> form = form(EventGroup.class).bindFromRequest();

		if (form.hasErrors()) {
			flash("error", Messages.get("ossmeter.profile.eventgroup.creation.error"));
			return badRequest(views.html.setupeventgroup.render(user, form));
		}

		EventGroup group = form.get();
		group.setRow(1);
		group.setCol(1);
		group.setSizeX(1);
		group.setSizeY(2);

		List<Event> toRemove = new ArrayList<>();
		for (Event e : group.getEvents()) {
			if (e.getName() == null || e.getName().equals("")
				||	e.getDate() == null) {
				toRemove.add(e);
			}
		}
		
		group.getEvents().removeAll(toRemove);

		MongoAuthenticator.insertNewGrid(user, group);

		flash(Application.FLASH_MESSAGE_KEY, Messages.get("ossmeter.profile.eventgroup.creation.success"));

		// flash(Application.FLASH_MESSAGE_KEY, "Unimplemented!");
		return redirect(routes.Application.profile());
	}

	@SubjectPresent
	public static Result createEvent() {
		// this is the currently logged in user
		// final User user = Application.getLocalUser(session());

		// DynamicForm dForm = Form.form().bindFromRequest();
		// final Form<Event> form = form(Event.class).bindFromRequest();

		// if (form.hasErrors()) {
		// 	flash("error", Messages.get("ossmeter.profile.event.creation.error"));
		// 	return badRequest(views.html.setupevent.render(user, form));
		// }

		// Event event = form.get();
		// System.err.println(event);
		// System.err.println(event.eventGroup);
		// System.err.println(dForm.get("eventGroup"));
		// // event.user = user;
		// // user.save();
		// // TODO

		// flash(Application.FLASH_MESSAGE_KEY, Messages.get("ossmeter.profile.event.creation.success"));
		flash(Application.FLASH_MESSAGE_KEY, "Unimplemented!");
		return redirect(routes.Application.profile());
	}

}
