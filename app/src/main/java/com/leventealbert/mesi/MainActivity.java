package com.leventealbert.mesi;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main activity where most of the magic happens
 *
 * @author Levente Albert
 */
public class MainActivity extends ActionBarActivity {

    private Toolbar mToolBar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    /**
     * function that is called when the activity is created
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar) findViewById(R.id.app_bar);
        TextView toolbarTitle = (TextView) mToolBar.findViewById(R.id.app_bar_title);

        toolbarTitle.setText(getTitle());

        //marking user online
        new AsyncHttpTask("PUT", "", new AsyncHttpTask.TaskListener() {
            @Override
            public void onFinished(String result) {
                if (result.equals("")) {
                    Toast.makeText(MainActivity.this, "User was NOT marked online!", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute("http://mesi.leventealbert.com/api/online");

        //Initialize Google Analytics tracker
        Tracker t = BaseApplication.getTracker(getBaseContext(),
                BaseApplication.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("Main");

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolBar);

        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.activity_main_pager);
        mPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

        // Assiging the Sliding Tab Layout View
        mTabs = (SlidingTabLayout) findViewById(R.id.activity_main_tabs);
        mTabs.setDistributeEvenly(true);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accent_color));
        mTabs.setCustomTabView(R.layout.custom_tab, 0);

        // Setting the ViewPager For the SlidingTabsLayout
        mTabs.setViewPager(mPager);
    }

    /**
     * function called when the menu is being created to inflate the menu
     *
     * @param menu menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * called on the action items selection
     *
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_map) {
            startActivity(new Intent(this, MapsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * method used for setting the current user on the drawer layout
     *
     * @param user User
     */
    public void setCurrentUser(User user) {
        ImageView image = (ImageView) findViewById(R.id.fragment_navigation_drawer_user_image);
        TextView name = (TextView) findViewById(R.id.fragment_navigation_drawer_user_name);
        TextView subTitle = (TextView) findViewById(R.id.fragment_navigation_drawer_user_team);

        name.setText(user.getFullName());
        subTitle.setText(user.getEmail().split("@")[1]);

        Picasso.with(getBaseContext()).load(user.getAvatar())
                .transform(new RoundedTransformation(40, 4))
                .error(R.drawable.ic_account_circle_grey600_48dp)
                .placeholder(R.drawable.ic_account_circle_grey600_48dp)
                .into(image);
    }

    /**
     * pager adapter uses for the sliding tabs
     */
    class MainPagerAdapter extends FragmentPagerAdapter {
        String[] tabs;
        int[] icons = {R.drawable.ic_group_white_48dp, R.drawable.ic_style_white_48dp, R.drawable.ic_folder_open_white_48dp};

        /**
         * constructor
         *
         * @param fm FragmentManager
         */
        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.activity_main_tabs);
        }

        /**
         * function used to retrive the correct fragment for a particular tab
         * @param position int
         * @return Fragment
         */
        @Override
        public Fragment getItem(int position) {
            if(position == 0)
            {
                return new UsersFragment();
            } else if (position == 1) {
                return new ProjectsFragment();
            } else {
                return new FilesFragment();
            }
        }

        /**
         * function to retrive the title of the tab and in this case ads the icon to the spannable string.
         *
         * @param position int
         * @return CharSequence
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = getResources().getDrawable(icons[position]);
            if (image != null) {
                image.setBounds(0, 0, image.getIntrinsicWidth() / 2, image.getIntrinsicHeight() / 2);
            }
            SpannableString sb = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }

        /**
         * returns the count of the tabs
         *
         * @return int
         */
        @Override
        public int getCount() {
            return 3;
        }
    }
}
