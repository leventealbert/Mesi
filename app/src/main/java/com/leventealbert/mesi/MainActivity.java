package com.leventealbert.mesi;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolBar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

    class MainPagerAdapter extends FragmentPagerAdapter {

        String[] tabs;
        int[] icons = {R.drawable.ic_group_white_48dp, R.drawable.ic_style_white_48dp, R.drawable.ic_folder_open_white_48dp};

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.activity_main_tabs);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
            {
                return new UsersFragment();
            }
            else {
                return new ProjectsFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = getResources().getDrawable(icons[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth() / 2, image.getIntrinsicHeight() / 2);
            SpannableString sb = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
