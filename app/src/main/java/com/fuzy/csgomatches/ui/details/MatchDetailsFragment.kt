package com.fuzy.csgomatches.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuzy.csgomatches.R
import com.fuzy.csgomatches.databinding.FragmentMatchDetailsBinding

class MatchDetailsFragment : Fragment() {

    private var _binding: FragmentMatchDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_details, container, false)
    }
}