package com.example.melentyev.sergey.trendingimages;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        activateToolBar(true);

        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        if (photo != null) {
            TextView photoTitle = (TextView) findViewById(R.id.photo_title);
            photoTitle.setText(photo.getmTitle());

            TextView photoTags = (TextView) findViewById(R.id.photo_tags);
            photoTags.setText(photo.getmTags());

            TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
            photoAuthor.setText(photo.getmAuthor());

            ImageView imageView = (ImageView) findViewById(R.id.photo_image);
            Picasso.with(this).load(photo.getmLink()).error(R.drawable.place_holder)
                    .placeholder(R.drawable.place_holder).into(imageView);
        }
    }

}
