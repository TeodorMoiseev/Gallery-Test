package com.teodormoiseev.gallerytest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private final int Pick_image = 1;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    GridView gridView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));
        if (savedInstanceState != null) {
            ArrayList<Uri> uris = savedInstanceState.getParcelableArrayList("images");
            ImageAdapter adapter = (ImageAdapter) gridView.getAdapter();
            adapter.setImageUris(uris);
            adapter.notifyDataSetChanged();
            gridView.setAdapter(adapter);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(
                        getApplicationContext(),
                        FullImageActivity.class
                );
                ImageAdapter adapter = ((ImageAdapter) gridView.getAdapter());
                i.putExtra("uri", adapter.getUriByIndex(position));
                startActivity(i);
            }
        });

        Button pickImageButton = (Button) findViewById(R.id.button);
        pickImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                Intent photoPickerIntent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI
                );
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case Pick_image:
                if (resultCode == RESULT_OK) {
                    final Uri imageUri = imageReturnedIntent.getData();
                    ImageAdapter adapter = (ImageAdapter) gridView.getAdapter();
                    adapter.addImage(imageUri);
                    adapter.notifyDataSetChanged();
                    gridView.setAdapter(adapter);
                }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<Uri> uris = ((ImageAdapter) gridView.getAdapter()).getImageUris();
        outState.putParcelableArrayList("images", uris);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        ArrayList<Uri> uris = savedInstanceState.getParcelableArrayList("images");
        ImageAdapter adapter = (ImageAdapter) gridView.getAdapter();
        adapter.setImageUris(uris);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }
}
