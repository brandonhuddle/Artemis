package com.brandonhuddle.artemis.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brandonhuddle.artemis.R
import com.brandonhuddle.historynav.HistoryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InboxFragment : HistoryFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
        = inflater.inflate(R.layout.fragment_inbox, container, false)

    companion object {
        @JvmStatic
        fun newInstance() =
            InboxFragment()
    }
}