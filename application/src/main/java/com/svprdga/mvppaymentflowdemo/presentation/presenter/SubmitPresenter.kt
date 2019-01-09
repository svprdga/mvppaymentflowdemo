package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.domain.model.ResultContact
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainBus
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainData
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainEvent
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.ISubmitPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.ISubmitView
import com.svprdga.mvppaymentflowdemo.util.Logger
import io.reactivex.observers.DisposableObserver

class SubmitPresenter(
    logger: Logger,
    private val mainBus: MainBus)
    : BasePresenter(logger), ISubmitPresenter {

    // ****************************************** VARS ***************************************** //

    private var view: ISubmitView? = null

    // ************************************* CALLBACK VARS ************************************* //

    private val mainDisposable: DisposableObserver<MainData>
    get() = object : DisposableObserver<MainData>() {
        override fun onNext(result: MainData) {
            if (result.event == MainEvent.SHOW_RESULTS &&
                    result.contacts != null && result.amount != null) {
                calculateAndShowResults(result.contacts, result.amount)
            }
        }

        override fun onError(e: Throwable) {
            // nothing.
        }

        override fun onComplete() {
            // nothing.
        }
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun bind(view: ISubmitView) {
        this.view = view

        mainBus.getData().subscribe(mainDisposable)
    }

    override fun unBind() {
        view = null

        mainDisposable.dispose()
    }

    // ************************************ PRIVATE METHODS ************************************ //

    /**
     * Calculate the total amount each contact must pay, and show the final results
     * in the view.
     */
    private fun calculateAndShowResults(contacts: List<Contact>, amount: Float) {
        val amountEach = amount / contacts.size
        val resultList = mutableListOf<ResultContact>()

        contacts.forEach {
            resultList.add(ResultContact(it, amountEach))
        }

        view?.displayResults(resultList)
        view?.displayTotalAmount(amount)
    }

}