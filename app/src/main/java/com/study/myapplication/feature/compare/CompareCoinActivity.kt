package com.study.myapplication.feature.compare

import android.os.Bundle
import androidx.lifecycle.Observer
import com.study.myapplication.R
import com.study.myapplication.base.BaseActivity
import com.study.myapplication.databinding.ActivityCompareCoinBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompareCoinActivity :
    BaseActivity<ActivityCompareCoinBinding, CompareCoinViewModel>(R.layout.activity_compare_coin) {

    override val viewModel by viewModel<CompareCoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            coinRv.adapter =
                CompareCoinAdapter()
        }
        viewModel.coinList.observe(this, Observer {
            (binding.coinRv.adapter as CompareCoinAdapter).setCoinList(it)
        })
    }

}