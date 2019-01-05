package com.svprdga.mvppaymentflowdemo.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IContactsPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IContactsView
import javax.inject.Inject

const val TAG_CONTACTS = "contacts";

class ContactsFragment : BaseFragment(), IContactsView {

    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var presenter: IContactsPresenter

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUiComponent(TAG_CONTACTS).inject(this)
        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.unBind()
    }

}
