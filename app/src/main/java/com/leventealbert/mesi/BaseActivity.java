package com.leventealbert.mesi;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setToolBar(){
        setToolBar("");
    }

    protected void setToolBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.app_bar_title);

        if (!title.equals("")) {
            toolbarTitle.setText(title);
        } else {
            toolbarTitle.setText(getTitle());
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}