package com.shine_star_11.abc;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.shine_star_11.abc.underComment;

/**
 * Created by shine_star_11 on 7/29/16.
 */
@IgnoreExtraProperties
public class pokemonPost {

    public String comment;
    public String email;
    public double enlat;
    public double enlng;
    public String picture;
    public double pokenumber;
    public String profile_img;
    public double type;
    public String username;
    public String created_at;
    public List<underComment> under;

    public pokemonPost() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public pokemonPost(String comment, String email, double enlat, double enlng, String picture, double pokenumber, String profile_img, double type, String username, String created_at) {
        this.comment = comment;
        this.email = email;
        this.enlat = enlat;
        this.enlng = enlng;
        this.picture = picture;
        this.pokenumber = pokenumber;
        this.profile_img = profile_img;
        this.type = type;
        this.username = username;
        this.created_at = created_at;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("comment", comment);
        result.put("email", email);
        result.put("enlat", enlat);
        result.put("enlng", enlng);
        result.put("picture", picture);
        result.put("pokenumber", pokenumber);
        result.put("profile_img", profile_img);
        result.put("type", type);
        result.put("username", username);
        result.put("created_at", created_at);

        return result;
    }
}
