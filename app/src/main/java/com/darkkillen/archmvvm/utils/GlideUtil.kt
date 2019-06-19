package com.darkkillen.archmvvm.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

object GlideUtil {

    @JvmStatic
    fun load(context: Context, url: String, imageView: ImageView, any: Any?) {
        Glide.with(context)
                .load(url)
                .listener(listener(any))
                .into(imageView)
    }

    fun loadCircle(context: Context?, uri: Uri?, imageView: ImageView, any: Any?) {
        if (context != null) Glide.with(context)
                .load(uri)
                .listener(listener(any))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
    }

    fun loadCircle(context: Context?, bitmap: Bitmap?, imageView: ImageView, any: Any?) {
        if (context != null) Glide.with(context)
                .load(bitmap)
                .listener(listener(any))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
    }

    private fun listener(any: Any?): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                return false
            }

        }
    }

}