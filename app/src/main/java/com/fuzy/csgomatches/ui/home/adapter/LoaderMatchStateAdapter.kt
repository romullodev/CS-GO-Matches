package com.fuzy.csgomatches.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fuzy.csgomatches.databinding.ItemMatchLoaderBinding
import com.fuzy.csgomatches.util.setVisibleOrGone

class LoaderMatchStateAdapter   (private val retry: () -> Unit) :
    LoadStateAdapter<LoaderMatchStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.from(parent, retry)
    }

    class LoaderViewHolder private constructor(
        private val binding: ItemMatchLoaderBinding,
        retry: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonLoaderRetry.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            binding.run {
                buttonLoaderRetry.setVisibleOrGone(loadState !is LoadState.Loading)
                textViewLoaderError.setVisibleOrGone(loadState !is LoadState.Loading)
                progressBarLoader.setVisibleOrGone((loadState is LoadState.Loading))
            }
        }

        companion object {
            fun from(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMatchLoaderBinding.inflate(layoutInflater, parent, false)
                return LoaderViewHolder(binding, retry)
            }
        }
    }
}