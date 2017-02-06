package com.example.melentyev.sergey.trendingimages;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetFlickrJsonData.OnDataAvailable,
        RecyclerItemClickListener.OnRecyclerClickListener{
    public static final String TAG = "MainActivity";
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());
        recyclerView.setAdapter(mFlickrRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetFlickrJsonData getFlickrJsonData =
                new GetFlickrJsonData("https://api.flickr.com/services/feeds/photos_public.gne",
                        this, "en-us", true);
        getFlickrJsonData.execute("death valley");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(List<Photo> photos, DownloadStatus status) {
        if (status == DownloadStatus.OK)
            mFlickrRecyclerViewAdapter.loadNewData(photos);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(MainActivity.this, "Normal tap at position" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(MainActivity.this, "Long tap at position" + position, Toast.LENGTH_SHORT).show();
    }
}
