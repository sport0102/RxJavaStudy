package com.study.myapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
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
    private var publishSubject: PublishSubject<String> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSearchEvent()
        binding {
            mainRv.adapter =
                object : SimpleRecyclerView.Adapter<MovieResponse.Item, ItemMovieInfoBinding>(
                    layoutRes = R.layout.item_movie_info,
                    bindingVariableId = BR.item,
                    onClicked = { item, view, position ->
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse((item as MovieResponse.Item).link)
                            )
                        )
                    }
                ) {}
            // 검색 버튼 처리
            mainBtnSearch.setOnClickListener {
                viewModel.searchKeyword.value?.let {
                    publishSubject.onNext(it)
                }
            }
        }
        // 데이터 로딩 에러 처리
        viewModel.isDataLoadingError.observe(this, Observer {
            it.getContentIfNotHandled()?.let { isError ->
                if (isError) {
                    toastM(getString(R.string.main_toast_error_network))
                }
            }
        })
    }

    private fun addSearchEvent() {
        addDisposable(publishSubject
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(500L, TimeUnit.MILLISECONDS)
            .subscribe {
                viewModel.getMovieList(it)
            })
    }

}