package cc.anisimov.vlad.letscelebrate.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class BirthdayData(
    val name: String,
    val birthdayDate: Date,
    val imageData: ImageData
) : Parcelable
