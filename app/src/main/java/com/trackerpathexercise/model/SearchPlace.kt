package com.trackerpathexercise.model

/**
 * Created by karabaralex on 09/06/16.
 */
data class SearchPlace(val desription: String, val id: String) {
    override fun toString(): String {
        return desription
    }
}