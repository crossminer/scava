package controllers;

import java.util.UUID;
import java.util.Date;

import views.html.*;
import model.InvitationRequest;
import model.Users;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints;

import static play.data.Form.*;
import com.typesafe.plugin.*;

import auth.MongoAuthenticator;
import com.mongodb.Mongo;
import com.mongodb.DB;

@With(LogAction.class)
public class Invitation extends Controller{

    private static Form<InvitationRequest> invitationForm = Form.form(InvitationRequest.class);

    public static Result submitInvitationRequest() throws Exception {       

        Form<model.InvitationRequest> boundForm= invitationForm.bindFromRequest();
        
        if(boundForm.hasErrors()){
            flash("error", "Please correct the form below.");
            return ok(views.html.invitation.render(invitationForm));
        }
        else{
            InvitationRequest invitation = boundForm.get();

            DB db = MongoAuthenticator.getUsersDb();
            Users users = new Users(db);

            // Check they're not already a user
            if (users.getUsers().findOneByEmail(invitation.getEmail()) != null) {
                db.getMongo().close();
                flash(Application.FLASH_MESSAGE_KEY, "It looks like you already have an account with this address. Why not try logging in, or requesting a password reset?");
                return redirect(routes.Application.login());
            }

            // Check they've not already requested an invite
            if (users.getInvites().findOneByEmail(invitation.getEmail()) != null) {
                db.getMongo().close();
                flash(Application.FLASH_MESSAGE_KEY, "It looks like you have already requested an invitation. Don't worry, we've not forgotten you! We'll get in touch as soon as we can.");
                return redirect(routes.Application.index());
            }

            // Generate token and store
            invitation.setToken(UUID.randomUUID().toString());
            invitation.setStatus("NOT SENT");
            invitation.setRequestedAt(new Date());
            users.getInvites().add(invitation);

            users.getInvites().sync();
            db.getMongo().close();

            flash(Application.FLASH_MESSAGE_KEY, "Thanks for your interest! We'll get in touch soon!");
            return redirect(routes.Application.index());
        }
    }

    public static Result requestInvitation() {
        return ok(views.html.invitation.render(invitationForm));
    }

    /**
     * Called when the user clicks the link in their email. Perhaps badly named.
     *
     */
    public static Result acceptInvitation(String key) {
        DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        InvitationRequest inv = users.getInvites().findOneByToken(key);

        if (inv == null || inv.getStatus().equals("ACCEPTED")) {
            db.getMongo().close();
            flash(Application.FLASH_ERROR_KEY, "Sorry, that registration token wasn't valid. If you think this is a mistake, please get in touch.");
            return redirect(routes.Application.index());
        }

        db.getMongo().close();

        Form<InvitationRequest> form = form(InvitationRequest.class);
        form.fill(inv);

        return ok(signup.render(form));
    }

    /**
     * This is called by Application.java when someone signs up. I
     */
    public static void userRegistered(String email) {
        DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        InvitationRequest inv = users.getInvites().findOneByEmail(email);

        if (inv != null) {
            inv.setAcceptedAt(new Date());
            inv.setStatus("ACCEPTED");
            users.getInvites().sync();
        }
        db.getMongo().close();
    }
}