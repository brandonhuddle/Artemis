package com.brandonhuddle.historynav

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * NOTE: I originally wanted to replicate the iOS style of navigation completely where you could
 * use only the edges for going back and give the rest of the center of the screen to whatever is
 * being displayed. It turns out Google doesn't allow you to disable the gestures for going back.
 * It deceivingly offers `setSystemGestureExclusionRects` which acts like it is the perfect
 * solution except for the one gotcha: if you try to exclude more than 200dp then it will ignore
 * your request past 200dp from the bottom. Genius Google, just Genius. So until Google decides to
 * stop treating Android developers like they're idiots we have to just put up with the absolute
 * garbage that the Android 10+ gesture navigation is. I'm honestly so tired of Google copying Apple
 * but somehow making their copy worse. If you're going to copy then copy to a T, don't gimp the
 * copy. I assume they will finally copy Apple 100% within the coming year or two based off past
 * experiences of when they first copied Apple's navigation but used the pill design that was so
 * poorly implemented you couldn't go to the home screen by tapping the home screen (literally
 * the first thing I tried). It also had completely annoying UX with how switching between
 * activities again felt like being told what to do rather than you telling it what to do. They
 * later came up with what we have now, the 100% clone of iOS for bottom navigation but still
 * "look at us, we're definitely not copying!" solution for the gesture back.
 *
 * Rant over.
 */
class HistoryNavigationView(
    context: Context,
    attrs: AttributeSet?
): ViewPager(context, attrs) {
    private lateinit var historyAdapter: HistoryAdapter
    // TODO: Uncomment when Google comes to their senses about gesture navigation.
//    private val viewBounds = Rect()
//    private val viewBoundsExclusion = listOf(viewBounds)

    init {
        setPageTransformer(false, PageTransformer())
    }

    fun setFragmentManager(fragmentManager: FragmentManager) {
        historyAdapter = HistoryAdapter(fragmentManager)
        this.adapter = historyAdapter
    }

    fun setMainFragment(fragment: HistoryFragment) {
        historyAdapter.setMainFragment(fragment)
        currentItem = 0
    }

    fun showFragment(after: HistoryFragment, fragment: HistoryFragment) {
        historyAdapter.addFragment(after, fragment)
        currentItem = historyAdapter.count - 1
    }

    // TODO: Uncomment when Google comes to their senses about gesture navigation.
//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        super.onLayout(changed, left, top, right, bottom)
//
//        if (changed) {
//            viewBounds.set(left, top, right, bottom)
//            ViewCompat.setSystemGestureExclusionRects(this, viewBoundsExclusion)
//        }
//    }

    fun goBackOne(): Boolean {
        return if (currentItem == 0) {
            false
        } else {
            currentItem -= 1
            true
        }
    }

    private inner class HistoryAdapter(
        fragmentManager: FragmentManager
    ): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        val fragments = ArrayList<HistoryFragment>()

        fun setMainFragment(fragment: HistoryFragment) {
            fragments.clear()
            fragments.add(fragment)
            notifyDataSetChanged()
        }

        fun addFragment(after: HistoryFragment, fragment: HistoryFragment) {
            var afterIndex = -1

            for (i in 0 until fragments.size) {
                if (fragments[i] == after) {
                    afterIndex = i
                    break
                }
            }

            if (afterIndex == -1) {
                throw NoSuchElementException("`after` was not found in the fragment manager!")
            }

            // TODO: Remove all items after `afterIndex` so from `afterIndex + 1` to `size`
            while (afterIndex + 1 < fragments.size) {
                fragments.removeAt(fragments.size - 1)
            }

            fragments.add(fragment)
            notifyDataSetChanged()
        }

        override fun getCount(): Int =
            fragments.size

        override fun getItem(position: Int): Fragment =
            fragments[position]
    }

    class PageTransformer : ViewPager.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                when {
                    position < -1 -> { // [-Infinity,-1)
                        alpha = 0f
                    }
                    position <= 0 -> { // [-1,0]
                        alpha = 1f
                        translationX = pageWidth * -position
                        translationZ = -1f
                        scaleX = 1f
                        scaleY = 1f
                    }
                    position <= 1 -> { // (0,1]
                        alpha = 1f
                        translationZ = 0f
                        scaleX = 1f
                        scaleY = 1f
                    }
                    else -> { // (1,+Infinity]
                        alpha = 0f
                    }
                }
            }
        }
    }
}