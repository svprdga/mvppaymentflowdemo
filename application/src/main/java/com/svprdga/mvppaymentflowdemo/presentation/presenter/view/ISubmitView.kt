package com.svprdga.mvppaymentflowdemo.presentation.presenter.view

import com.svprdga.mvppaymentflowdemo.domain.model.ResultContact

interface ISubmitView : IView {

    fun displayResults(results: List<ResultContact>)

}
