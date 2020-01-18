package com.study.myapplication.feature

import android.os.Bundle
import com.study.myapplication.BR
import com.study.myapplication.R
import com.study.myapplication.base.BaseActivity
import com.study.myapplication.base.SimpleRecyclerView
import com.study.myapplication.databinding.ActivityCompareCoinBinding
import com.study.myapplication.databinding.ItemCompareCoinInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompareCoinActivity :
    BaseActivity<ActivityCompareCoinBinding, CompareCoinViewModel>(R.layout.activity_compare_coin) {

    override val viewModel by viewModel<CompareCoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            coinRv.adapter =
                object : SimpleRecyclerView.Adapter<CompareCoinInfoVo, ItemCompareCoinInfoBinding>(
                    layoutRes = R.layout.item_compare_coin_info,
                    bindingVariableId = BR.item,
                    onClicked = { item, view, position -> }
                ) {}
        }
    }

}