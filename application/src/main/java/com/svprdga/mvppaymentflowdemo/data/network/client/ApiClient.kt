package com.svprdga.mvppaymentflowdemo.data.network.client

import com.svprdga.mvppaymentflowdemo.data.network.abstraction.IApi
import com.svprdga.mvppaymentflowdemo.data.network.mapper.Mapper
import com.svprdga.mvppaymentflowdemo.domain.extra.Keys
import com.svprdga.mvppaymentflowdemo.domain.extra.Mockable
import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.util.CryptoUtils
import io.reactivex.Observable
import retrofit2.Retrofit

@Mockable
class ApiClient(retrofit: Retrofit,
                private val cryptoUtils: CryptoUtils,
                private val mapper: Mapper
) {

    // ****************************************** VARS ***************************************** //

    private val api: IApi = retrofit.create(IApi::class.java)

    // ************************************* PUBLIC METHODS ************************************ //

    /**
     * Get the Marvel characters.
     * @param limit Specify the limit of the fetch operation.
     */
    fun getCharacters(limit: Int): Observable<List<Contact>> {

        val pubKey = Keys.MARVEL_PUB_KEY
        val privKey = Keys.MARVEL_PRIV_KEY
        val ts = System.currentTimeMillis().toString()

        val secret = "$ts$privKey$pubKey"
        val hash = cryptoUtils.encryptToMd5(secret)

        return api.getCharacters(pubKey, ts, hash, limit)
            .map(mapper.charactersResponseToContacts())
    }

}