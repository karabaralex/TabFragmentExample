package com.trackerpathexercise.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.places.Place

import com.trackerpathexercise.R
import com.trackerpathexercise.model.SearchPlace
import com.trackerpathexercise.presenter.IInfoViewer
import com.trackerpathexercise.presenter.InfoPresenter
import com.trackerpathexercise.presenter.PlacesPresenter
import kotlinx.android.synthetic.main.fragment_info.*

class InfoSelectionFragment : Fragment(), IInfoViewer {

    val presenter: InfoPresenter = InfoPresenter()

    override fun publish(result: Place) {
        name.text = result.name
        address.text = result.address
        coordinate.text = "${result.latLng.latitude} ${result.latLng.longitude}"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bindView(this)
    }

    fun showDetails(place: SearchPlace) {
        presenter.getInfo(place)
    }

    override fun onStop() {
        super.onStop()

        presenter.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        presenter.onSaveInstanceState(outState)
    }
}
