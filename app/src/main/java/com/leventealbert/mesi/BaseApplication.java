package com.leventealbert.mesi;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author levencus
 */
public class BaseApplication extends Application {

    //private static String mCurrentUserId = "41bde8a9-0bdb-4a8d-b491-3b9ae7789236";
    private static String mCurrentUserId = "ca92662c-4be0-4ae8-a55b-9ffa5b77b2fe";
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
}
