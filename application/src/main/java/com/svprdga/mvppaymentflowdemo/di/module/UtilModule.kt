package com.svprdga.mvppaymentflowdemo.di.module

import android.content.Context
import com.svprdga.mvppaymentflowdemo.util.PermissionHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    @Provides
    @Singleton
    fun providePermissionHelper(context: Context): PermissionHelper {
        return PermissionHelper(context)
    }
}