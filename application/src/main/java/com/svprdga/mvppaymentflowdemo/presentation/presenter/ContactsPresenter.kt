package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.data.datasource.ContactDataSource
import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.*
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IContactsPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IContactsView
import com.svprdga.mvppaymentflowdemo.util.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ContactsPresenter(
    logger: Logger,
    private val mainBus: MainBus,
    private val contactDataSource: ContactDataSource,
    private val contactsBus: ContactsBus)
    : BasePresenter(logger), IContactsPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IContactsView? = null
    private var contacts = ArrayList<Contact>()

    // ************************************* CALLBACK VARS ************************************* //

    private val mainDisposable: DisposableObserver<MainEvent>
        get() = object : DisposableObserver<MainEvent>() {
            override fun onNext(event: MainEvent) {
                if (event == MainEvent.LOAD_CONTACTS) loadContacts()
                else if (event == MainEvent.UNSELECT_ALL) unselectAllContacts()
            }

            override fun onError(e: Throwable) {
                // nothing.
            }

            override fun onComplete() {
                // nothing.
            }
        }


    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IContactsView) {
        this.view = view

        mainBus.getEvent().subscribe(mainDisposable)
    }

    override fun unBind() {
        view = null

        mainDisposable.dispose()
    }

    override fun onStartView() {
        log.debug("Start view.")
    }

    override fun onStopView() {
        log.debug("Stop view.")
    }

    override fun contactSelected(contact: Contact) {
        contacts.add(contact)
        contactsBus.setData(contacts)
    }

    override fun contactUnselected(contact: Contact) {
        contacts.remove(contact)
        contactsBus.setData(contacts)
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun loadContacts() {

        view?.showLoading()

        contactDataSource.getAllContacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { contacts ->
                view?.hideLoading()
                view?.showContacts(contacts)
            }
    }

    private fun unselectAllContacts() {
        view?.unselectAllViews()
        contacts = ArrayList()
        contactsBus.setData(contacts)
    }

}
