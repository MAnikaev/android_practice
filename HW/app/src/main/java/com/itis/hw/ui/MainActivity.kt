package com.itis.hw.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.hw.R
import com.itis.hw.databinding.ActivityMainBinding
import com.itis.hw.di.ServiceLocator
import com.itis.hw.utils.ParamKeys

class MainActivity : AppCompatActivity() {
    private val containerId: Int = R.id.main_container
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val controller = (supportFragmentManager.findFragmentById(containerId) as NavHostFragment).navController
        NavigationUI.setupWithNavController(binding.mainBnv, controller)

        hideBottomNavigationView()
    }

    fun hideBottomNavigationView() {
        binding.mainBnv.visibility = View.GONE
    }

    fun showBottomNavigationView() {
        binding.mainBnv.visibility = View.VISIBLE
    }
}