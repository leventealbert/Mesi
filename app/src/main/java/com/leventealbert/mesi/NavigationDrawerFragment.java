package com.leventealbert.mesi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * class that is handling the navitaion drawer
 *
 * @author Levente Albert
 */
public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private View mContainerView;

    /**
     * constuctor
     */
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    /**
     * function called when the view is created
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        //setting the invite button
        TextView inviteButton = (TextView) layout.findViewById(R.id.fragment_navigation_drawer_invite_user);
        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InviteUserActivity.class));
            }
        });

        //setting the setting button
        TextView settingsButton = (TextView) layout.findViewById(R.id.fragment_navigation_drawer_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        //setting the logout button
        TextView logoutButton = (TextView) layout.findViewById(R.id.fragment_navigation_drawer_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //marking user offline
                new AsyncHttpTask("PUT", "", new AsyncHttpTask.TaskListener() {
                    @Override
                    public void onFinished(String result) {
                        //clear CurrentUserId in application
                        BaseApplication.setCurrentUserId("");
                        BaseApplication.setPref(getActivity().getBaseContext(), "CurrentUserId", "");
                    }
                }).execute("http://mesi.leventealbert.com/api/offline");

                Intent intent = new Intent(getActivity(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return layout;
    }

    /**
     * method that is being called from the main activity to setup the drawer
     *
     * @param fragmentId int
     * @param drawerLayout DrawerLayout
     * @param toolBar Toolbar
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolBar) {
        mContainerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close) {

            /**
             * called on the drawer opened
             * @param drawerView View
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            /**
             * called when the drawer is closed
             * @param drawerView View
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        // setting the drawer listener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
}
