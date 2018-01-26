package com.agung.android.moviedb.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by agung on 23/01/18.
 */

public class Function {
    public static void setImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(imageView);
    }

}
