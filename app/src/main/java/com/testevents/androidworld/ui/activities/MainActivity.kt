package com.testevents.androidworld.ui.activities

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.testevents.androidworld.R
import com.testevents.androidworld.ui.fragments.ContentFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            return
        }

        window.statusBarColor = getColor(R.color.darkOrange)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.orange)))
        supportActionBar?.elevation = 0f

        supportFragmentManager.addOnBackStackChangedListener {
            shouldDisplayHomeUp()
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ContentFragment())
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun fullScreenMode(enabled: Boolean) {
        if (enabled) {
            supportActionBar?.hide()
            window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            supportActionBar?.show()
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        }
    }

    private fun shouldDisplayHomeUp() {
        val canGoBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(canGoBack)
    }
}
