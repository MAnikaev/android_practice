package com.itis.homework.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.R
import com.itis.homework.databinding.ActivityMainBinding
import com.itis.homework.model.BaseActivity
import com.itis.homework.ui.fragments.StartFragment
import com.itis.homework.utils.ActionType

class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private val containerId: Int = R.id.main_container
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToScreen(ActionType.Add, StartFragment.getInstance())
    }

    override fun goToScreen(action: ActionType, destination: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            when(action) {
                ActionType.Replace -> {
                    replace(containerId, destination, tag)
                }
                ActionType.Remove -> {
                    remove(destination)
                }
                ActionType.Add -> {
                    add(containerId, destination, tag)
                }
            }
            commit()
        }
    }
}