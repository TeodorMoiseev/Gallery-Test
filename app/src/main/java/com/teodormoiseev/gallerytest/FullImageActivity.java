package com.teodormoiseev.gallerytest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(getString(R.string.uri));
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        Glide
                .with(this)
                .load(uri)
                .into(imageView);

    }
}
