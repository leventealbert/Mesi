package com.leventealbert.mesi;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * base application class holds application wide parameters and methods
 *
 * @author Levente Albert
 */
public class BaseApplication extends Application {

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    static HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    /**
     * static function to return an instance of a tracker fro Google Analytics
     *
     * @param context Context
     * @param trackerId TrackerName
     * @return Tracker
     */
    static synchronized Tracker getTracker(Context context, TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            Tracker t = analytics.newTracker(context.getString(R.string.google_analytics_property_id));
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }

    private static String mCurrentUserId;
    private static User mCurrentUser;
    private static UserList mUsers;
    private static MessageList mMessages;
    private static MessageList mReceivedMessages;

    private static Map<String, String> mMap;

    /**
     * get current user id
     *
     * @return String
     */
    public static String getCurrentUserId() {
        return mCurrentUserId;
    }

    /**
     * set the current user id
     *
     * @param currentUserId String
     */
    public static void setCurrentUserId(String currentUserId) {
        mCurrentUserId = currentUserId;
    }

    /**
     * get current user
     *
     * @return User
     */
    public static User getCurrentUser() {
        return mCurrentUser;
    }

    /**
     * get all the users
     *
     * @return User
     */
    public static UserList getUsers() {
        return mUsers;
    }

    /**
     * set all the users
     *
     * @param users UserList
     */
    public static void setUsers(UserList users) {
        mUsers = new UserList();
        mMap = new HashMap<>();

        for (User user : users) {
            if (!user.getId().equals("")){
                if (user.getId().equals(mCurrentUserId)) {
                    mCurrentUser = user;
                } else {
                    mUsers.add(user);
                }
                mMap.put(user.getId(),user.getAvatar());
            }
        }
        users.clear();
    }

    /**
     * getting the avatar from the hashmap
     *
     * @param userId String
     * @return String
     */
    public static String getAvatar(String userId){
        return mMap.get(userId);
    }

    /**
     * Get all the messages
     *
     * @return MessageList
     */
    public static MessageList getMessages() {
        return mMessages;
    }

    /**
     * Set all the messages
     *
     * @param messages MessageList
     */
    public static void setMessages(MessageList messages) {
        mMessages = messages;
    }

    /**
     * function used to get item from shared preferences
     *
     * @param context Context
     * @param key String
     * @return String
     */
    public static String getPref(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    /**
     * Method used to save a string to the shared preferences
     * @param context Context
     * @param key String
     * @param value String
     */
    public static void setPref(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        prefs.edit().putString(key, value).apply();
    }
}
