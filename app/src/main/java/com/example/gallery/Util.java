package com.example.gallery;

import android.content.Context;
import android.widget.ImageView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Util {
    public static void loadImage(ImageView view, String url, CircularProgressDrawable drawable)
    {
        RequestOptions options = new RequestOptions()
                .placeholder(drawable)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(view.getContext())
        .setDefaultRequestOptions(options)
        .load(url)
        .into(view);
    }
    public static CircularProgressDrawable getProgressDrawable(Context context){
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.start();
        return progressDrawable;
    }

}
