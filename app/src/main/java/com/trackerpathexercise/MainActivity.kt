package com.trackerpathexercise

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trackerpathexercise.adapter.FragmentAdapter
import com.trackerpathexercise.adapter.PAGE_INFO
import com.trackerpathexercise.fragments.InfoSelectionFragment
import com.trackerpathexercise.model.SearchPlace
import com.trackerpathexercise.network.NetworkManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ITabContainer {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkManager.setup(applicationContext)
        NavigationManager.bindTabComponent(this)

        val adapter = FragmentAdapter(supportFragmentManager,
                arrayOf(getString(R.string.tab_places), getString(R.string.tab_info), getString(R.string.tab_edittext)));
        fragment_pager.adapter = adapter

        tabs.setupWithViewPager(fragment_pager)
    }

    override fun open(page: SearchPlace) {
        fragment_pager.setCurrentItem(PAGE_INFO);

        val fragment : InfoSelectionFragment? = supportFragmentManager.fragments.find { f -> f is InfoSelectionFragment } as InfoSelectionFragment?
        fragment?.showDetails(page)
    }
}
