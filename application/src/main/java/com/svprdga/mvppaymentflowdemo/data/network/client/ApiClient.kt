package com.svprdga.mvppaymentflowdemo.data.network.client

import com.svprdga.mvppaymentflowdemo.data.network.abstraction.IApi
import com.svprdga.mvppaymentflowdemo.data.network.rx.SchedulersProvider
import com.svprdga.mvppaymentflowdemo.domain.extra.Keys
import com.svprdga.mvppaymentflowdemo.domain.extra.Mockable
import com.svprdga.mvppaymentflowdemo.util.CryptoUtils
import retrofit2.Retrofit
import java.security.MessageDigest

@Mockable
class ApiClient(retrofit: Retrofit,
                private val schedulers: SchedulersProvider,
                private val cryptoUtils: CryptoUtils) {

    // ****************************************** VARS ***************************************** //

    private val api: IApi = retrofit.create(IApi::class.java)

    // ************************************* PUBLIC METHODS ************************************ //

    fun getCharacters() {

        val pubKey = Keys.MARVEL_PUB_KEY
        val privKey = Keys.MARVEL_PRIV_KEY
        val ts = System.currentTimeMillis().toString()

        val secret = "$ts$privKey$pubKey"
        val hash = cryptoUtils.encryptToMd5(secret)

        val response = api.getCharacters(pubKey, ts, hash, 50).execute()
    }

}