package com.study.myapplication.feature.compare

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.myapplication.databinding.ItemCompareCoinInfoBinding
import com.study.myapplication.databinding.ItemCompareCoinInfoHeaderBinding

class CompareCoinAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val compareCoinList = arrayListOf<CompareCoinInfoVo>()
    private val typeHeader = 0
    private val typeItem = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == typeHeader) {
            CompareCoinHeaderViewHolder(
                ItemCompareCoinInfoHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        } else {
            CompareCoinViewHolder(
                ItemCompareCoinInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) typeHeader else typeItem
    }

    override fun getItemCount(): Int = compareCoinList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == typeItem) {
            (holder as CompareCoinViewHolder).bind(compareCoinList[position - 1])
        }
    }

    inner class CompareCoinViewHolder(private val binding: ItemCompareCoinInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(compareCoinInfo: CompareCoinInfoVo) {
            binding.run {
                item = compareCoinInfo
            }
        }
    }

    inner class CompareCoinHeaderViewHolder(binding: ItemCompareCoinInfoHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setCoinList(list: List<CompareCoinInfoVo>) {
        compareCoinList.clear()
        compareCoinList.addAll(list)
        notifyDataSetChanged()
    }

}