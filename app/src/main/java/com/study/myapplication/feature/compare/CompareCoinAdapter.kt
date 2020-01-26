package com.study.myapplication.feature.compare

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.myapplication.databinding.ItemCompareCoinInfoBinding

class CompareCoinAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val compareCoinList = arrayListOf<CompareCoinInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CompareCoinViewHolder(
            ItemCompareCoinInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = compareCoinList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CompareCoinViewHolder).bind(compareCoinList[position])
    }

    inner class CompareCoinViewHolder(private val binding: ItemCompareCoinInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(compareCoinInfo: CompareCoinInfo) {
            binding.run {
                item = compareCoinInfo
            }
        }
    }

    fun setCoinList(list: List<CompareCoinInfo>) {
        compareCoinList.clear()
        compareCoinList.addAll(list)
        notifyDataSetChanged()
    }

}