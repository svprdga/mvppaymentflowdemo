package com.svprdga.mvppaymentflowdemo.presentation.custom

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.domain.model.Contact

interface ContactClickListener {
    fun onClick(view: ContactViewHolder, contact: Contact?, selected: Boolean)
}

class ContactViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {

    // ****************************************** VARS ***************************************** //

    var contact: Contact? = null
    var selected: Boolean = false

    // ***************************************** VIEWS ***************************************** //

    private val entryLayout: LinearLayout =
            view.findViewById(R.id.entryLayout)
    private val avatarRoundedImageView: RoundedImageView =
            view.findViewById(R.id.avatarRoundedImageView)
    private val avatarImageView: ImageView =
            view.findViewById(R.id.avatarImageView)
    private val contactNameTextView: TextView =
            view.findViewById(R.id.contactNameTextView)
    private val contactDetailsTextView: TextView =
            view.findViewById(R.id.contactDetailsTextView)
    private val bottomDividerView: View =
            view.findViewById(R.id.bottomDividerView)
    private val selectedView: View =
            view.findViewById(R.id.selectedView)

    // ************************************* PUBLIC METHODS ************************************ //

    /**
     * Display the bottom line divider.
     */
    fun showBottomDivider() {
        bottomDividerView.visibility = View.VISIBLE
    }

    /**
     * Initialize the view.
     * @param contact [Contact] to fetch the data from.
     */
    fun initializeView(contact: Contact, listener: ContactClickListener) {

        this.contact = contact

        contactNameTextView.text = contact.name

        contactDetailsTextView.text = contact.phone

        if (contact.avatarPath != null) {

            val uri = Uri.parse(contact.avatarPath)
            avatarRoundedImageView.setImageURI(uri)
            avatarRoundedImageView.visibility = View.VISIBLE
            avatarImageView.visibility = View.GONE
        } else {
            avatarImageView.visibility = View.VISIBLE
            avatarRoundedImageView.visibility = View.GONE
        }

        entryLayout.setOnClickListener {

            if (selected) unselect()
            else select()

            listener.onClick(this, this.contact, selected)
        }
    }

    /**
     * Mark this entry as selected.
     */
    fun select() {
        if (!selected) {
            selectedView.visibility = View.VISIBLE
            selected = true
        }
    }

    /**
     * Unmark this entry as selected.
     */
    fun unselect() {
        if (selected) {
            selectedView.visibility = View.GONE
            selected = false
        }
    }
}

/**
 * Entry for the contact list.
 */
class ContactListAdapter(
        private val contacts: List<Contact>,
        private val contactClickListener: ContactClickListener)
    : RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_contact_entry, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.initializeView(contact, contactClickListener)

        if (position < contacts.size-1) holder.showBottomDivider()
    }
}
