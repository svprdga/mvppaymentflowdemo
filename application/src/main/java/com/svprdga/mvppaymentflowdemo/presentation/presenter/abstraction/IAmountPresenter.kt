package com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction

import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IAmountView

interface IAmountPresenter : IPresenter<IAmountView> {

    /**
     * The user changed the value of the amount input.
     */
    fun newValueEntered(value: String)

    /**
     * The keyboard emitted an enter action.
     */
    fun keyboardEnterAction()
}
