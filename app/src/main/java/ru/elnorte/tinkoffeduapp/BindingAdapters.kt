package ru.elnorte.tinkoffeduapp

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.elnorte.tinkoffeduapp.ui.overview.ScreenState

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.download_fail_image)
            )
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imgView)
    }
}
@BindingAdapter("hideOnLoading")
fun ViewGroup.hideOnLoading(responseState: ScreenState?) {
    visibility = if (responseState is ScreenState.Loading)
        View.GONE
    else
        View.VISIBLE
}

@BindingAdapter("showOnLoading")
fun ViewGroup.showOnLoading(responseState: ScreenState?) {
    visibility = if (responseState is ScreenState.Loading)
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("showOnError")
fun ViewGroup.showError(responseState: ScreenState?) {
    visibility = if (responseState is ScreenState.Error)
        View.VISIBLE
    else
        View.GONE
            //text = (responseState as UIResponseState.Error).errorMessage
}

@BindingAdapter("errorText")
fun TextView.showError(responseState: ScreenState?) {
    if(responseState is ScreenState.Error){
        this.text = responseState.message
    }
    else{
        this.text = null
    }
    //text = (responseState as UIResponseState.Error).errorMessage
}
