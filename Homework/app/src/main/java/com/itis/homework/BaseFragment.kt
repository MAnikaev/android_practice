package com.itis.homework

import android.view.OrientationEventListener
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {
    abstract fun initViews()
}