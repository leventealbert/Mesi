package com.leventealbert.mesi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

public class UsersFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_users, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.fragment_users_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.fragment_users_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent_color);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        //Initialize Google Analytics tracker
        Tracker t = BaseApplication.getTracker(getActivity().getBaseContext(),
                BaseApplication.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("Users List");

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        refreshItems();

        return layout;
    }

    private void refreshItems() {
        //Getting user data
        new AsyncHttpTask("GET", new AsyncHttpTask.TaskListener() {
            @Override
            public void onFinished(String result) {
                try {
                    UserList userList = new Gson().fromJson(result, UserList.class);
                    BaseApplication.setUsers(userList);

                    MainActivity activity = (MainActivity) getActivity();
                    activity.setCurrentUser(BaseApplication.getCurrentUser());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).execute("http://mesi.leventealbert.com/api/users");

        //getting messages
        new AsyncHttpTask("GET", new AsyncHttpTask.TaskListener() {
            @Override
            public void onFinished(String result) {
                try {
                    MessageList messageList = new Gson().fromJson(result, MessageList.class);
                    BaseApplication.setMessages(messageList);

                    //all items loaded refresh layout
                    onItemsLoadComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).execute("http://mesi.leventealbert.com/api/messages");
    }

    private void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        mRecyclerView.setAdapter(new UserListAdapter(getActivity(), BaseApplication.getUsers(), R.layout.list_row));

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
