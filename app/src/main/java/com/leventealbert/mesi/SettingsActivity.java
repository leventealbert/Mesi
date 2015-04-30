package com.leventealbert.mesi;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * setting activity for future improvements
 *
 * @author Levente Albert
 */
public class SettingsActivity extends BaseActivity {

    /**
     * method called on create
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setToolBar();
    }
}
