package com.study.myapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.myapplication.Event

abstract class BaseViewModel : ViewModel() {

    protected val _isDataLoading = MutableLiveData(true)
    val isDataLoading: LiveData<Boolean> get() = _isDataLoading

    protected val _isDataLoadingError = MutableLiveData(Event("" to false))
    val isDataLoadingError: LiveData<Event<Pair<String, Boolean>>> get() = _isDataLoadingError

}