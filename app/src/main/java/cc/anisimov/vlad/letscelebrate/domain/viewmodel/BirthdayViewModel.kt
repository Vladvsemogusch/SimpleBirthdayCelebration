package cc.anisimov.vlad.letscelebrate.domain.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import cc.anisimov.vlad.letscelebrate.domain.model.BirthdayData
import cc.anisimov.vlad.letscelebrate.util.SingleLiveEvent

class BirthdayViewModel @ViewModelInject constructor() : ViewModel() {

    val oError = SingleLiveEvent<String?>()

    fun initWith(birthdayData: BirthdayData) {

    }

}