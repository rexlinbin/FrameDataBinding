package com.lengyue.frame_databinding.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class GlideImageLoader {

    public static void displayImage(Context context, Object path, ImageView imageView, int placeHolderImageId) {
        Glide.with(context).load(path).placeholder(placeHolderImageId)
                .error(placeHolderImageId)
                .centerCrop().into(imageView);
    }

    public static void displayImage(Context context, Object path, ImageView imageView, int placeHolderImageId, int errorImageId) {
        Glide.with(context).load(path).placeholder(placeHolderImageId)
                .error(errorImageId)
                .centerCrop().into(imageView);
    }

    public static void displayCircleImage(Context context, Object path, ImageView imageView, int placeHolderImageId) {
        Glide.with(context)
                .load(path)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(placeHolderImageId)
                .centerCrop().into(imageView);
    }

    public static void displayCircleImage(Context context, Object path, ImageView imageView, int placeHolderImageId, int errorImageId) {
        Glide.with(context)
                .load(path)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(placeHolderImageId)
                .error(errorImageId)
                .centerCrop().into(imageView);
    }

    public static void displayRoundImage(Context context, Object path, ImageView imageView, int placeHolderImageId, int radius) {
        Glide.with(context)
                .load(path)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                .placeholder(placeHolderImageId)
                .centerCrop().into(imageView);
    }

    public static void displayRoundImage(Context context, Object path, ImageView imageView, int placeHolderImageId, int errorImageId, int radius) {
        Glide.with(context)
                .load(path)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                .placeholder(placeHolderImageId)
                .error(errorImageId)
                .centerCrop().into(imageView);
    }
}
