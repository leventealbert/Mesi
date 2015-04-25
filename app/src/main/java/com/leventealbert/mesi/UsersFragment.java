package com.leventealbert.mesi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_users, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.fragment_users_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*Downloading data from below url*/
        new AsyncHttpTask(this).execute("http://mesi.leventealbert.com/api/users");

        return layout;
    }

    @Override
    public void setAdapter(String result) {
        try {
            JSONArray usersJson = new JSONArray(result);

            ArrayList<User> userList = new ArrayList<>();

            for (int i = 0; i < usersJson.length(); i++) {
                JSONObject userJson = usersJson.optJSONObject(i);
                User item = new User(userJson);
                userList.add(item);
            }

            mRecyclerView.setAdapter(new UserListAdapter(getActivity(), userList, R.layout.user_list_row));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
