package controllers;

import auth.MongoAuthenticator;

import model.Users;
import model.User;
import model.Log;

import com.mongodb.DB;

import play.mvc.Result;
import play.mvc.Action;
import play.mvc.Http.Context;
import static play.libs.F.Promise;

public class LogAction extends Action.Simple {
	public Promise<Result> call(Context ctx) throws Throwable {
		if (ctx.request().uri().startsWith("/assets")) return delegate.call(ctx);
		if (ctx.request().uri().startsWith("/api")) return delegate.call(ctx);

		String username = "anonymous";
    	User user = Application.getLocalUser(ctx.session());

    	if (user != null) {
    		username = user.getEmail();
    	}

    	Log log = new Log();
    	log.setUser(username);
    	log.setDate(new java.util.Date());
    	log.setUri(ctx.request().uri());

    	DB db = MongoAuthenticator.getUsersDb();
    	Users users = new Users(db);

    	users.getLogs().add(log);
    	users.getLogs().sync();

    	db.getMongo().close();

    	return delegate.call(ctx);
  	}
}
