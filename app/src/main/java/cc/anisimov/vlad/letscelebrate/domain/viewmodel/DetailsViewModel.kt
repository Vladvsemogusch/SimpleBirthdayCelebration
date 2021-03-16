package cc.anisimov.vlad.letscelebrate.domain.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cc.anisimov.vlad.letscelebrate.domain.model.ImageData
import java.util.*

class DetailsViewModel @ViewModelInject constructor() : ViewModel() {
    companion object{
        const val DEFAULT_IMAGE_URL = "https://as1.ftcdn.net/jpg/02/91/54/38/1000_F_291543809_26XYk48erTYbRDdu7MxOCOzAyEwtCMK5.jpg"
    }
    //  o for observable
    val oName = MutableLiveData("")
    val oBirthdayDate = MutableLiveData(initialDate)
    val oSubmitEnabled = MediatorLiveData<Boolean?>()
    val oImageUrl = MutableLiveData<ImageData?>()

    init {
        oImageUrl.value = ImageData(DEFAULT_IMAGE_URL,null)
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

    val initialDate: Date
        get() = Calendar.getInstance().time


    val minDate: Date
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -12)
            return calendar.time
        }
}