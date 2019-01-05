package com.svprdga.mvppaymentflowdemo.presentation.presenter.view

interface IMainView : IView {

    fun askForReadContactsPermission()

    fun showPermissionDeniedLayout()

}
