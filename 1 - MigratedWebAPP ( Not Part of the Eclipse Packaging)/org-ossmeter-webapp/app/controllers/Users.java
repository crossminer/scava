package controllers;

import java.util.List;

import play.*;
import play.mvc.*;

import model.Project;
import model.User;

import auth.MongoAuthenticator;

@With(LogAction.class)
public class Users extends Controller {

	public static Result users() {
	    List<User> userList = MongoAuthenticator.findAllUsers();
	    return ok(views.html.users.users.render(userList));
	}
	
	public static Result view(String email) {
	    User user = MongoAuthenticator.findUser(email);
	    return ok(views.html.users.view_item.render(user));
	}
	
	public static Result delete_confirmation(String id) {
		return ok(views.html.users.confirmation.render(id));
	}
	
	public static Result delete(String id) {
		User user = MongoAuthenticator.findUser(id);
		MongoAuthenticator.deleteUser(user);
		return redirect(routes.Users.users());
	}
}
