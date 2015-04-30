package com.leventealbert.mesi;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * this class is the base of all the activities
 *
 * @author Levente Albert
 */
public class BaseActivity extends ActionBarActivity {

    /**
     * function that is called when the activity is created
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * method overload
     */
    protected void setToolBar(){
        setToolBar("", true);
    }

    /**
     * method overload
     *
     * @param title String
     */
    protected void setToolBar(String title){
        setToolBar(title, true);
    }

    /**
     * method used for setting the toolbar
     *
     * @param title String
     * @param homeButton boolean
     */
    protected void setToolBar(String title, boolean homeButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.app_bar_title);

        if (!title.equals("")) {
            toolbarTitle.setText(title);
        } else {
            toolbarTitle.setText(getTitle());
        }

        //setting the toolbar to be used for the actionbar
        setSupportActionBar(toolbar);
        if (homeButton) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(null);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}