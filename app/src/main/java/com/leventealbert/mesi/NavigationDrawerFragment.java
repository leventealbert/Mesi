package com.leventealbert.mesi;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    private NavigationView mNavigationView;
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

        NavigationView mNavigationView = (NavigationView) layout.findViewById(R.id.fragment_navigation_drawer_view);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.menu_drawer_invite:
                        startActivity(new Intent(getActivity(), InviteUserActivity.class));
                        return true;
                    case R.id.menu_drawer_settings:
                        startActivity(new Intent(getActivity(), SettingsActivity.class));
                        return true;
                    case R.id.menu_drawer_logout:
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
                        return true;
                    default:
                        return true;
                }
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
