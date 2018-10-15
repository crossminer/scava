package controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import model.*;
import model.Users;
import models.*;
import play.Routes;
import play.data.Form;
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

import static play.data.Form.*;

import org.apache.commons.mail.*;

import play.libs.ws.*;
import play.libs.F.Function;
import play.libs.F.Promise;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class News extends Controller {

	private static final Form<NewsItem> NEWS_ITEM_FORM = form(NewsItem.class);

	public static Result index() {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

		List<NewsItem> news = new ArrayList<>();
        Iterator<NewsItem> it = users.getNews().iterator();
        while (it.hasNext()) {
        	NewsItem n = it.next();
        	if (n.getStatus().equals(NewsItemStatus.PUBLISHED)) {
	        	news.add(n);
        	}
        }

        db.getMongo().close();

		return ok(views.html.news.index.render(news));
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result adminIndex() {

		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

		List<NewsItem> news = new ArrayList<>();
        Iterator<NewsItem> it = users.getNews().iterator();
        while (it.hasNext()) {
        	news.add(it.next());
        }
        
        db.getMongo().close();

		return ok(views.html.admin.news.render(news, NEWS_ITEM_FORM));
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result createItem() {
		final Form<NewsItem> filledForm = NEWS_ITEM_FORM.bindFromRequest();

		NewsItem news = filledForm.get();
		news.setDate(new java.util.Date());

		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        users.getNews().add(news);

        users.getNews().sync();
        db.getMongo().close();

		flash(Application.FLASH_MESSAGE_KEY, "News item created.");
		return redirect(routes.News.adminIndex());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result deleteItem(String id) {
		
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        NewsItem news = users.getNews().findOneById(id);

        if (news != null) {
        	users.getNews().remove(news);
        }

        users.getNews().sync();
        db.getMongo().close();

		flash(Application.FLASH_MESSAGE_KEY, "News item deleted.");
		return redirect(routes.News.adminIndex());
	}

	@Restrict(@Group(MongoAuthenticator.ADMIN_ROLE))
	public static Result changeStatus(String id, String status) {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

        NewsItem news = users.getNews().findOneById(id);

        if (news != null) {
        	try {
	        	NewsItemStatus st = NewsItemStatus.valueOf(status);
	        	news.setStatus(st);
		        users.getNews().sync();
	        } catch (Exception e) {
	        	flash(Application.FLASH_ERROR_KEY, "Attempt to update to an invalid status: " + status);
	        }
        }

        db.getMongo().close();

		flash(Application.FLASH_MESSAGE_KEY, "News item updated.");
		return redirect(routes.News.adminIndex());
	}

	public static List<NewsItem> getLatestNews(int count) {
		DB db = MongoAuthenticator.getUsersDb();
        Users users = new Users(db);

		List<NewsItem> news = new ArrayList<>();
        Iterator<NewsItem> it = users.getNews().iterator();
        while (it.hasNext() && news.size() < count) {
        	NewsItem n = it.next();
        	if (n.getStatus().equals(NewsItemStatus.PUBLISHED)) news.add(n);
        }
        
        db.getMongo().close();
        return news;
	}
}