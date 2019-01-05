package com.svprdga.mvppaymentflowdemo.presentation.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.presentation.custom.ContactClickListener
import com.svprdga.mvppaymentflowdemo.presentation.custom.ContactListAdapter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IContactsPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IContactsView
import kotlinx.android.synthetic.main.fragment_contacts.*
import javax.inject.Inject

const val TAG_CONTACTS = "contacts";

class ContactsFragment : BaseFragment(), IContactsView {

    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var presenter: IContactsPresenter

    // ****************************************** VARS ***************************************** //

    private var adapter: ContactListAdapter? = null

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

    // ************************************* PUBLIC METHODS ************************************ //

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    override fun showContacts(contacts: List<Contact>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = ContactListAdapter(contacts, entryListener)
        recyclerView.adapter = adapter
        scrollView.visibility = View.VISIBLE
    }

    // ************************************** UI LISTENERS ************************************* //

    private val entryListener: ContactClickListener
        get () = object : ContactClickListener {
            override fun onClick(contact: Contact?) {
                // TODO
            }
        }

}
