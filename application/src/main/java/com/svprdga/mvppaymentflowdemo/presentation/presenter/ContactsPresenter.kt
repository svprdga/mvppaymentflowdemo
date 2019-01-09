package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.data.datasource.ContactDataSource
import com.svprdga.mvppaymentflowdemo.data.network.client.ApiClient
import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.*
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IContactsPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IContactsView
import com.svprdga.mvppaymentflowdemo.util.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

private const val API_CHAR_LIMIT = 50

class ContactsPresenter(
    logger: Logger,
    private val mainBus: MainBus,
    private val contactDataSource: ContactDataSource,
    private val contactsBus: ContactsBus,
    private val apiClient: ApiClient
) : BasePresenter(logger), IContactsPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IContactsView? = null
    private var contacts = ArrayList<Contact>()

    // ************************************* CALLBACK VARS ************************************* //

    private val mainDisposable: DisposableObserver<MainData>
        get() = object : DisposableObserver<MainData>() {
            override fun onNext(result: MainData) {
                if (result.event == MainEvent.LOAD_CONTACTS) loadContacts()
                else if (result.event == MainEvent.UNSELECT_ALL) unselectAllContacts()
            }

            override fun onError(e: Throwable) {
                // nothing.
            }

            override fun onComplete() {
                // nothing.
            }
        }

    private val disposable: DisposableObserver<List<Contact>> =
        object : DisposableObserver<List<Contact>>() {

            val mainList = ArrayList<Contact>()

            override fun onNext(list: List<Contact>) {
                mainList.addAll(list)
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                view?.showErrorLayout()
            }

            override fun onComplete() {
                view?.hideLoading()
                view?.showContacts(mainList.sorted())
            }
        }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IContactsView) {
        this.view = view

        mainBus.getData().subscribe(mainDisposable)
    }

    override fun unBind() {
        view = null

        mainDisposable.dispose()
        disposable.dispose()
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
            .mergeWith(apiClient.getCharacters(API_CHAR_LIMIT))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposable)
    }

    private fun unselectAllContacts() {
        view?.unselectAllViews()
        contacts = ArrayList()
        contactsBus.setData(contacts)
    }

}
