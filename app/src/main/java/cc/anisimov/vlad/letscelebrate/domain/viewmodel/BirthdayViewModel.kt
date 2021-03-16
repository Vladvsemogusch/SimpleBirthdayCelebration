package cc.anisimov.vlad.letscelebrate.domain.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cc.anisimov.vlad.letscelebrate.domain.model.BirthdayData
import cc.anisimov.vlad.letscelebrate.domain.model.ImageData
import cc.anisimov.vlad.letscelebrate.util.SingleLiveEvent
import java.util.*

class BirthdayViewModel @ViewModelInject constructor() : ViewModel() {

    val oError = SingleLiveEvent<String?>()
    val oName = MutableLiveData("")
    val oImageData = MutableLiveData<ImageData?>()
    private lateinit var birthdayDate: Date


    fun initWith(birthdayData: BirthdayData) {
        oName.value = birthdayData.name
        birthdayDate = birthdayData.birthdayDate
        oImageData.value = birthdayData.imageData
    }

}