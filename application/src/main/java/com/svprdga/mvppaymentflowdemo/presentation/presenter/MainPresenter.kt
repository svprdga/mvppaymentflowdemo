package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainBus
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainEvent
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IMainView
import com.svprdga.mvppaymentflowdemo.util.Logger
import com.svprdga.mvppaymentflowdemo.util.PermissionHelper

class MainPresenter(
    logger: Logger,
    private val permissionHelper: PermissionHelper,
    private val mainBus: MainBus)
    : BasePresenter(logger), IMainPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IMainView? = null

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IMainView) {
        this.view = view

        if (permissionHelper.isReadContactsPermisionGranted) {
            readContactsPermissionGranted()
        } else {
            view.askForReadContactsPermission()
        }

    }

    override fun unBind() {
        view = null
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

}