package cc.anisimov.vlad.letscelebrate.domain.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import cc.anisimov.vlad.letscelebrate.domain.model.BirthdayData

class BirthdayViewModel @ViewModelInject constructor() : ViewModel() {

    fun initWith(birthdayData: BirthdayData) {
    }

}