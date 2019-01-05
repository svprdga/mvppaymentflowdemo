package com.svprdga.mvppaymentflowdemo.util

import android.content.Context
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.domain.extra.Mockable

@Mockable
class TextProvider(private val context: Context) {

    val contactStatus: String
        get() = context.getString(R.string.mainActivity_contactsStatus)
    val amountStatus: String
        get() = context.getString(R.string.mainActivity_amountStatus)
    val totalStatus: String
        get() = context.getString(R.string.mainActivity_totalStatus)
}