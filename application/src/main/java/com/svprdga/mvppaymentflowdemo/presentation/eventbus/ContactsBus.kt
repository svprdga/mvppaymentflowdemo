package com.svprdga.mvppaymentflowdemo.presentation.eventbus

import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ContactsBus {

    // ****************************************** VARS ***************************************** //

    private val subject: PublishSubject<ArrayList<Contact>> = PublishSubject.create()

    // ************************************* PUBLIC METHODS ************************************ //

    fun setData(contacts: ArrayList<Contact>) {
        subject.onNext(contacts)
    }

    fun getData(): Observable<ArrayList<Contact>> {
        return subject
    }

}