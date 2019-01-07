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

data class ResultContact(val contact: Contact, val amount: Float)

class ResultViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {

    // ****************************************** VARS ***************************************** //

    private val mainLayout: LinearLayout =
            view.findViewById(R.id.customResultEntry_mainLinearLayout)
    private val roundedImageView: RoundedImageView =
            view.findViewById(R.id.customResultEntry_avatarRoundedImageView)
    private val avatarImageView: ImageView =
            view?.findViewById(R.id.customResultEntry_avatarImageView)
    private val nameTextView: TextView =
            view?.findViewById(R.id.customResultEntry_nameTextView)
    private val amountTextView: TextView =
            view?.findViewById(R.id.customResultEntry_amountTextView)
    private val dividerView: View =
            view?.findViewById(R.id.customResultEntry_bottomDividerView)

    // ************************************* PUBLIC METHODS ************************************ //

    /**
     * Display the bottom line divider.
     */
    fun showBottomDivider() {
        dividerView.visibility = View.VISIBLE
    }

    /**
     * Initialize this view.
     * @param contact [Contact] to display.
     * @param amount Amount of money he/she owns.
     */
    fun initializeView(contact: Contact, amount: Float) {

        nameTextView.text = contact.name
        amountTextView.text = amount.toString()

        if (contact.avatarPath != null) {

            val uri = Uri.parse(contact.avatarPath)
            roundedImageView.setImageURI(uri)
            roundedImageView.visibility = View.VISIBLE
            avatarImageView.visibility = View.GONE
        } else {
            avatarImageView.visibility = View.VISIBLE
            roundedImageView.visibility = View.GONE
        }
    }
}

class ResultListAdapter(
    private val contacts: List<ResultContact>)
    : RecyclerView.Adapter<ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_result_entry, parent, false)
        return ResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = contacts[position]
        holder.initializeView(result.contact, result.amount)

        if (position < contacts.size-1) holder.showBottomDivider()
    }

}