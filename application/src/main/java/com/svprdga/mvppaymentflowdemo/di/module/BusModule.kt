package com.svprdga.mvppaymentflowdemo.di.module

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
}