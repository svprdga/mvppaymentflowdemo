package com.svprdga.mvppaymentflowdemo.data.network.client

import com.svprdga.mvppaymentflowdemo.data.network.abstraction.IApi
import com.svprdga.mvppaymentflowdemo.data.network.rx.SchedulersProvider
import com.svprdga.mvppaymentflowdemo.domain.extra.Mockable
import retrofit2.Retrofit

@Mockable
class ApiClient(private val retrofit: Retrofit,
                private val schedulers: SchedulersProvider
) {

    private val api: IApi = retrofit.create(IApi::class.java)

}