package cc.anisimov.vlad.letscelebrate.domain.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cc.anisimov.vlad.letscelebrate.R
import cc.anisimov.vlad.letscelebrate.domain.model.BirthdayData
import cc.anisimov.vlad.letscelebrate.domain.model.ImageData
import cc.anisimov.vlad.letscelebrate.util.SingleLiveEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*

class DetailsViewModel @ViewModelInject constructor(@ApplicationContext val appContext: Context) :
    ViewModel() {


    companion object {
        const val DEFAULT_IMAGE_URL =
            "https://as1.ftcdn.net/jpg/02/91/54/38/1000_F_291543809_26XYk48erTYbRDdu7MxOCOzAyEwtCMK5.jpg"
    }

    //  o for observable
    val oError = SingleLiveEvent<String?>()
    val oName = MutableLiveData("")
    val oBirthdayDate = MutableLiveData(initialDate)
    val oSubmitEnabled = MediatorLiveData<Boolean?>()
    val oImageData = MutableLiveData<ImageData?>()
    val oGoToBirthdayEvent = SingleLiveEvent<BirthdayData>()

    init {
        oImageData.value = ImageData(DEFAULT_IMAGE_URL, null)
        // At least 1 false enough to disable
        oSubmitEnabled.addSource(oName) { newName ->
            oSubmitEnabled.value = if (newName.isEmpty()) {
                false
            } else oBirthdayDate.value != null
        }
        oSubmitEnabled.addSource(oBirthdayDate) { newDate ->
            oSubmitEnabled.value = if (newDate == null) {
                false
            } else oName.value!!.isNotEmpty()
        }
    }

    //  Current time
    val initialDate: Date
        get() = Calendar.getInstance().time

    //  Current time -12 years
    val minDate: Date
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -12)
            return calendar.time
        }

    fun onSubmit() {
        if (oName.value == null || oBirthdayDate.value == null || oImageData == null) {
            oError.value = appContext.getString(R.string.some_error)
            return
        }
        val birthdayData = BirthdayData(
            name = oName.value!!,
            birthdayDate = oBirthdayDate.value!!,
            imageData = oImageData.value!!
        )
        oGoToBirthdayEvent.value = birthdayData
    }
}