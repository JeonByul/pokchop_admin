package com.shine_star_11.abc.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.shine_star_11.abc.underComment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class pokeStopGym {

    public String email;
    public double enlat;
    public double enlng;
    public int type;
    public String created_at;

    public pokeStopGym() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public pokeStopGym(String email, double enlat, double enlng, int type, String created_at) {
        this.email = email;
        this.enlat = enlat;
        this.enlng = enlng;
        this.type = type;
        this.created_at = created_at;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("enlat", enlat);
        result.put("enlng", enlng);
        result.put("type", type);
        result.put("created_at", created_at);

        return result;
    }
}
