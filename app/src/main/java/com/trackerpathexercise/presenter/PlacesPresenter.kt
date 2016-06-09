package com.trackerpathexercise.presenter

import android.os.AsyncTask
import com.trackerpathexercise.database.DatabaseManager
import com.trackerpathexercise.model.SearchPlace
import com.trackerpathexercise.network.NetworkManager

class PlacesPresenter {

    private var mViewer: IPlaceViewer? = null

    private var mTask: LoadingTask? = null

    fun bindView(viewer: IPlaceViewer) {
        mViewer = viewer
    }

    fun onStarted() {
        mTask?.attach(this)
    }

    fun onStop() {
        mTask?.detach()
        mViewer = null
    }

    fun query(query : String) {
        mTask?.cancel(true)
        mTask?.detach()

        mTask = LoadingTask()
        mTask?.attach(this)
        mTask?.execute(query)
    }

    fun postResults(result: List<SearchPlace>?) {
        mViewer?.publish(result ?: emptyList())
    }

    class LoadingTask : AsyncTask<String, Void, List<SearchPlace>>() {

        private var mPresenter: PlacesPresenter? = null

        override fun doInBackground(vararg params: String?): List<SearchPlace>? {
            val query = params[0]
            if (query != null) {
                val cached = DatabaseManager.get(query)
                if (cached != null) {
                    return cached
                } else {
                    val downloaded = NetworkManager.getPredictiveResults(query)
                    if (downloaded != null) {
                        DatabaseManager.save(query, downloaded)
                    }

                    return downloaded
                }
            }

            return null
        }

        override fun onPostExecute(result: List<SearchPlace>?) {
            mPresenter?.postResults(result)
        }

        fun attach(presenter: PlacesPresenter) {
            mPresenter = presenter
        }

        fun detach() {
            mPresenter = null
        }
    }
}