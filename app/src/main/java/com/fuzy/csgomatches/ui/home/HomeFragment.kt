package com.fuzy.csgomatches.ui.home

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.TextViewBindingAdapter.setTextSize
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.databinding.FragmentHomeBinding
import com.fuzy.csgomatches.ui.home.adapter.LoaderMatchStateAdapter
import com.fuzy.csgomatches.ui.home.adapter.MatchPagedAdapter
import com.fuzy.csgomatches.util.BaseFragment
import com.fuzy.csgomatches.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: MatchPagedAdapter
    private lateinit var stateAdapter: LoaderMatchStateAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

    override fun baseSetupAdapters() {
        super.baseSetupAdapters()
        adapter = MatchPagedAdapter {
            showToast("developing")
        }

        stateAdapter = LoaderMatchStateAdapter{ adapter.retry() }
        binding.recyclerViewHome.adapter = adapter.withLoadStateFooter(
            footer = stateAdapter
        )
    }

    override fun baseSetupObservers() {
        super.baseSetupObservers()
        viewModel.fetchMatches().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    // to prevent memory leaks
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}