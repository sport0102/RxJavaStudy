package com.study.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.study.myapplication.api.model.MovieResponse
import com.study.myapplication.base.BaseViewModel
import com.study.myapplication.source.NaverRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val naverRepository: NaverRepository) : BaseViewModel() {

    private val _movieList = MutableLiveData<List<MovieResponse.Item>>()
    val movieList: LiveData<List<MovieResponse.Item>> get() = _movieList
    val searchKeyword = MutableLiveData<String>()

    private fun clearKeyword() {
        searchKeyword.value = ""
    }

    fun getMovieList(query: String) {
        naverRepository.getMovieList(query)?.let { single ->
            addDisposable(
                single
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _isDataLoadingError.value = Event(false)
                        _movieList.postValue(it.items)
                        clearKeyword()
                    }, {
                        _isDataLoadingError.value = Event(true)
                    })
            )
        }
    }
}
