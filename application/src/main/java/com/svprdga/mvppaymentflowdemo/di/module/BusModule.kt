package com.svprdga.mvppaymentflowdemo.di.module

import com.svprdga.mvppaymentflowdemo.presentation.eventbus.AmountBus
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.ContactsBus
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BusModule {

    @Provides
    @Singleton
    fun provideMainBus(): MainBus {
        return MainBus()
    }

    @Provides
    @Singleton
    fun provideContactsBus(): ContactsBus {
        return ContactsBus()
    }

    @Provides
    @Singleton
    fun provideAmountBus(): AmountBus {
        return AmountBus()
    }
}