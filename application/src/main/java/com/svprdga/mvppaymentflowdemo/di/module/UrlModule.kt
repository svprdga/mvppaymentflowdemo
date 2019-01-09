package com.svprdga.mvppaymentflowdemo.di.module

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

const val API_URL_NAME = "apiUrl"

@Module
class UrlModule {

    private val apiUrl = "https://gateway.marvel.com:443/"

    @Provides
    @Named(API_URL_NAME)
    @Singleton
    fun provideApiUrl(): String {
        return apiUrl
    }
}