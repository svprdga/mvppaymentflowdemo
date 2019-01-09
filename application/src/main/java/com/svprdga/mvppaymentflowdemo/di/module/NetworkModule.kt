package com.svprdga.mvppaymentflowdemo.di.module

import com.google.gson.Gson
import com.svprdga.mvppaymentflowdemo.BuildConfig
import com.svprdga.mvppaymentflowdemo.data.network.client.ApiClient
import com.svprdga.mvppaymentflowdemo.data.network.mapper.Mapper
import com.svprdga.mvppaymentflowdemo.data.network.rx.SchedulersProvider
import com.svprdga.mvppaymentflowdemo.util.CryptoUtils
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        val httpLogInterceptor = HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLogInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLogInterceptor
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named(API_URL_NAME) baseUrl: String,
                        logInterceptor: Interceptor): Retrofit {

        val client = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSchedulers(): SchedulersProvider {
        return SchedulersProvider()
    }

    @Provides
    @Singleton
    fun provideMapper(): Mapper {
        return Mapper()
    }

    @Provides
    @Singleton
    fun provideApiClient(schedulersProvider: SchedulersProvider, retrofit: Retrofit,
                         cryptoUtils: CryptoUtils, mapper: Mapper): ApiClient {
        return ApiClient(retrofit, schedulersProvider, cryptoUtils, mapper)
    }

}