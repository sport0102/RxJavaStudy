package com.study.myapplication.ui

import android.annotation.SuppressLint
import android.os.Bundle
import com.study.myapplication.BR
import com.study.myapplication.R
import com.study.myapplication.api.model.MovieResponse
import com.study.myapplication.base.BaseActivity
import com.study.myapplication.base.SimpleRecyclerView
import com.study.myapplication.databinding.ActivityMainBinding
import com.study.myapplication.databinding.ItemMovieInfoBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel by viewModel<MainViewModel>()
    private var mPublishSubject: PublishSubject<String> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchEvent()
        binding {
            mainRv.adapter =
                object : SimpleRecyclerView.Adapter<MovieResponse.Item, ItemMovieInfoBinding>(
                    layoutRes = R.layout.item_movie_info,
                    bindingVariableId = BR.item,
                    onClicked = { item, view, position -> }
                ) {}
            mainBtnSearch.setOnClickListener {
                viewModel.searchKeyword.value?.let {
                    mPublishSubject.onNext(it)
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun searchEvent() {
        mPublishSubject
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(500L,TimeUnit.MILLISECONDS)
            .subscribe {
                viewModel.getMovieList(it)
            }
    }

}