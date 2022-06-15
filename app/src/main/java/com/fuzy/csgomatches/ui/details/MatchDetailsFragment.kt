package com.fuzy.csgomatches.ui.details

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.databinding.FragmentMatchDetailsBinding
import com.fuzy.csgomatches.ui.details.adapter.MatchDetailsAdapter
import com.fuzy.csgomatches.util.BaseFragment
import com.fuzy.csgomatches.util.GlobalConstants.Companion.FIRST_OPPONENT
import com.fuzy.csgomatches.util.GlobalConstants.Companion.SECOND_OPPONENT
import com.fuzy.csgomatches.util.setupToolbarWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchDetailsFragment : BaseFragment() {

    private val args: MatchDetailsFragmentArgs by navArgs()

    private var _binding: FragmentMatchDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArgs()
    }

    override fun baseSetupToolbar() {
        super.baseSetupToolbar()
        binding.run {
            layoutAppBarDetails.setupToolbarWithNavController(this@MatchDetailsFragment)
            layoutAppBarDetails.textViewToolbarTitle.run {
                "${args.matchDetail.league.name} | ${args.matchDetail.serie.name}".let {
                    text = it
                }
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.textsize_large)
                )
                gravity = Gravity.CENTER
            }
        }
    }

    private fun setupArgs() {
        binding.run {
            args.matchDetail.opponents[FIRST_OPPONENT].opponent.let { firstOpponent ->
                args.matchDetail.opponents[SECOND_OPPONENT].opponent.let { secondOpponent ->
                    recyclerViewMatchDetails.adapter = MatchDetailsAdapter(
                        firstOpponent.players,
                        secondOpponent.players
                    )
                    RequestOptions()
                        .placeholder(R.drawable.cs_go_placeholder)
                        .error(R.drawable.cs_go_placeholder)
                        .dontAnimate().also {
                            Glide.with(requireContext())
                                .load(firstOpponent.image)
                                .apply(it)
                                .into(layoutDetailsVersusTeams.shapeableImageViewVersusFirstOpponent)

                            Glide.with(requireContext())
                                .load(secondOpponent.image)
                                .apply(it)
                                .into(layoutDetailsVersusTeams.shapeableImageViewVersusSecondOpponent)
                        }

                    layoutDetailsVersusTeams.textViewFirstOpponentName.text = firstOpponent.name
                    layoutDetailsVersusTeams.textViewSecondOpponentName.text = secondOpponent.name
                }
            }
        }
    }

    // to prevent memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}