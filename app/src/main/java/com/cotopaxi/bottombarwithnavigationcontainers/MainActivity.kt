package com.cotopaxi.bottombarwithnavigationcontainers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import redux.api.Store

class MainActivity : AppCompatActivity(), Store.Subscriber {

    var storeSubscription: Store.Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_favorites -> {
                    navStore.dispatch(ChangeTabAction(Tab.FIRST))
                }
                R.id.action_schedules -> {
                    navStore.dispatch(ChangeTabAction(Tab.SECOND))
                }
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        if (storeSubscription == null) {
            storeSubscription = navStore.subscribe(this)
            onStateChanged()
        }
    }

    override fun onStop() {
        super.onStop()
        storeSubscription?.unsubscribe()
        storeSubscription = null
    }

    override fun onStateChanged() {
        val backStack = navStore.state.currentBackStack()
        if (backStack != null && backStack.size > 1){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }else{
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val backStack = navStore.state.currentBackStack()
        if (backStack != null && backStack.size > 1){
            navStore.dispatch(BackButtonAction())
        }else{
            finish()
        }
    }

}
