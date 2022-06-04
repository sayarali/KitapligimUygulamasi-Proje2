package com.alisayar.kitapligimuygulamas_proje2.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    private val _goAddFragmentEvent = MutableLiveData<Boolean>()
    val goAddFragmentEvent: LiveData<Boolean> get() = _goAddFragmentEvent

    init {

    }

    fun goAddFragment(){
        _goAddFragmentEvent.value = true
    }
    fun goAddFragmentComplete(){
        _goAddFragmentEvent.value = false
    }

}