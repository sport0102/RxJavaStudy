package com.study.myapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.myapplication.Event
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun removeDisposable(disposable: CompositeDisposable) {
        compositeDisposable.remove(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}