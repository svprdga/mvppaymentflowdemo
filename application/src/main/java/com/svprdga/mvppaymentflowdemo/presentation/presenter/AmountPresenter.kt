package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IAmountPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IAmountView
import com.svprdga.mvppaymentflowdemo.util.Logger

class AmountPresenter(logger: Logger): BasePresenter(logger), IAmountPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IAmountView? = null

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IAmountView) {
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