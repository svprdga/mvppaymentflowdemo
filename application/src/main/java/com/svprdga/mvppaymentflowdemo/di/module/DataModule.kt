package com.svprdga.mvppaymentflowdemo.di.module

import android.content.Context
import com.svprdga.mvppaymentflowdemo.data.datasource.ContactDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideContactDataSource(context: Context): ContactDataSource {
        return ContactDataSource(context)
    }
}
