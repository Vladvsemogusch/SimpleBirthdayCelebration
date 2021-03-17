package cc.anisimov.vlad.letscelebrate.util

import android.widget.ImageView
import cc.anisimov.vlad.letscelebrate.domain.model.ImageData
import coil.load

fun ImageView.load(imageData: ImageData){
    imageData.url?.let { load(it) }
    imageData.uri?.let { load(it) }
}