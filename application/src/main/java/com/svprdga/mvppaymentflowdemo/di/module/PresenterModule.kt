package com.svprdga.mvppaymentflowdemo.di.module

import com.svprdga.mvppaymentflowdemo.di.annotation.PerUiComponent
import com.svprdga.mvppaymentflowdemo.presentation.presenter.AmountPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.ContactPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.MainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.SubmitPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IAmountPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IContactsPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.ISubmitPresenter
import com.svprdga.mvppaymentflowdemo.util.Logger
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    @PerUiComponent
    fun provideMainPresenter(logger: Logger): IMainPresenter {
        return MainPresenter(logger)
    }

    @Provides
    @PerUiComponent
    fun provideContactPresenter(logger: Logger): IContactsPresenter {
        return ContactPresenter(logger)
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