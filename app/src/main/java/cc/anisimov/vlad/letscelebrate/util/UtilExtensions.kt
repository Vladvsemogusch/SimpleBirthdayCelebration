package cc.anisimov.vlad.letscelebrate.util

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import cc.anisimov.vlad.letscelebrate.domain.model.ImageData
import coil.load
import coil.request.ImageRequest

fun ImageView.load(imageData: ImageData, @DrawableRes placeholderResId: Int? = null) {
    val configureImageRequestBuilder: ImageRequest.Builder.() -> Unit = {
        crossfade(true)
        placeholderResId?.let {
            placeholder(placeholderResId)
        }
    }
    imageData.url?.let {
        load(uri = it, builder = configureImageRequestBuilder)
    }
    imageData.uri?.let { load(uri = it, builder = configureImageRequestBuilder) }

}


fun Int.toPx(context: Context): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}