package com.svprdga.mvppaymentflowdemo.data.network.mapper

import com.svprdga.mvppaymentflowdemo.data.network.response.CharactersResponse
import com.svprdga.mvppaymentflowdemo.domain.extra.Mockable
import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import io.reactivex.functions.Function

@Mockable
class Mapper {

    /**
     * Map a [CharactersResponse] to a [List] of [Contact].
     */
    fun charactersResponseToContacts(): Function<CharactersResponse, List<Contact>> {
        return Function {
            val contactList = ArrayList<Contact>()

            it.data.results.forEach { entity ->
                val contact = Contact(name = entity.name)
                contactList.add(contact)
            }

            contactList
        }
    }

}