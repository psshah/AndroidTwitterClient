package com.codepath.apps.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;

@Table(name = "User")
public class User extends Model implements Serializable {
    @Column(name = "name")
	private String name;
    @Column(name = "userid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long userid;
    @Column(name = "screenName", unique = true)
	private String screenName;	
    @Column(name = "profileImageUrl")
	private String profileImageUrl;

    public String getName() {
		return name;
	}
	public long getUserid() {
		return userid;
	}
	public String getScreenName() {
		return screenName;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

    // Make sure to have a default constructor for every ActiveAndroid model
    public User(){
       super();
    }

	public static User fromJSON(JSONObject jsonObject) {
		User user = new User();
		try {
			user.name = jsonObject.getString("name");
			user.userid = jsonObject.getLong("id");
			user.screenName = jsonObject.getString("screen_name");
			user.profileImageUrl = jsonObject.getString("profile_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		//user.save();
		return user;
	}
	
}
