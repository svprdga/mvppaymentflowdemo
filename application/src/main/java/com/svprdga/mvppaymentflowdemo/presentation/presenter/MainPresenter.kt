package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.*
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.BulletState
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.ButtonState
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IMainView
import com.svprdga.mvppaymentflowdemo.util.Logger
import com.svprdga.mvppaymentflowdemo.util.PermissionHelper
import com.svprdga.mvppaymentflowdemo.util.TextProvider
import io.reactivex.observers.DisposableObserver

enum class Screen {
    CONTACTS,
    AMOUNT,
    SUBMIT
}

class MainPresenter(
    logger: Logger,
    private val permissionHelper: PermissionHelper,
    private val mainBus: MainBus,
    private val textProvider: TextProvider,
    private val contactsBus: ContactsBus,
    private val amountBus: AmountBus
) : BasePresenter(logger), IMainPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IMainView? = null
    private var screen: Screen = Screen.CONTACTS
    private var selectedContacts = ArrayList<Contact>()
    private var inputAmount = 0f

    // ************************************* CALLBACK VARS ************************************* //

    private val contactsDisposable: DisposableObserver<ArrayList<Contact>>
        get() = object : DisposableObserver<ArrayList<Contact>>() {
            override fun onNext(contacts: ArrayList<Contact>) {
                selectedContacts = contacts
                navBarContacts()
            }

            override fun onError(e: Throwable) {
                // nothing.
            }

            override fun onComplete() {
                // nothing
            }
        }

    private val amountDisposable: DisposableObserver<AmountData>
        get() = object : DisposableObserver<AmountData>() {
            override fun onNext(data: AmountData) {
                when(data.event) {
                    AmountEvent.SET_AMOUNT -> {
                        data.amount?.let {
                            inputAmount = it
                            navBarAmount()
                        }
                    }
                    AmountEvent.NEXT -> {
                        if (inputAmount > 0f) {
                            goToSubmitScreen()
                        }
                    }
                }

            }

            override fun onError(e: Throwable) {
                // Nothing.
            }

            override fun onComplete() {
                // Nothing.
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
        amountBus.getData().subscribe(amountDisposable)

        // Initialize bottom navigation bar.
        navBarContacts()
    }

    override fun unBind() {
        view = null

        contactsDisposable.dispose()
        amountDisposable.dispose()
    }

    override fun onStartView() {
        log.debug("Start view.")
    }

    override fun onStopView() {
        log.debug("Stop view.")
    }

    override fun readContactsPermissionGranted() {
        view?.hidePermissionDeniedLayout()
        mainBus.setData(MainData(event = MainEvent.LOAD_CONTACTS))
        view?.showMainLayouts()
    }

    override fun readContactsPermissionDenied() {
        view?.showPermissionDeniedLayout()
    }

    override fun askForContactsPermissionClick() {
        view?.askForReadContactsPermission()
    }

    override fun backPressed() {
        when (screen) {
            Screen.CONTACTS -> {
                if (selectedContacts.isEmpty()) {
                    view?.finish()
                } else {
                    mainBus.setData(MainData(event = MainEvent.UNSELECT_ALL))
                }
            }
            else -> view?.finish()
        }
    }

    override fun clickNext() {
        when (screen) {
            Screen.CONTACTS -> goToAmountScreen()
            Screen.AMOUNT -> goToSubmitScreen()
        }
    }

    override fun clickBack() {
        when (screen) {
            Screen.AMOUNT -> returnToContactsScreen()
            Screen.SUBMIT -> returnToAmountScreen()
        }
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun goToAmountScreen() {
        view?.animateContactsToAmount()
        navBarAmount()
        screen = Screen.AMOUNT
    }

    private fun returnToContactsScreen() {
        view?.animateAmountToContacts()
        navBarContacts()
        screen = Screen.CONTACTS
    }

    private fun goToSubmitScreen() {
        mainBus.setData(MainData(
            MainEvent.SHOW_RESULTS, selectedContacts, inputAmount))

        view?.animateAmountToSubmit()
        view?.closeKeyboard()
        navBarSubmit()
        screen = Screen.SUBMIT
    }

    private fun returnToAmountScreen() {
        view?.animateSubmitToAmount()
        navBarAmount()
        screen = Screen.AMOUNT
    }

    /**
     * Set the bottom navigation bar to its correct state for the contacts layout.
     */
    private fun navBarContacts() {
        view?.setBackButtonState(ButtonState.INVISIBLE)
        if (selectedContacts.isEmpty()) {
            view?.setNextButtonState(ButtonState.DISABLED)
        } else {
            view?.setNextButtonState(ButtonState.ENABLED)
        }

        view?.setStatusText(textProvider.contactStatus)

        view?.setContactsBulletState(BulletState.ENABLED)
        view?.setAmountBulletState(BulletState.DISABLED)
        view?.setSubmitBulletState(BulletState.DISABLED)
    }

    /**
     * Set the bottom navigation bar to its correct state for the amount layout.
     */
    private fun navBarAmount() {
        view?.setBackButtonState(ButtonState.ENABLED)
        if (inputAmount > 0) view?.setNextButtonState(ButtonState.ENABLED)
        else view?.setNextButtonState(ButtonState.DISABLED)

        view?.setStatusText(textProvider.amountStatus)

        view?.setContactsBulletState(BulletState.DISABLED)
        view?.setAmountBulletState(BulletState.ENABLED)
        view?.setSubmitBulletState(BulletState.DISABLED)
    }

    /**
     * Set the bottom navigation bar to its correct state for the submit layout.
     */
    private fun navBarSubmit() {
        view?.setBackButtonState(ButtonState.ENABLED)
        view?.setNextButtonState(ButtonState.INVISIBLE)

        view?.setStatusText(textProvider.totalStatus)

        view?.setContactsBulletState(BulletState.DISABLED)
        view?.setAmountBulletState(BulletState.DISABLED)
        view?.setSubmitBulletState(BulletState.ENABLED)
    }

}