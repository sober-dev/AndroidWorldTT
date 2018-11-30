package com.testevents.androidworld.ui.activities

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    private fun shouldDisplayHomeUp() {
        val canGoBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar!!.setDisplayHomeAsUpEnabled(canGoBack)
    }
}
