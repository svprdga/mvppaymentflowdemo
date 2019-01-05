package com.svprdga.mvppaymentflowdemo.presentation.presenter.view

import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import java.util.*

interface IContactsView : IView {

    fun showLoading()

    fun hideLoading()

    fun showContacts(contacts: List<Contact>)

}
