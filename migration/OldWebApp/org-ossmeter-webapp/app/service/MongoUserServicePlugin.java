package service;

import model.User;
import play.Application;

import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.service.UserServicePlugin;

import auth.MongoAuthenticator;

public class MongoUserServicePlugin extends UserServicePlugin {

	public MongoUserServicePlugin(final Application app) {
		super(app);
	}

	/*
	 *	Invoked when a user logs in for the first time
	 */
	@Override
	public Object save(final AuthUser authUser) {
		final boolean exists = MongoAuthenticator.userExists(authUser);
		if (!exists) {
			User user =  MongoAuthenticator.createUser(authUser);
			return user.getIdentifier();
		} else {
			return null;
		}
	}

	/*
	 *	Invoked on any login to check whether the session user has a 
	 * 	valid local user. 
	 *	@returns the local user or null
	 */
	@Override
	public Object getLocalIdentity(final AuthUserIdentity identity) {
		final User u = MongoAuthenticator.findUser(identity);
		if (u == null){
			return null;
		} else {
			return u.getIdentifier();
		}
	}

	/*
	 *	Merges two different local user accounts into one.
	 *	@returns the user to generate the session information from/
	 */
	@Override
	public AuthUser merge(final AuthUser newUser, final AuthUser oldUser) {
		if (!oldUser.equals(newUser)) {
			MongoAuthenticator.mergeUsers(oldUser, newUser);
		}
		return oldUser;
	}

	/*
	 *	Links a new account to an existing local user.
	 *	@returns the auth user to log in with.
	 */
	@Override
	public AuthUser link(final AuthUser user, final AuthUser linkedAccount) {
		MongoAuthenticator.linkAccount(user, linkedAccount);
		return null; // TODO: not sure. The example returns this.
	}
	
	/*
	 *	Invoked when a user logs in. 
	 *	@returns itself, perhaps updated (e.g. with last login date)
	 */
	@Override
	public AuthUser update(final AuthUser knownUser) {
		MongoAuthenticator.updateUserLastLogin(knownUser);
		return knownUser;
	}

}
