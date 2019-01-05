package com.svprdga.mvppaymentflowdemo.presentation.eventbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


enum class MainEvent {
    LOAD_CONTACTS
}

class MainBus {

    // ****************************************** VARS ***************************************** //

    private val subject: PublishSubject<MainEvent> = PublishSubject.create()

    // ************************************* PUBLIC METHODS ************************************ //

    fun setEvent(event: MainEvent) {
        subject.onNext(event)
    }

    fun getEvent(): Observable<MainEvent> {
        return subject
    }

}