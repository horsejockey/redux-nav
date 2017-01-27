package com.cotopaxi.bottombarwithnavigationcontainers

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import mu.KotlinLogging
import redux.api.Store

/**
 * Created by matt on 1/27/17.
 */


class MainTabContainer(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), Store.Subscriber {

    var storeSubscription: Store.Subscription? = null
    private val logger = KotlinLogging.logger("Main Tab Container")

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (storeSubscription == null) {
            storeSubscription = navStore.subscribe(this)
            onStateChanged()
        }
        logger.debug { "Attached to window" }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        storeSubscription?.unsubscribe()
        storeSubscription = null
        logger.debug { "Detached from window" }
    }

    override fun onStateChanged() {
        showTab(navStore.state.tab)
    }

    fun showTab(tab: Tab) {

        var view: View = getChildAt(0)
        when(tab) {
            Tab.FIRST -> {
                if (view is NavigationContainer){
                    return
                }else{
                    removeViewAt(0)
                    View.inflate(getContext(), R.layout.navigation_container, this);
                }
            }
            Tab.SECOND -> {
                if (view is SecondTabView){
                    return
                }else{
                    removeViewAt(0)
                    View.inflate(getContext(), R.layout.second_tab_view, this);
                }
            }
        }
    }
}

class NavigationContainer(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), Store.Subscriber {

    var storeSubscription: Store.Subscription? = null
    private val logger = KotlinLogging.logger("Music Navigation Container")

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (storeSubscription == null) {
            storeSubscription = navStore.subscribe(this)
            onStateChanged()
        }
        logger.debug { "Attached to window" }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        storeSubscription?.unsubscribe()
        storeSubscription = null
        logger.debug { "Detached from window" }
    }

    override fun onStateChanged() {
        showView(navStore.state.favoritesBackStack.last())
    }

    private fun showView(viewGroup: NavViewGroup){
        var view: View = getChildAt(0)
        when(viewGroup) {
            NavViewGroup.DETAIL -> {
                if (view is DetailView){
                    return
                }else{
                    removeViewAt(0)
                    View.inflate(getContext(), R.layout.detail_view, this)
                }
            }
            NavViewGroup.ROOT -> {
                if (view is FirstTabView){
                    return
                }else{
                    removeViewAt(0)
                    View.inflate(getContext(), R.layout.first_tab_view, this)
                }
            }
        }
    }
}