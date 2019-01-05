package com.svprdga.mvppaymentflowdemo.di.module

import com.svprdga.mvppaymentflowdemo.data.ContactDataSource
import com.svprdga.mvppaymentflowdemo.di.annotation.PerUiComponent
import com.svprdga.mvppaymentflowdemo.presentation.presenter.AmountPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.ContactsPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.MainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.SubmitPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IAmountPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IContactsPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.ISubmitPresenter
import com.svprdga.mvppaymentflowdemo.util.Logger
import com.svprdga.mvppaymentflowdemo.util.PermissionHelper
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    @PerUiComponent
    fun provideMainPresenter(logger: Logger, permissionHelper: PermissionHelper): IMainPresenter {
        return MainPresenter(logger, permissionHelper)
    }

    @Provides
    @PerUiComponent
    fun provideContactPresenter(logger: Logger): IContactsPresenter {
        return ContactsPresenter(logger)
    }

    @Provides
    @PerUiComponent
    fun provideAmountPresenter(logger: Logger): IAmountPresenter {
        return AmountPresenter(logger)
    }

    @Provides
    @PerUiComponent
    fun provideSubmitPresenter(logger: Logger): ISubmitPresenter {
        return SubmitPresenter(logger)
    }
}