package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class TokenCollection extends PongoCollection<Token> {
	
	public TokenCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("token");
	}
	
	public Iterable<Token> findById(String id) {
		return new IteratorIterable<Token>(new PongoCursorIterator<Token>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Token> findByToken(String q) {
		return new IteratorIterable<Token>(new PongoCursorIterator<Token>(this, dbCollection.find(new BasicDBObject("token", q + ""))));
	}
	
	public Token findOneByToken(String q) {
		Token token = (Token) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("token", q + "")));
		if (token != null) {
			token.setPongoCollection(this);
		}
		return token;
	}
	

	public long countByToken(String q) {
		return dbCollection.count(new BasicDBObject("token", q + ""));
	}
	
	@Override
	public Iterator<Token> iterator() {
		return new PongoCursorIterator<Token>(this, dbCollection.find());
	}
	
	public void add(Token token) {
		super.add(token);
	}
	
	public void remove(Token token) {
		super.remove(token);
	}
	
}