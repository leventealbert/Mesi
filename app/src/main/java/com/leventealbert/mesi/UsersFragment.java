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
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        refreshItems();

        return layout;
    }

    private void refreshItems() {
        //Getting user dat
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
