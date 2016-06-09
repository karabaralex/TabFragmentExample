package com.trackerpathexercise.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.trackerpathexercise.NavigationManager
import com.trackerpathexercise.R
import com.trackerpathexercise.model.SearchPlace
import com.trackerpathexercise.presenter.IPlaceViewer
import com.trackerpathexercise.presenter.PlacesPresenter
import kotlinx.android.synthetic.main.fragment_places_selection.*


class PlacesSelectionFragment : Fragment(), IPlaceViewer {
    val presenter: PlacesPresenter = PlacesPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_places_selection, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bindView(this)

        autocomplete.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                presenter.query(s.toString())
            }
        })
    }

    override fun publish(result: List<SearchPlace>) {
        list.adapter = ArrayAdapter<SearchPlace>(getContext(), android.R.layout.simple_list_item_1, result)
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l -> run {
            autocomplete.clearFocus()

            val imm : InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
            imm.hideSoftInputFromWindow(autocomplete.getWindowToken(), 0);

            NavigationManager.showDetails(list.adapter.getItem(i) as SearchPlace)}
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.onStarted()
    }

    override fun onStop() {
        super.onStop()

        presenter.onStop()
    }
}
