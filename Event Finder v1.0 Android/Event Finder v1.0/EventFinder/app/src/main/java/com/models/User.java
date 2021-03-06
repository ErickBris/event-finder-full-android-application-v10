package com.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4736646656828092302L;

	@JsonProperty("user_id")
	int user_id;
	String username;
	String login_hash;
	String facebook_id;
	String twitter_id;
	String google_id;
	String full_name;
	String thumb_url;
	String photo_url;
	String email;
	String sms_no;
	String phone_no;

	int created_at;
	int updated_at;
	int is_deleted;

	String events_attended_count;
	String friends_count;
	String is_going;
	String is_interested;
	String is_invited;

	public String getEvents_attended_count() {
		return events_attended_count;
	}

	public void setEvents_attended_count(String events_attended_count) {
		this.events_attended_count = events_attended_count;
	}

	public String getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(String friends_count) {
		this.friends_count = friends_count;
	}

	public String getIs_going() {
		return is_going;
	}

	public void setIs_going(String is_going) {
		this.is_going = is_going;
	}

	public String getIs_interested() {
		return is_interested;
	}

	public void setIs_interested(String is_interested) {
		this.is_interested = is_interested;
	}

	public String getIs_invited() {
		return is_invited;
	}

	public void setIs_invited(String is_invited) {
		this.is_invited = is_invited;
	}

	public String getGoogle_id() {
		return google_id;
	}

	public void setGoogle_id(String google_id) {
		this.google_id = google_id;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setSms_no(String sms_no) {
		this.sms_no = sms_no;
	}

	public String getSms_no() {
		return sms_no;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public String getThumb_url() {
		return thumb_url;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setTwitter_id(String twitter_id) {
		this.twitter_id = twitter_id;
	}

	public String getTwitter_id() {
		return twitter_id;
	}

	public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
	}

	public String getFacebook_id() {
		return facebook_id;
	}

	public void setLogin_hash(String login_hash) {
		this.login_hash = login_hash;
	}

	public String getLogin_hash() {
		return login_hash;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_id() {
		return user_id;
	}


	public void setCreated_at(int created_at) {
		this.created_at = created_at;
	}

	public int getCreated_at() {
		return created_at;
	}


	public void setUpdated_at(int updated_at) {
		this.updated_at = updated_at;
	}

	public int getUpdated_at() {
		return updated_at;
	}


	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

	public int getIs_deleted() {
		return is_deleted;
	}
}
