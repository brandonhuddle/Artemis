package com.brandonhuddle.historynav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brandonhuddle.artemis.R
import com.brandonhuddle.historynav.HistoryNavigationView

class HistoryNavigationFragment(
    private val mainFragment: HistoryFragment
) : Fragment() {
    private lateinit var historyNavigation: HistoryNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(R.layout.fragment_history_navigation, container, false)

        historyNavigation = result.findViewById(R.id.history_navigation)
        historyNavigation.setFragmentManager(requireActivity().supportFragmentManager)
        historyNavigation.setMainFragment(mainFragment)
        mainFragment.setHistoryNavigationView(historyNavigation)

        return result
    }

    fun onBackPressed(): Boolean {
        return historyNavigation.goBackOne()
    }
}