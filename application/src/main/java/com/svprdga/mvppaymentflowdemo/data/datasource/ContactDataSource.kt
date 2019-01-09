package com.svprdga.mvppaymentflowdemo.data.datasource

import android.content.Context
import android.provider.ContactsContract
import com.svprdga.mvppaymentflowdemo.domain.extra.Mockable
import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import io.reactivex.Observable

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

    const val WHERE = "${ContactsContract.Data.MIMETYPE} = ?"

    val SELECTION = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
}

@Mockable
class ContactDataSource(private val context: Context) {

    // ************************************* PUBLIC METHODS ************************************ //

    /**
     * Fetch all contacts.
     * @return An [Observable] with a [List] of all [Contact].
     */
    fun getAllContacts(): Observable<List<Contact>> {
        return Observable.create{
            it.onNext(obtainAllContacts())
            it.onComplete()
        }
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun obtainAllContacts(): List<Contact> {
        val cursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            All.PROJECTION,
            All.WHERE,
            All.SELECTION,
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