package com.trackerpathexercise

import com.trackerpathexercise.model.SearchPlace
import java.lang.ref.WeakReference

/**
 * Created by karabaralex on 09/06/16.
 */
object NavigationManager {

    var tabContainer : WeakReference<ITabContainer>? = null

    fun bindTabComponent(container: ITabContainer) {
        tabContainer = WeakReference(container)
    }

    fun showDetails(place : SearchPlace) {
        tabContainer?.get()?.open(place)
    }
}
