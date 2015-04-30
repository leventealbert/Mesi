package com.leventealbert.mesi;

import org.json.JSONObject;

/**
 * data class for the user object
 *
 * @author Levente Albert
 */
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

    /**
     * return the id
     *
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * set the id
     *
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get first name
     *
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * set the first name
     *
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * get last name
     *
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * set last name
     *
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * get email
     *
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * set email
     *
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *  get latitude
     *
     * @return String
     */
    public String getLat() {
        return this.lat;
    }

    /**
     * set latitude
     *
     * @param lat String
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * get longitude
     *
     * @return String
     */
    public String getLng() {
        return this.lng;
    }

    /**
     * set longitude
     *
     * @param lng String
     */
    public void setLng(String lng) {
        this.lng = lng;
    }

    /**
     * get avatar
     *
     * @return String
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     *
     * set avatar
     * @param avatar String
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * get team id
     *
     * @return String
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     * set team id
     *
     * @param teamId String
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    /**
     * get full name
     *
     * @return String
     */
    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    /**
     * return if user is online
     *
     * @return Boolean
     */
    public Boolean getIsOnline() {
        return isOnline;
    }

    /**
     * get all the mesage count for the user
     *
     * @return int
     */
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

    /**
     * constructor
     *
     * @param firstName String
     * @param lastName String
     * @param email String
     */
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * constructor
     *
     * @param id String
     * @param firstName String
     * @param lastName String
     * @param email String
     * @param lat Strin
     * @param lng String
     * @param avatar String
     * @param teamId String
     */
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
