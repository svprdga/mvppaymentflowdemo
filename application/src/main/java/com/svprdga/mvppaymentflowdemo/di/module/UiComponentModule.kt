package com.svprdga.mvppaymentflowdemo.di.module

import android.app.Activity
import com.svprdga.mvppaymentflowdemo.di.annotation.PerUiComponent
import com.svprdga.mvppaymentflowdemo.presentation.ui.activity.BaseActivity
import com.svprdga.mvppaymentflowdemo.presentation.ui.fragment.BaseFragment
import com.svprdga.mvppaymentflowdemo.util.Logger
import dagger.Module
import dagger.Provides

@Module
class UiComponentModule(private val baseActivity: BaseActivity?,
                        private val baseFragment: BaseFragment?,
                        private val tag: String) {

    // ************************************* PUBLIC METHODS ************************************ //

    @Provides
    @PerUiComponent
    fun provideActivity(): Activity? {
        return baseActivity
    }

    @Provides
    @PerUiComponent
    fun provideBaseActivty(): BaseActivity? {
        return baseActivity
    }

    @Provides
    @PerUiComponent
    fun provideBaseFragment(): BaseFragment? {
        return baseFragment
    }


    @Provides
    @PerUiComponent
    fun provideAndroidLogger(): Logger {
        return Logger(tag)
    }
}
