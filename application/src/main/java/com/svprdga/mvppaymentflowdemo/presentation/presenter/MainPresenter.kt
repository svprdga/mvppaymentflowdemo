package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IMainView
import com.svprdga.mvppaymentflowdemo.util.Logger

class MainPresenter(logger: Logger): BasePresenter(logger), IMainPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IMainView? = null

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IMainView) {
        this.view = view
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

}