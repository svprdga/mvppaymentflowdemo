package com.svprdga.mvppaymentflowdemo.di.module

import com.svprdga.mvppaymentflowdemo.data.datasource.ContactDataSource
import com.svprdga.mvppaymentflowdemo.di.annotation.PerUiComponent
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.ContactsBus
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainBus
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
import com.svprdga.mvppaymentflowdemo.util.TextProvider
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    @PerUiComponent
    fun provideMainPresenter(logger: Logger, permissionHelper: PermissionHelper,
                             mainBus: MainBus, textProvider: TextProvider, contactsBus: ContactsBus): IMainPresenter {
        return MainPresenter(logger, permissionHelper, mainBus, textProvider, contactsBus)
    }

    @Provides
    @PerUiComponent
    fun provideContactPresenter(logger: Logger, mainBus: MainBus,
                                contactDataSource: ContactDataSource, contactsBus: ContactsBus
    ): IContactsPresenter {
        return ContactsPresenter(logger, mainBus, contactDataSource, contactsBus)
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