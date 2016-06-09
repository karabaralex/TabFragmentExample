package com.trackerpathexercise.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.trackerpathexercise.fragments.EdittextSelectionFragment
import com.trackerpathexercise.fragments.InfoSelectionFragment

import com.trackerpathexercise.fragments.PlacesSelectionFragment

var PAGE_PLACES = 0;
var PAGE_INFO = 1;
var PAGE_EDIT_TEXT = 2;

class FragmentAdapter(val fm: FragmentManager, val titles: Array<String>) : FragmentPagerAdapter(fm) {

    private val NUM_ITEMS = 3

    override fun getCount(): Int {
        return NUM_ITEMS
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            PAGE_PLACES -> return PlacesSelectionFragment()
            PAGE_INFO -> return InfoSelectionFragment()
            PAGE_EDIT_TEXT -> return EdittextSelectionFragment()
        }

        throw IllegalStateException();
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position];
    }
}