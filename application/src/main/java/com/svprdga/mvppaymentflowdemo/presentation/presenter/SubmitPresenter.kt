package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.ISubmitPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.ISubmitView
import com.svprdga.mvppaymentflowdemo.util.Logger

class SubmitPresenter(logger: Logger): BasePresenter(logger), ISubmitPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: ISubmitView? = null

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: ISubmitView) {
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