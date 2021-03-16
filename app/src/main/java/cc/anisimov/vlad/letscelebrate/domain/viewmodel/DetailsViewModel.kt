package cc.anisimov.vlad.letscelebrate.domain.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat

class DetailsViewModel @ViewModelInject constructor() : ViewModel() {
    //  o for observable
    val oName = MutableLiveData<String>()
    val oBirthday = MutableLiveData<String>()
}