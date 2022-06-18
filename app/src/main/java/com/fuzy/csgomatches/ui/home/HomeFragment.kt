package com.fuzy.csgomatches.ui.home

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.databinding.FragmentHomeBinding
import com.fuzy.csgomatches.ui.home.adapter.LoaderMatchStateAdapter
import com.fuzy.csgomatches.ui.home.adapter.MatchPagedAdapter
import com.fuzy.csgomatches.util.BaseFragment
import com.fuzy.csgomatches.util.showInfoAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.security.interfaces.RSAKey

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var stateAdapter: LoaderMatchStateAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMatches().observe(requireActivity()) {
            lifecycleScope.launch {
                viewModel.adapter.submitData(it)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun baseSetupToolbar() {
        super.baseSetupToolbar()
        binding.layoutAppBarHome.textViewToolbarTitle.run {
            text = getString(R.string.home_app_bar_title)
            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.textsize_huge)
            )
        }
    }

    override fun baseSetupDataBindings() {
        super.baseSetupDataBindings()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun baseSetupAdapters() {
        super.baseSetupAdapters()
        stateAdapter = LoaderMatchStateAdapter{ viewModel.adapter.retry() }
        binding.recyclerViewHome.adapter = viewModel.adapter.withLoadStateFooter(
            footer = stateAdapter
        )
    }

    override fun baseSetupObservers() {
        super.baseSetupObservers()

        viewModel.homeState.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { state ->
                when(state){
                    is HomeViewModel.HomeStates.GetMatchInDetailSuccessfully -> {
                        findNavController()
                            .navigate(HomeFragmentDirections
                                .actionHomeFragmentToMatchDetailsFragment(
                                        matchDetail = state.matchInDetail
                                )
                            )
                    }
                    is HomeViewModel.HomeStates.HomeAlertDialog -> showInfoAlertDialog(
                        message = getString(state.msg ?: R.string.an_error_occurred_try_again),
                        buttonMessage = getString(state.buttonMessage ?: R.string.all_ok_button),
                        actionPos = state.posButtonAction
                    )
                }
            }
        }
    }

    // to prevent memory leaks
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}