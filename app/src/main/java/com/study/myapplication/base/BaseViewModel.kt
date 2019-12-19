package com.study.myapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val _isDataLoading = MutableLiveData(true)
    val isDataLoading: LiveData<Boolean> get() = _isDataLoading

    protected val _isDataLoadingError = MutableLiveData(false)
    val isDataLoadingError: LiveData<Boolean> get() = _isDataLoadingError

    protected val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

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