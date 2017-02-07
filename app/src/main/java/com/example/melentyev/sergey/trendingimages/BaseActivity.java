package com.example.melentyev.sergey.trendingimages;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {
    static final String FLICKR_QUERY = "FLICKR_QUERY";
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    void activateToolBar(boolean enableHome) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
    }
}
