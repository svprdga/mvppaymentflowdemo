package com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction

import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IContactsView

interface IContactsPresenter : IPresenter<IContactsView> {

    fun contactSelected(contact: Contact)

    fun contactUnselected(contact: Contact)

}
