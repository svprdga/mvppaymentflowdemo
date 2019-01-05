package com.svprdga.mvppaymentflowdemo.presentation.ui.application

import android.app.Application
import com.svprdga.birthdays.di.component.AppComponent
import com.svprdga.birthdays.di.component.DaggerAppComponent
import com.svprdga.birthdays.di.component.UiComponent
import com.svprdga.mvppaymentflowdemo.BuildConfig
import com.svprdga.mvppaymentflowdemo.di.module.AppModule
import com.svprdga.mvppaymentflowdemo.domain.extra.Values

class CoreApp : Application() {

    var appComponent: AppComponent? = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        set(appComponent) =
            if (BuildConfig.BUILD_TYPE.equals(Values.Development.Environment.AUTO_TEST, ignoreCase = true)) {
                field = appComponent
            } else {
                throw UnsupportedOperationException("CoreApp#setAppComponent() is only available in a test environment.")
            }

    var uiComponent: UiComponent? = null
        set(uiComponent) =
            if (BuildConfig.BUILD_TYPE.equals(Values.Development.Environment.AUTO_TEST, ignoreCase = true)) {
                field = uiComponent
            } else {
                throw UnsupportedOperationException(
                    "CoreApp#setUiComponent() is only available in a test environment."
                )
            }

}
