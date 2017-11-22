package com.start.laundryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageViewerForOrder extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer_for_order);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("imageUri");

        PhotoView photoView = findViewById(R.id.imageViewForOrder);

        ImageLoader.getInstance().displayImage(uri, photoView);

        Log.i("URI", uri);

    }
}
