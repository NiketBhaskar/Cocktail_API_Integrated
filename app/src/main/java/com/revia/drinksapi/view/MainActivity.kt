package com.revia.drinksapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.revia.drinksapi.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFragment()
        setContentView(R.layout.activity_main)
    }
    private fun openFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = HomeFragment()
        fragmentTransaction.replace(R.id.HostFragment, fragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
}