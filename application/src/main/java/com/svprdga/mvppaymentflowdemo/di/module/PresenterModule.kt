package com.svprdga.mvppaymentflowdemo.di.module

import com.svprdga.mvppaymentflowdemo.data.datasource.ContactDataSource
import com.svprdga.mvppaymentflowdemo.data.network.client.ApiClient
import com.svprdga.mvppaymentflowdemo.di.annotation.PerUiComponent
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.AmountBus
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
                             mainBus: MainBus, textProvider: TextProvider, contactsBus: ContactsBus,
                             amountBus: AmountBus): IMainPresenter {
        return MainPresenter(logger, permissionHelper, mainBus, textProvider, contactsBus,
            amountBus)
    }

    @Provides
    @PerUiComponent
    fun provideContactPresenter(logger: Logger, mainBus: MainBus,
                                contactDataSource: ContactDataSource, contactsBus: ContactsBus,
                                apiClient: ApiClient): IContactsPresenter {
        return ContactsPresenter(logger, mainBus, contactDataSource, contactsBus, apiClient)
    }

    @Provides
    @PerUiComponent
    fun provideAmountPresenter(logger: Logger, amountBus: AmountBus): IAmountPresenter {
        return AmountPresenter(logger, amountBus)
    }

    @Provides
    @PerUiComponent
    fun provideSubmitPresenter(logger: Logger, mainBus: MainBus): ISubmitPresenter {
        return SubmitPresenter(logger, mainBus)
    }
}