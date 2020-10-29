package com.brandonhuddle.historynav

import androidx.fragment.app.Fragment

// `HistoryFragment` is the base of ALL fragments that will be used within a `HistoryNavigationView`
abstract class HistoryFragment: Fragment() {
    private lateinit var historyNavigationView: HistoryNavigationView

    fun setHistoryNavigationView(historyNavigationView: HistoryNavigationView) {
        this.historyNavigationView = historyNavigationView
    }

    protected fun showFragment(fragment: HistoryFragment) =
        historyNavigationView.showFragment(this, fragment)
}