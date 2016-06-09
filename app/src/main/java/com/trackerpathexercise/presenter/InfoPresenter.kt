package com.trackerpathexercise.presenter

import android.os.AsyncTask
import android.os.Bundle
import com.google.android.gms.location.places.Place
import com.trackerpathexercise.database.DatabaseManager
import com.trackerpathexercise.model.SearchPlace
import com.trackerpathexercise.network.NetworkManager

var ARG_ID = "id"

class InfoPresenter {

    private var mViewer: IInfoViewer? = null

    private var mTask: LoadingTask? = null

    private var mSavedId : String? = null

    fun onCreate(savedInstanceState: Bundle?) {
        mSavedId = savedInstanceState?.getCharSequence(ARG_ID, null)?.toString()
    }

    fun bindView(viewer: IInfoViewer) {
        mViewer = viewer
        mSavedId?.let { id -> loadInfo(id) }
        mTask?.attach(this)
    }

    fun onStop() {
        mTask?.detach()
        mViewer = null
    }

    fun getInfo(place : SearchPlace) {
        mSavedId = place.id
        loadInfo(place.id)
    }

    private fun loadInfo(id: String) {
        mTask?.cancel(true)
        mTask?.detach()

        mTask = LoadingTask()
        mTask?.attach(this)
        mTask?.execute(id)
    }

    private fun postResults(result: Place?) {
        if (result != null) {
            mViewer?.publish(result)
        }
    }

    class LoadingTask : AsyncTask<String, Void, Place>() {

        private var mPresenter: InfoPresenter? = null

        override fun doInBackground(vararg params: String?): Place? {
            val id = params[0]
            if (id != null) {
                val cached = DatabaseManager.getInfo(id)
                if (cached != null) {
                    return cached
                } else {
                    val downloaded = NetworkManager.getInfo(id)
                    if (downloaded != null) {
                        DatabaseManager.save(id, downloaded)
                    }
                    return downloaded
                }
            }

            return null
        }

        override fun onPostExecute(result: Place?) {
            mPresenter?.postResults(result)
        }

        fun attach(presenter: InfoPresenter) {
            mPresenter = presenter
        }

        fun detach() {
            mPresenter = null
        }
    }

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putCharSequence(ARG_ID, mSavedId)
    }
}