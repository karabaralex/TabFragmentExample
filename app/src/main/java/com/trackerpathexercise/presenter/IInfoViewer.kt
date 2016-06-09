package com.trackerpathexercise.presenter

import com.google.android.gms.location.places.Place
import com.trackerpathexercise.model.SearchPlace

interface IInfoViewer {

    fun publish(result: Place)

}