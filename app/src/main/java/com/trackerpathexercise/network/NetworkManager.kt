package com.trackerpathexercise.network

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.Places
import com.trackerpathexercise.model.SearchPlace
import java.util.*
import java.util.concurrent.TimeUnit

object NetworkManager : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var mGoogleApiClient: GoogleApiClient? = null

    fun setup(context: Context) {
        mGoogleApiClient = GoogleApiClient
                .Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient?.connect();
    }

    fun destroy() {
        mGoogleApiClient?.disconnect();
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnected(p0: Bundle?) {
    }

    fun getPredictiveResults(query: String?): List<SearchPlace>? {
        var list : ArrayList<SearchPlace>? = null
        val buffer = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, query, null, null).await(1, TimeUnit.SECONDS);

        if (buffer.getStatus().isSuccess()) {
            list = ArrayList<SearchPlace>()
            for (prediction in buffer) {
                prediction?.getPrimaryText(null)?.let { (list as ArrayList<SearchPlace>).add(SearchPlace(prediction.getPrimaryText(null).toString(), prediction.placeId!!)) }
            }
        } else {
            Log.w("network", buffer.status.statusMessage)
        }

        buffer.release();

        return list
    }

    fun getInfo(id: String?): com.google.android.gms.location.places.Place? {
        val buffer = Places.GeoDataApi.getPlaceById(mGoogleApiClient, id).await(1, TimeUnit.SECONDS);

        if (buffer.getStatus().isSuccess()) {
            for (prediction in buffer) {
               return prediction
            }
        }

        buffer.release();

        return null
    }
}