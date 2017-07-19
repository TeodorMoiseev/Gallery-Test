package com.teodormoiseev.gallerytest;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<Uri> mImageUris = new ArrayList<>();

    ImageAdapter(Context c) {
        mContext = c;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return mImageUris.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gridview_item_image, parent, false);
        }

        Glide
                .with(mContext)
                .load(mImageUris.get(position))
                .fitCenter()
                .into((ImageView) convertView);

        return convertView;
    }

    final Uri getUriByIndex(int position) {
        return mImageUris.get(position);
    }

    void addImage(Uri uri) {
        mImageUris.add(uri);
    }

    ArrayList<Uri> getImageUris() {
        return mImageUris;
    }

    void setImageUris(ArrayList<Uri> uris) {
        mImageUris = uris;
    }
}
