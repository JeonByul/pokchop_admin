package com.shine_star_11.abc.model;

/**
 * Created by hyunjunian on 7/30/16.
 */
public class postitem {
    Double lat,lng;
    int pokeNumber;
    String userName, profileUrl, comment, createdAt;

    public postitem(String createdAt, Double lat, Double lng, int pokeNumber, String userName, String profileUrl, String comment) {
        this.createdAt = createdAt;
        this.lat = lat;
        this.lng = lng;
        this.pokeNumber = pokeNumber;
        this.userName = userName;
        this.profileUrl = profileUrl;
        this.comment = comment;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public int getPokeNumber() {
        return pokeNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getComment() {
        return comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
