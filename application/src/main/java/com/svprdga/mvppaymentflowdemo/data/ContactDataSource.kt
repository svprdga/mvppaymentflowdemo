package com.svprdga.mvppaymentflowdemo.data

import android.content.Context
import android.provider.ContactsContract
import com.svprdga.mvppaymentflowdemo.domain.extra.Mockable
import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import io.reactivex.Single
import io.reactivex.SingleObserver

object All {

    const val CONTACT_ID = 0
    const val CONTACT_NAME = 1
    const val INDEX_PHONE = 2
    const val INDEX_PHOTO_URI = 3

    val PROJECTION = arrayOf(
        ContactsContract.CommonDataKinds.Event.CONTACT_ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
}

@Mockable
class ContactDataSource(private val context: Context) {

    // ************************************* PUBLIC METHODS ************************************ //

    /**
     * Fetch all contacts.
     * @return A [Single] with a [List] of all [Contact].
     */
    fun getAllContacts(): Single<List<Contact>> {
        return object : Single<List<Contact>>() {
            override fun subscribeActual(observer: SingleObserver<in List<Contact>>) {
                observer.onSuccess(obtainAllContacts())
            }
        }
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun obtainAllContacts(): List<Contact> {
        val cursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            All.PROJECTION,
            null,
            null,
            null)

        val list = mutableListOf<Contact>()

        while (cursor!!.moveToNext()) {
            val id = cursor.getLong(All.CONTACT_ID)
            val name = cursor.getString(All.CONTACT_NAME)
            val phone = cursor.getString(All.INDEX_PHONE)
            val avatarPath = cursor.getString(All.INDEX_PHOTO_URI)

            val contact = Contact(systemId = id, name = name, phone = phone, avatarPath = avatarPath)

            list.add(contact)
        }
        cursor.close()
        return list
    }

}