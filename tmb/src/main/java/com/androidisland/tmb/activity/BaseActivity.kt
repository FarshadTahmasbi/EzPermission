package com.androidisland.tmb.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutRes() != 0) {
            setContentView(layoutRes())
        }
        initObjects(savedInstanceState)
        initViews(savedInstanceState)
    }

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected open fun initViews(savedInstanceState: Bundle?) {}

    protected open fun initObjects(savedInstanceState: Bundle?) {}

}