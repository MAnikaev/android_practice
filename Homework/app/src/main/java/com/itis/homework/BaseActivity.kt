package com.itis.homework

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    abstract fun goToScreen(action: ActionType,
                   destination: BaseFragment,
                   tag: String? = null,
                   isAddToBackStack: Boolean = true,)
}