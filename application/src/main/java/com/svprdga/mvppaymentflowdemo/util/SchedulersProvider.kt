package com.svprdga.mvppaymentflowdemo.util

import com.svprdga.mvppaymentflowdemo.domain.extra.Mockable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Mockable
class SchedulersProvider {

    fun io(): Scheduler {
        return Schedulers.io()
    }

    fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}