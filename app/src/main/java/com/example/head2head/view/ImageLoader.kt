package com.example.head2head.view

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.head2head.R

interface ImageLoader {

    fun getImage(context: Context, view: ImageView?, route: String){
        if(view != null) {
            Glide.with(context)
                .load(route)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.baseline_error_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        }
    }
    fun clearCache(context: Context){
        val glide = Glide.get(context)
        glide.clearDiskCache()
        glide.clearMemory()
    }
}