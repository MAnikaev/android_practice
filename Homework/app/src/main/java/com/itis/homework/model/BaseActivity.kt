package com.itis.homework.model

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.itis.homework.utils.ActionType

abstract class BaseActivity : AppCompatActivity() {
    abstract fun goToScreen(action: ActionType, destination: Fragment, tag: String = "", animationEnter: Int? = null, animationExit: Int? = null)
}