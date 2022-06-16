package com.fuzy.csgomatches.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.databinding.ItemMatchDetailsBinding
import com.fuzy.csgomatches.domain.entities.Player
import com.fuzy.csgomatches.ui.loading.visibleOrGone
import kotlin.math.max

class MatchDetailsAdapter(
    private var firstPlayers: List<Player>,
    private var secondPlayers: List<Player>,
) : RecyclerView.Adapter<MatchDetailsAdapter.MatchDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchDetailsViewHolder {
        return MatchDetailsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MatchDetailsViewHolder, position: Int) {
        holder.bindView(
            if (position < firstPlayers.size) firstPlayers[position] else null,
            if (position < secondPlayers.size) secondPlayers[position] else null,
        )
    }

    override fun getItemCount(): Int {
        return max(firstPlayers.size, secondPlayers.size)
    }

    class MatchDetailsViewHolder private constructor(private val binding: ItemMatchDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(firstPlayer: Player?, secondPlayer: Player?) {
            binding.run {
                cardViewHomeMatchesFirst.visibleOrGone(firstPlayer != null)
                shapeableImageViewMatchDetailsPlayerFirstPlayer.visibleOrGone(firstPlayer != null)
                textViewFirstNickName.visibleOrGone(firstPlayer != null)
                textViewFirstFullNamePlayer.visibleOrGone(firstPlayer != null)

                cardViewHomeMatchesSecond.visibleOrGone(secondPlayer != null)
                shapeableImageViewMatchDetailsPlayerSecondPlayer.visibleOrGone(secondPlayer != null)
                textViewSecondNickName.visibleOrGone(secondPlayer != null)
                textViewSecondFullNamePlayer.visibleOrGone(secondPlayer != null)

                val glideOptions = RequestOptions()
                    .placeholder(R.drawable.person_placeholder)
                    .error(R.drawable.person_placeholder)
                    .dontAnimate()

                firstPlayer?.let {
                    textViewFirstNickName.text = it.nickname
                    textViewFirstFullNamePlayer.text = it.fullName

                    Glide.with(root.context)
                        .load(it.image)
                        .apply(glideOptions)
                        .into(shapeableImageViewMatchDetailsPlayerFirstPlayer)
                }

                secondPlayer?.let {
                    textViewSecondNickName.text = it.nickname
                    textViewSecondFullNamePlayer.text = it.fullName

                    Glide.with(root.context)
                        .load(it.image)
                        .apply(glideOptions)
                        .into(shapeableImageViewMatchDetailsPlayerSecondPlayer)
                }

            }
        }

        companion object {
            fun from(parent: ViewGroup): MatchDetailsViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMatchDetailsBinding.inflate(inflater, parent, false)
                return MatchDetailsViewHolder(binding)
            }
        }
    }
}