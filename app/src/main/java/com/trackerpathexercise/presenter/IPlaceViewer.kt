package com.trackerpathexercise.presenter

import com.trackerpathexercise.model.SearchPlace

interface IPlaceViewer {

    fun publish(result: List<SearchPlace>)

}