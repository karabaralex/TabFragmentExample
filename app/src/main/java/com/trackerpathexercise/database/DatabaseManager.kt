package com.trackerpathexercise.database

import com.google.android.gms.location.places.Place
import com.trackerpathexercise.model.SearchPlace
import java.util.*

/**
* Created by karabaralex on 08/06/16.
*/
object DatabaseManager {

    var cache: HashMap<String, List<SearchPlace>> = HashMap()
    var cacheInfo: HashMap<String, Place> = HashMap()

    fun save(query: String, answer: List<SearchPlace>) {
        cache.put(query, answer)
    }

    fun save(id: String, answer: Place) {
        cacheInfo.put(id, answer)
    }

    fun get(query: String): List<SearchPlace>? {
        return cache.get(query)
    }

    fun getInfo(id: String): Place? {
        return cacheInfo.get(id)
    }
}