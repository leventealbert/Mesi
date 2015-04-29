package com.leventealbert.mesi;

import org.json.JSONObject;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String lat;
    private String lng;
    private String avatar;
    private String teamId;
    private Boolean isOnline;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return this.lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTeamId() {
        return teamId;
    }
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }


    public int getNewMessagesCount(){
        int count = 0;
        if (BaseApplication.getMessages() != null) {
            for (Message message : BaseApplication.getMessages()) {
                if (message.getFromId().equals(id) && message.getIsNew()) {
                    count++;
                }
            }
        }
        return count;
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String id, String firstName, String lastName, String email, String lat, String lng, String avatar, String teamId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
        this.avatar = avatar;
        this.teamId = teamId;
    }
}
