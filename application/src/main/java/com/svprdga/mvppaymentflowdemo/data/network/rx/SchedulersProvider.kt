package com.svprdga.mvppaymentflowdemo.data.network.rx

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}