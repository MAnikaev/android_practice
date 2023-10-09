package com.itis.homework

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.homework.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private val fragmentContainer = R.id.main_container_fcv
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            goToScreen(
                ActionType.Add,
                FirstFragment.getInstance("Первый фрагмент"),
                isAddToBackStack = false
            )
        }
    }
    override fun goToScreen(
        action: ActionType,
        destination: BaseFragment,
        tag: String?,
        isAddToBackStack: Boolean,
    ) {
        supportFragmentManager.beginTransaction().apply {
            if(isAddToBackStack){
                addToBackStack(null)
            }
            when(action) {
                ActionType.Replace -> {
                    replace(fragmentContainer, destination, tag)
                }
                ActionType.Remove -> {
                    remove(destination)
                }
                ActionType.Add -> {
                    add(fragmentContainer, destination, tag)
                }
                ActionType.Hide -> {
                    hide(destination)
                }
                ActionType.Show -> {
                    show(destination)
                }
            }
            commit()
        }
    }
}