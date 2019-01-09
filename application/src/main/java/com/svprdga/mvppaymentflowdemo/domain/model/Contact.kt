package com.svprdga.mvppaymentflowdemo.domain.model

import java.util.*

class Contact(
    val id: UUID = UUID.randomUUID(),
    val systemId: Long? = null,
    val name: String,
    val phone: String? = null,
    val avatarPath: String? = null)
    : Comparable<Contact> {

    override fun compareTo(other: Contact): Int {
        return name.compareTo(other.name)
    }
}

data class ResultContact(
    val contact: Contact,
    val amount: Float)
    : Comparable<ResultContact> {

    override fun compareTo(other: ResultContact): Int {
        return contact.name.compareTo(other.contact.name)
    }
}