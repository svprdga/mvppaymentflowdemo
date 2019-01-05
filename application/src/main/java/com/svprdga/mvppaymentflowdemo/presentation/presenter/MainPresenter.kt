package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.*
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.ButtonState
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IMainView
import com.svprdga.mvppaymentflowdemo.util.Logger
import com.svprdga.mvppaymentflowdemo.util.PermissionHelper
import com.svprdga.mvppaymentflowdemo.util.TextProvider
import io.reactivex.observers.DisposableObserver

enum class Screen {
    CONTACTS,
    AMOUNT,
    TOTAL
}

class MainPresenter(
    logger: Logger,
    private val permissionHelper: PermissionHelper,
    private val mainBus: MainBus,
    private val textProvider: TextProvider,
    private val contactsBus: ContactsBus)
    : BasePresenter(logger), IMainPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IMainView? = null
    private var screen: Screen = Screen.CONTACTS
    private var selectedContacts = ArrayList<Contact>()

    // ************************************* CALLBACK VARS ************************************* //

    private val contactsDisposable: DisposableObserver<ArrayList<Contact>>
    get() = object : DisposableObserver<ArrayList<Contact>>() {
        override fun onNext(contacts: ArrayList<Contact>) {
            selectedContacts = contacts

            if (selectedContacts.isEmpty()) {
                view?.setNextButtonState(ButtonState.DISABLED)
            } else {
                view?.setNextButtonState(ButtonState.ENABLED)
            }
        }

        override fun onError(e: Throwable) {
            // nothing.
        }

        override fun onComplete() {
            // nothing
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IMainView) {
        this.view = view

        if (permissionHelper.isReadContactsPermisionGranted) {
            readContactsPermissionGranted()
        } else {
            view.askForReadContactsPermission()
        }

        contactsBus.getData().subscribe(contactsDisposable)

        // Initialize bottom navigation bar.
        view?.setBackButtonState(ButtonState.INVISIBLE)
        view?.setNextButtonState(ButtonState.DISABLED)
        view?.setStatusText(textProvider.contactStatus)
    }

    override fun unBind() {
        view = null

        contactsDisposable.dispose()
    }

    override fun onStartView() {
        log.debug("Start view.")
    }

    override fun onStopView() {
        log.debug("Stop view.")
    }

    override fun readContactsPermissionGranted() {
        view?.hidePermissionDeniedLayout()
        mainBus.setEvent(MainEvent.LOAD_CONTACTS)
        view?.showMainLayouts()
    }

    override fun readContactsPermissionDenied() {
        view?.showPermissionDeniedLayout()
    }

    override fun askForContactsPermissionClick() {
        view?.askForReadContactsPermission()
    }

    override fun backPressed() {
        when(screen) {
            Screen.CONTACTS -> {
                if (selectedContacts.isEmpty()) {
                    view?.finish()
                } else {
                    mainBus.setEvent(MainEvent.UNSELECT_ALL)
                }
            }
            else -> view?.finish()
        }
    }

    override fun clickNext() {
        when(screen) {
            Screen.CONTACTS -> goToAmountScreen()
        }
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun goToAmountScreen(){
        view?.animateContactsToAmount()
    }

}