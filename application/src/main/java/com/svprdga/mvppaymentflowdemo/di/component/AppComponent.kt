package com.svprdga.birthdays.di.component

import com.svprdga.mvppaymentflowdemo.di.module.*
import com.svprdga.mvppaymentflowdemo.presentation.ui.activity.BaseActivity
import com.svprdga.mvppaymentflowdemo.presentation.ui.fragment.BaseFragment

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [AppModule::class, DataModule::class, UtilModule::class])
interface AppComponent {

    fun plusUiComponent(
        uiComponentModule: UiComponentModule,
        presenterModule: PresenterModule): UiComponent

    fun inject(target: BaseActivity)
    fun inject(target: BaseFragment)
}
