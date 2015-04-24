package com.leventealbert.mesi;

import org.json.JSONObject;

public class User {

    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    private String FirstName;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    private String LastName;

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    private String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String Lat;

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    private String Lng;

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }

    private String Avatar;

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    private String TeamId;

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String teamId) {
        TeamId = teamId;
    }

    public String getFullName() {
        return FirstName + ' ' + LastName;
    }

    public User(String id, String firstName, String lastName, String email, String lat, String lng, String avatar, String teamId) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Lat = lat;
        Lng = lng;
        Avatar = avatar;
        TeamId = teamId;
    }

    public User(JSONObject json) {
        Id = json.optString("id");
        FirstName = json.optString("firstName");
        LastName = json.optString("lastName");
        Email = json.optString("email");
        Lat = json.optString("lat");
        Lng = json.optString("lng");
        Avatar = json.optString("avatar");
        TeamId = json.optString("teamId");
    }
}
