package com.study.myapplication.presentation.compare

import android.os.Bundle
import androidx.lifecycle.Observer
import com.study.myapplication.R
import com.study.myapplication.presentation.base.BaseActivity
import com.study.myapplication.databinding.ActivityCompareCoinBinding
import com.study.myapplication.presentation.utils.event.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompareCoinActivity :
    BaseActivity<ActivityCompareCoinBinding, CompareCoinViewModel>(R.layout.activity_compare_coin) {

    override val viewModel by viewModel<CompareCoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            compareCoinRv.adapter = CompareCoinAdapter()
        }
        viewModel.coinList.observe(this, Observer {
            (binding.compareCoinRv.adapter as CompareCoinAdapter).setCoinList(it)
        })
        viewModel.isDataLoadingError.observe(this, EventObserver {
            if (it.second) {
                toastM("${it.first}")
            }
        })
    }

}