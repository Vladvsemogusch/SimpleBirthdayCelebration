package cc.anisimov.vlad.letscelebrate.domain.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class DetailsViewModel @ViewModelInject constructor() : ViewModel() {
    //  o for observable
    val oName = MutableLiveData("")
    val oBirthdayDate = MutableLiveData(initialDate)
    val oSubmitEnabled = MediatorLiveData<Boolean?>()

    init {
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