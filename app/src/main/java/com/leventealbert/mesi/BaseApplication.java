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
 * @author levencus
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

    static HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

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

    public static String getCurrentUserId() {
        return mCurrentUserId;
    }

    public static void setCurrentUserId(String currentUserId) {
        mCurrentUserId = currentUserId;
    }

    public static User getCurrentUser() {
        return mCurrentUser;
    }

    public static UserList getUsers() {
        return mUsers;
    }

    public static void setUsers(UserList users) {
        mUsers = new UserList();
        mMap = new HashMap<String, String>();

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

    public static String getAvatar(String userId){
        return mMap.get(userId);
    }

    public static ArrayList<Message> getMessages() {
        return mMessages;
    }

    public static void setMessages(MessageList messages) {
        mMessages = messages;
    }

    public static String getPref(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void setPref(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        prefs.edit().putString(key, value).apply();
    }
}
