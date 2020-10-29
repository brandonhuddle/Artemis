package com.brandonhuddle.artemis.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.brandonhuddle.artemis.R
import com.brandonhuddle.historynav.HistoryFragment
import com.brandonhuddle.historynav.HistoryNavigationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigation: BottomNavigationView
    private var currentFragment: HistoryNavigationFragment? = null

    // TODO: We need to do the following:
    //        * Cache the fragments (and work towards a custom system for allowing fragments to
    //          keep their own history and allow them to swipe their history away like iOS)
    //        * Implement the basic fragments (Home already has a list but needs the post items)
    //        * We can use `ViewPager` for the history sliding that I want.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)

        bottomNavigation = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(this)

        setCurrentFragment<HomeFragment>(R.id.navigation_home)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                toolbar.title = "Home"
                setCurrentFragment<HomeFragment>(R.id.navigation_home)
                return true
            }
            R.id.navigation_inbox -> {
                toolbar.title = "Inbox"
                setCurrentFragment<InboxFragment>(R.id.navigation_inbox)
                return true
            }
            R.id.navigation_search -> {
                toolbar.title = "Search"
                return true
            }
            R.id.navigation_accounts -> {
                toolbar.title = "Accounts"
                return true
            }
        }

        return false
    }

    private inline fun <reified TFragment : HistoryFragment> setCurrentFragment(tag: Int) {
        var fragment: HistoryNavigationFragment? =
            supportFragmentManager.findFragmentByTag(tag.toString()) as HistoryNavigationFragment?

        if (fragment == null) {
            fragment = HistoryNavigationFragment(TFragment::class.java.newInstance())
        }

        currentFragment = fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag.toString())
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (!currentFragment!!.onBackPressed()) {
            super.onBackPressed()
        }
    }
}