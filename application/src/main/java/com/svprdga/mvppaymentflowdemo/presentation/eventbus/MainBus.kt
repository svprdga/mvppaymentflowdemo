package com.svprdga.mvppaymentflowdemo.presentation.eventbus

import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


enum class MainEvent {
    LOAD_CONTACTS,
    UNSELECT_ALL,
    SHOW_RESULTS
}

data class MainData(
    val event: MainEvent,
    val contacts: List<Contact>? = null,
    val amount: Float? = null
)

class MainBus {

    // ****************************************** VARS ***************************************** //

    private val subject: PublishSubject<MainData> = PublishSubject.create()

    // ************************************* PUBLIC METHODS ************************************ //

    fun setData(event: MainData) {
        subject.onNext(event)
    }

    fun getData(): Observable<MainData> {
        return subject
    }

}