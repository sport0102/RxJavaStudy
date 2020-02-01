package com.study.myapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.myapplication.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel() {

    protected val _isDataLoading = MutableLiveData(
        Event(
            true
        )
    )
    val isDataLoading: LiveData<Event<Boolean>> get() = _isDataLoading

    protected val _isDataLoadingError = MutableLiveData(
        Event(
            false
        )
    )
    val isDataLoadingError: LiveData<Event<Boolean>> get() = _isDataLoadingError

    protected val coroutineExceptionHanlder = CoroutineExceptionHandler { _, throwable ->
        _isDataLoadingError.value = Event(true)
    }

    protected val ioDispatchers = Dispatchers.IO + coroutineExceptionHanlder
    protected val uiDispatchers = Dispatchers.Main + coroutineExceptionHanlder

}