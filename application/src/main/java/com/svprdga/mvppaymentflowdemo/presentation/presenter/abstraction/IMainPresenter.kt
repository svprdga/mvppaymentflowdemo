package com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction

import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IMainView

interface IMainPresenter : IPresenter<IMainView> {

    fun readContactsPermissionGranted()

    fun readContactsPermissionDenied()

    /**
     * The user clicked to ask for read contacts permission.
     */
    fun askForContactsPermissionClick()

}