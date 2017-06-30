package com.teodormoiseev.gallerytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Uri> imageUris = new ArrayList<>();

    ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return imageUris.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new SquareImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        try {
            final InputStream imageStream = mContext.getContentResolver().openInputStream(imageUris.get(position));
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            selectedImage = Bitmap.createScaledBitmap(selectedImage, 100, 100, true);
            imageView.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return imageView;
    }

    final Uri getUriByIndex(int position) {
        return imageUris.get(position);
    }

    void addImage(Uri uri) {
        imageUris.add(uri);
    }

    ArrayList<Uri> getImageUris() {
        return imageUris;
    }

    void setImageUris(ArrayList<Uri> uris) {
        imageUris = uris;
    }
}
