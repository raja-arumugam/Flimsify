package com.example.moviesdataapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.moviesdataapp.R
import com.example.moviesdataapp.utils.Constants.getBackDropPath
import com.example.moviesdataapp.utils.Constants.getPosterPath
import com.example.moviesdataapp.utils.Constants.getUserPic
import com.example.moviesdataapp.utils.Constants.getYouTubePath
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun ImageView.loadImage(url: String?, imagePaths: ImagePaths) {

    val placeholder = when (imagePaths) {
        ImagePaths.BACKDROP -> R.drawable.gray_placeholder
        ImagePaths.POSTER -> R.drawable.gray_placeholder
        ImagePaths.YOUTUBE -> R.drawable.gray_placeholder
        ImagePaths.REVIEW -> R.drawable.gray_placeholder
    }

    url?.let {

        val urlString = when (imagePaths) {
            ImagePaths.BACKDROP -> getBackDropPath(url)
            ImagePaths.POSTER -> getPosterPath(url)
            ImagePaths.YOUTUBE -> getYouTubePath(url)
            ImagePaths.REVIEW -> getUserPic(url)
        }

        Glide.with(this.context)
            .load(urlString)
            .apply(RequestOptions())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(this.context.circularProgressDrawable())
            .error(placeholder)
            .into(this)

    } ?: run {
        this.setImageResource(placeholder)
    }
}

@Suppress("IMPLICIT_CAST_TO_ANY")
fun Context.setLanguage(language: String): String {

    val lang = when (language) {
        resources.getString(R.string.en) -> LanguagePaths.English
        resources.getString(R.string.zh) -> LanguagePaths.Chinese
        resources.getString(R.string.ja) -> LanguagePaths.Japanese
        resources.getString(R.string.ko) -> LanguagePaths.Korean
        resources.getString(R.string.es) -> LanguagePaths.Spanish
        resources.getString(R.string.sv) -> LanguagePaths.Swedish
        resources.getString(R.string.de) -> LanguagePaths.German
        resources.getString(R.string.da) -> LanguagePaths.Danish
        resources.getString(R.string.pt) -> LanguagePaths.Portuguese
        resources.getString(R.string.ar) -> LanguagePaths.Arabic
        resources.getString(R.string.hi) -> LanguagePaths.Hindi
        resources.getString(R.string.ms) -> LanguagePaths.Malay
        resources.getString(R.string.tl) -> LanguagePaths.EastTimor
        resources.getString(R.string.sk) -> LanguagePaths.Slovakia
        resources.getString(R.string.fr) -> LanguagePaths.French
        resources.getString(R.string.nl) -> LanguagePaths.Dutch
        resources.getString(R.string.fi) -> LanguagePaths.Farsi
        resources.getString(R.string.ta) -> LanguagePaths.Tamil
        resources.getString(R.string.ml) -> LanguagePaths.Malayalam
        resources.getString(R.string.te) -> LanguagePaths.Telegu
        resources.getString(R.string.kn) -> LanguagePaths.Kannadam

        else -> {}
    }

    return lang.toString()
}


fun Context.circularProgressDrawable(): Drawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = 7f
        centerRadius = 60f
        setColorSchemeColors(
            androidx.core.content.ContextCompat.getColor(
                this@circularProgressDrawable,
                R.color.silver
            )
        )
        start()
    }
}

fun Double.format(digits: Int): String {
    val df = DecimalFormat()
    df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    df.maximumFractionDigits = digits
    return df.format(this)
}
