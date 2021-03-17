package cc.anisimov.vlad.letscelebrate.util

import android.content.Context
import android.widget.ImageView
import cc.anisimov.vlad.letscelebrate.domain.model.ImageData
import coil.load

fun ImageView.load(imageData: ImageData){
    imageData.url?.let { load(it) }
    imageData.uri?.let { load(it) }
}


fun Int.toPx(context: Context): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}