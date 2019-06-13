package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import java.io.Serializable;
import java.util.UUID;
import org.eclipse.scava.crossflow.runtime.Job;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class Document implements Serializable {
	
	public Document() {}
	
	public Document(String uid, String project_name, String post_id, String parent_id, String creator, String accepted_answer_id, Date created_at, Integer score, Long view_count, String message_body, String last_editor, String last_editor_display_name, Date last_edit_date, Date last_activity_date, Date community_owned_date, String title, List<String> tags, Integer answer_count, Integer comment_count, Integer favourite_count, String plain_text, Boolean contains_code) {
		this.uid = uid;
		this.project_name = project_name;
		this.post_id = post_id;
		this.parent_id = parent_id;
		this.creator = creator;
		this.accepted_answer_id = accepted_answer_id;
		this.created_at = created_at;
		this.score = score;
		this.view_count = view_count;
		this.message_body = message_body;
		this.last_editor = last_editor;
		this.last_editor_display_name = last_editor_display_name;
		this.last_edit_date = last_edit_date;
		this.last_activity_date = last_activity_date;
		this.community_owned_date = community_owned_date;
		this.title = title;
		this.tags = tags;
		this.answer_count = answer_count;
		this.comment_count = comment_count;
		this.favourite_count = favourite_count;
		this.plain_text = plain_text;
		this.contains_code = contains_code;
	}
	
		
	protected String uid;
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getUid() {
		return uid;
	}
	
	protected String project_name;
	
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	
	public String getProject_name() {
		return project_name;
	}
	
	protected String post_id;
	
	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}
	
	public String getPost_id() {
		return post_id;
	}
	
	protected String parent_id;
	
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	
	public String getParent_id() {
		return parent_id;
	}
	
	protected String creator;
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getCreator() {
		return creator;
	}
	
	protected String accepted_answer_id;
	
	public void setAccepted_answer_id(String accepted_answer_id) {
		this.accepted_answer_id = accepted_answer_id;
	}
	
	public String getAccepted_answer_id() {
		return accepted_answer_id;
	}
	
	protected Date created_at;
	
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	public Date getCreated_at() {
		return created_at;
	}
	
	protected Integer score;
	
	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Integer getScore() {
		return score;
	}
	
	protected Long view_count;
	
	public void setView_count(Long view_count) {
		this.view_count = view_count;
	}
	
	public Long getView_count() {
		return view_count;
	}
	
	protected String message_body;
	
	public void setMessage_body(String message_body) {
		this.message_body = message_body;
	}
	
	public String getMessage_body() {
		return message_body;
	}
	
	protected String last_editor;
	
	public void setLast_editor(String last_editor) {
		this.last_editor = last_editor;
	}
	
	public String getLast_editor() {
		return last_editor;
	}
	
	protected String last_editor_display_name;
	
	public void setLast_editor_display_name(String last_editor_display_name) {
		this.last_editor_display_name = last_editor_display_name;
	}
	
	public String getLast_editor_display_name() {
		return last_editor_display_name;
	}
	
	protected Date last_edit_date;
	
	public void setLast_edit_date(Date last_edit_date) {
		this.last_edit_date = last_edit_date;
	}
	
	public Date getLast_edit_date() {
		return last_edit_date;
	}
	
	protected Date last_activity_date;
	
	public void setLast_activity_date(Date last_activity_date) {
		this.last_activity_date = last_activity_date;
	}
	
	public Date getLast_activity_date() {
		return last_activity_date;
	}
	
	protected Date community_owned_date;
	
	public void setCommunity_owned_date(Date community_owned_date) {
		this.community_owned_date = community_owned_date;
	}
	
	public Date getCommunity_owned_date() {
		return community_owned_date;
	}
	
	protected String title;
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	protected List<String> tags;
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	protected Integer answer_count;
	
	public void setAnswer_count(Integer answer_count) {
		this.answer_count = answer_count;
	}
	
	public Integer getAnswer_count() {
		return answer_count;
	}
	
	protected Integer comment_count;
	
	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}
	
	public Integer getComment_count() {
		return comment_count;
	}
	
	protected Integer favourite_count;
	
	public void setFavourite_count(Integer favourite_count) {
		this.favourite_count = favourite_count;
	}
	
	public Integer getFavourite_count() {
		return favourite_count;
	}
	
	protected String plain_text;
	
	public void setPlain_text(String plain_text) {
		this.plain_text = plain_text;
	}
	
	public String getPlain_text() {
		return plain_text;
	}
	
	protected Boolean contains_code;
	
	public void setContains_code(Boolean contains_code) {
		this.contains_code = contains_code;
	}
	
	public Boolean getContains_code() {
		return contains_code;
	}
	
	
	public Object[] toObjectArray() {
		Object[] ret = new Object[22];
	 	ret[0] = getUid();
	 	ret[1] = getProject_name();
	 	ret[2] = getPost_id();
	 	ret[3] = getParent_id();
	 	ret[4] = getCreator();
	 	ret[5] = getAccepted_answer_id();
	 	ret[6] = getCreated_at();
	 	ret[7] = getScore();
	 	ret[8] = getView_count();
	 	ret[9] = getMessage_body();
	 	ret[10] = getLast_editor();
	 	ret[11] = getLast_editor_display_name();
	 	ret[12] = getLast_edit_date();
	 	ret[13] = getLast_activity_date();
	 	ret[14] = getCommunity_owned_date();
	 	ret[15] = getTitle();
	 	ret[16] = getTags();
	 	ret[17] = getAnswer_count();
	 	ret[18] = getComment_count();
	 	ret[19] = getFavourite_count();
	 	ret[20] = getPlain_text();
	 	ret[21] = getContains_code();
		return ret;
	}
	
	public String toString() {
		return "Document (" + " uid=" + uid + " project_name=" + project_name + " post_id=" + post_id + " parent_id=" + parent_id + " creator=" + creator + " accepted_answer_id=" + accepted_answer_id + " created_at=" + created_at + " score=" + score + " view_count=" + view_count + " message_body=" + message_body + " last_editor=" + last_editor + " last_editor_display_name=" + last_editor_display_name + " last_edit_date=" + last_edit_date + " last_activity_date=" + last_activity_date + " community_owned_date=" + community_owned_date + " title=" + title + " tags=" + tags + " answer_count=" + answer_count + " comment_count=" + comment_count + " favourite_count=" + favourite_count + " plain_text=" + plain_text + " contains_code=" + contains_code + ")";
	}
	
}

