package com.dicoding.justview.detail

import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.justview.core.R
import com.dicoding.justview.core.domain.model.View
import com.facebook.shimmer.ShimmerFrameLayout

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_cloud_download_24)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("linkName", "linkUrl")
fun bindLinkText(textView: TextView, linkName: String?, linkUrl: String?) {
    val text = "<a href='$linkUrl'>$linkName</a>"
    textView.movementMethod = LinkMovementMethod.getInstance()
    textView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

@BindingAdapter("loadContainer")
fun loadContainer(constraintLayout: ConstraintLayout, data: View?) {
    if (data == null) {
        constraintLayout.visibility = android.view.View.GONE
    } else {
        constraintLayout.visibility = android.view.View.VISIBLE
    }
}

@BindingAdapter("shimmerLoad")
fun shimmering(shimmerFrameLayout: ShimmerFrameLayout, data: View?) {
    if (data == null) {
        shimmerFrameLayout.visibility = android.view.View.VISIBLE
    } else {
        shimmerFrameLayout.visibility = android.view.View.GONE
    }
}