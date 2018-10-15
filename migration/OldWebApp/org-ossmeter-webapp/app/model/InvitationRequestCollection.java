package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class InvitationRequestCollection extends PongoCollection<InvitationRequest> {
	
	public InvitationRequestCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("email");
		createIndex("token");
	}
	
	public Iterable<InvitationRequest> findById(String id) {
		return new IteratorIterable<InvitationRequest>(new PongoCursorIterator<InvitationRequest>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<InvitationRequest> findByEmail(String q) {
		return new IteratorIterable<InvitationRequest>(new PongoCursorIterator<InvitationRequest>(this, dbCollection.find(new BasicDBObject("email", q + ""))));
	}
	
	public InvitationRequest findOneByEmail(String q) {
		InvitationRequest invitationRequest = (InvitationRequest) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("email", q + "")));
		if (invitationRequest != null) {
			invitationRequest.setPongoCollection(this);
		}
		return invitationRequest;
	}
	

	public long countByEmail(String q) {
		return dbCollection.count(new BasicDBObject("email", q + ""));
	}
	public Iterable<InvitationRequest> findByToken(String q) {
		return new IteratorIterable<InvitationRequest>(new PongoCursorIterator<InvitationRequest>(this, dbCollection.find(new BasicDBObject("token", q + ""))));
	}
	
	public InvitationRequest findOneByToken(String q) {
		InvitationRequest invitationRequest = (InvitationRequest) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("token", q + "")));
		if (invitationRequest != null) {
			invitationRequest.setPongoCollection(this);
		}
		return invitationRequest;
	}
	

	public long countByToken(String q) {
		return dbCollection.count(new BasicDBObject("token", q + ""));
	}
	
	@Override
	public Iterator<InvitationRequest> iterator() {
		return new PongoCursorIterator<InvitationRequest>(this, dbCollection.find());
	}
	
	public void add(InvitationRequest invitationRequest) {
		super.add(invitationRequest);
	}
	
	public void remove(InvitationRequest invitationRequest) {
		super.remove(invitationRequest);
	}
	
}