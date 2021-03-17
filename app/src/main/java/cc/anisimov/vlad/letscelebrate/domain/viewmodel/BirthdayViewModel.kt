package cc.anisimov.vlad.letscelebrate.domain.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cc.anisimov.vlad.letscelebrate.domain.model.Age
import cc.anisimov.vlad.letscelebrate.domain.model.BirthdayData
import cc.anisimov.vlad.letscelebrate.domain.model.ImageData
import cc.anisimov.vlad.letscelebrate.util.DateUtils
import cc.anisimov.vlad.letscelebrate.util.SingleLiveEvent

class BirthdayViewModel @ViewModelInject constructor() : ViewModel() {

    companion object {
        const val DEFAULT_NEW_CHILD_IMAGE_URL =
            "https://as2.ftcdn.net/jpg/02/50/10/03/1000_F_250100321_1hYz6jdwgiKmXz3mC4q0BCnR83jSTSbL.jpg"
    }

    val oError = SingleLiveEvent<String?>()
    lateinit var name: String
    val oImageData = MutableLiveData<ImageData?>()
    val oAge = MutableLiveData<Age?>()
    lateinit var uiOption: UIOption

    enum class UIOption {
        Elephant, Fox, Pelican
    }

    fun initWith(birthdayData: BirthdayData) {
        name = birthdayData.name
        oImageData.value = birthdayData.imageData
        oAge.value = DateUtils.getAge(birthdayData.birthdayDate)
        uiOption = UIOption.values()[UIOption.values().indices.random()]
    }

    fun onCameraClick() {
        oImageData.value = ImageData(url = DEFAULT_NEW_CHILD_IMAGE_URL)
    }
}