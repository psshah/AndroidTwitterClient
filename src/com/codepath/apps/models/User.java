package com.codepath.apps.models;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

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
    @Column(name = "followersCount")
    private int followersCount;
    @Column(name = "followingCount")
    private int followingCount;
    @Column(name = "tagline")
    public String tagline;

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
	public int getFollowersCount() {
		return followersCount;
	}
	public int getFollowingCount() {
		return followingCount;
	}
	public String getTagline() {
		return tagline;
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
			user.followersCount = jsonObject.getInt("followers_count");
			user.followingCount = jsonObject.getInt("friends_count");
			user.tagline = jsonObject.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		// save user in database if not exists
		//User dbUser = new Select().from(User.class).where("screenName = ?",user.username).executeSingle();
		User dbUser = new Select().from(User.class).where("userid=?", user.userid).executeSingle();
		if(dbUser == null) {
			user.save();
			return user;
		} else {
			return dbUser;
		}
	}
	
}
