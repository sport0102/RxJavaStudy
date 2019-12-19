package com.study.myapplication.ext

import android.os.Build
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("bind:html")
fun TextView.html(title: String?) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(title,Html.FROM_HTML_MODE_COMPACT)
    } else{
        Html.fromHtml(title)
    }
}