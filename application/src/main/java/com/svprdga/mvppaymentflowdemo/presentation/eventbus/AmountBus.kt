package com.svprdga.mvppaymentflowdemo.presentation.eventbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

enum class AmountEvent {
    SET_AMOUNT,
    NEXT
}

data class AmountData(val event: AmountEvent, val amount: Float? = null)

class AmountBus {

    // ****************************************** VARS ***************************************** //

    private val subject: PublishSubject<AmountData> = PublishSubject.create()

    // ************************************* PUBLIC METHODS ************************************ //

    fun setData(amount: AmountData) {
        subject.onNext(amount)
    }

    fun getData(): Observable<AmountData> {
        return subject
    }
}