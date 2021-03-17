package cc.anisimov.vlad.letscelebrate.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageData(
    val url: String? = null,
    val uri: Uri? = null
) : Parcelable
