package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.presentation.eventbus.AmountBus
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.AmountData
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.AmountEvent
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IAmountPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IAmountView
import com.svprdga.mvppaymentflowdemo.util.Logger
import java.lang.NumberFormatException

class AmountPresenter(
    logger: Logger,
    private val amountBus: AmountBus)
    : BasePresenter(logger), IAmountPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: IAmountView? = null

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: IAmountView) {
        this.view = view
    }

    override fun unBind() {
        view = null
    }

    override fun newValueEntered(value: String) {
        val data = try {
            AmountData(AmountEvent.SET_AMOUNT, value.toFloat())
        } catch (e: NumberFormatException) {
            AmountData(AmountEvent.SET_AMOUNT, 0f)
        }

        amountBus.setData(data)
    }

    override fun keyboardEnterAction() {
        amountBus.setData(
            AmountData(event = AmountEvent.NEXT))
    }

}