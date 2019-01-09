package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.BuildConfig
import com.svprdga.mvppaymentflowdemo.data.datasource.ContactDataSource
import com.svprdga.mvppaymentflowdemo.data.network.client.ApiClient
import com.svprdga.mvppaymentflowdemo.util.SchedulersProvider
import com.svprdga.mvppaymentflowdemo.domain.extra.Values
import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.*
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IContactsPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IContactsView
import com.svprdga.mvppaymentflowdemo.util.Logger
import io.reactivex.observers.DisposableObserver
import java.lang.UnsupportedOperationException
import java.util.*

private const val API_CHAR_LIMIT = 50

class ContactsPresenter(
    logger: Logger,
    private val mainBus: MainBus,
    private val contactDataSource: ContactDataSource,
    private val contactsBus: ContactsBus,
    private val apiClient: ApiClient,
    private val schedulers: SchedulersProvider
) : BasePresenter(logger), IContactsPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IContactsView? = null
    private var contacts = ArrayList<Contact>()

    // ************************************* CALLBACK VARS ************************************* //

    var mainDisposable: DisposableObserver<MainData> =
        object : DisposableObserver<MainData>() {
            override fun onNext(result: MainData) {
                if (result.event == MainEvent.LOAD_CONTACTS) loadContacts()
                else if (result.event == MainEvent.UNSELECT_ALL) unselectAllContacts()
            }

            override fun onError(e: Throwable) {
                // cannot happen
            }

            override fun onComplete() {
                // cannot happen
            }
        }

    var contactsDisposable: DisposableObserver<List<Contact>> =
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
        contactsDisposable.dispose()
    }

    override fun contactSelected(contact: Contact) {
        contacts.add(contact)
        contactsBus.setData(contacts)
    }

    override fun contactUnselected(contact: Contact) {
        contacts.remove(contact)
        contactsBus.setData(contacts)
    }

    // *********************************** PROTECTED METHODS *********************************** //

    /**
     * Get the attached view.
     *
     * Use is restricted to the test environment.
     */
    @Throws(UnsupportedOperationException::class)
    fun getView(): IContactsView? {
        if (BuildConfig.BUILD_TYPE == Values.Development.Environment.AUTO_TEST) {
            return view
        } else {
            throw UnsupportedOperationException()
        }
    }

    /**
     * Set view.
     *
     * Use is restricted to the test environment.
     */
    @Throws(UnsupportedOperationException::class)
    fun setView(view: IContactsView) {
        if (BuildConfig.BUILD_TYPE == Values.Development.Environment.AUTO_TEST) {
            this.view = view
        } else {
            throw UnsupportedOperationException()
        }
    }

    /**
     * Get the list of stored contacts.
     *
     * Use is restricted to the test environment.
     */
    @Throws(UnsupportedOperationException::class)
    fun getContacts(): ArrayList<Contact> {
        if (BuildConfig.BUILD_TYPE == Values.Development.Environment.AUTO_TEST) {
            return contacts
        } else {
            throw UnsupportedOperationException()
        }
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun loadContacts() {

        view?.showLoading()

        contactDataSource.getAllContacts()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .mergeWith(apiClient.getCharacters(API_CHAR_LIMIT))
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(contactsDisposable)
    }

    private fun unselectAllContacts() {
        view?.unselectAllViews()
        contacts = ArrayList()
        contactsBus.setData(contacts)
    }

}
