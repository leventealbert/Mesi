package com.leventealbert.mesi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private RecyclerView mRecycleView;
    private CustomListAdapter mUserAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_users, container, false);
        mRecycleView = (RecyclerView) layout.findViewById(R.id.fragment_users_list);
        mUserAdapter = new CustomListAdapter(getActivity(), getData(), R.layout.user_list_row);
        mRecycleView.setAdapter(mUserAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public List<User> getData() {
        List<User> data = new ArrayList<>();
        int[] avatars = {R.drawable.user_avatar, R.drawable.user_avatar, R.drawable.user_avatar, R.drawable.user_avatar};
        String[] names = {"Levente Albert", "Eva Simon", "John Cook", "Amanda Los"};

        for (int i = 0; i < names.length && i < avatars.length; i++) {
            User current = new User();
            current.fullName = names[i];
            current.iconId = avatars[i];
            data.add(current);
        }
        return data;
    }
}
