package com.fuzy.csgomatches.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.databinding.ItemHomeMatchBinding
import com.fuzy.csgomatches.domain.entities.Match
import com.fuzy.csgomatches.domain.entities.MatchStatusEnum
import com.fuzy.csgomatches.ui.loading.visibleOrGone
import com.fuzy.csgomatches.util.GlobalConstants.Companion.FIRST_OPPONENT
import com.fuzy.csgomatches.util.GlobalConstants.Companion.SECOND_OPPONENT
import com.fuzy.csgomatches.util.formatTimeFromIso8601
import java.util.*

private typealias MatchClickListener = (Match) -> Unit

class MatchPagedAdapter(
    private val listener: MatchClickListener
) : PagingDataAdapter<Match, MatchPagedAdapter.MatchPagedViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Match>() {
            override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MatchPagedViewHolder, position: Int) {
        getItem(position)?.run {
            holder.bindView(this, listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchPagedViewHolder {
        return MatchPagedViewHolder.from(parent)
    }

    class MatchPagedViewHolder private constructor(private val binding: ItemHomeMatchBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(match: Match, action: MatchClickListener) {
            binding.run {
                buttonHomeMatchRunning.visibleOrGone(match.status == MatchStatusEnum.RUNNING)
                buttonHomeMatchOtherStatus.visibleOrGone(match.status != MatchStatusEnum.RUNNING)
                when(match.status){
                    MatchStatusEnum.CANCELED -> {
                        buttonHomeMatchOtherStatus.text = root.context.getString(R.string.home_match_status_canceled)
                    }
                    MatchStatusEnum.NO_STATUS -> {
                        buttonHomeMatchOtherStatus.text = root.context.getString(R.string.home_match_status_no_info)
                    }
                    MatchStatusEnum.RUNNING -> {}
                    MatchStatusEnum.NOT_STARTED -> {
                        buttonHomeMatchOtherStatus.text = match.scheduleAt.formatTimeFromIso8601(root.context)
                    }
                    MatchStatusEnum.FINISHED -> {
                        buttonHomeMatchOtherStatus.text = root.context.getString(R.string.home_match_status_finish)
                    }
                }
                cardViewHomeMatches.setOnClickListener {
                    action(match)
                }
                layoutVersusTeams.textViewFirstOpponentName.text = match.opponents[FIRST_OPPONENT].opponent.name
                layoutVersusTeams.textViewSecondOpponentName.text = match.opponents[SECOND_OPPONENT].opponent.name
                "${match.league.name} | ${match.serie.name}".let {
                    textViewItemHomeLeagueSerieName.text = it
                }

                RequestOptions()
                    .placeholder(R.drawable.cs_go_placeholder)
                    .error(R.drawable.cs_go_placeholder)
                    .dontAnimate().also {
                        Glide.with(root.context)
                            .load(match.opponents[FIRST_OPPONENT].opponent.image)
                            .apply(it)
                            .into(layoutVersusTeams.shapeableImageViewVersusFirstOpponent)

                        Glide.with(root.context)
                            .load(match.opponents[SECOND_OPPONENT].opponent.image)
                            .apply(it)
                            .into(layoutVersusTeams.shapeableImageViewVersusSecondOpponent)

                        Glide.with(root.context)
                            .load(match.league.image)
                            .apply(it)
                            .into(shapeableImageViewHomeMatchLeagueSerie)
                    }
            }
        }

        companion object {
            fun from(parent: ViewGroup): MatchPagedViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHomeMatchBinding.inflate(layoutInflater, parent, false)
                return MatchPagedViewHolder(binding)
            }
        }
    }
}