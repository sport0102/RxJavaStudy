package com.study.myapplication.presentation.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("bind:imageUrl")
fun ImageView.bindImageUrl(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(this.context)
            .load(it)
            .into(this)
    }
}