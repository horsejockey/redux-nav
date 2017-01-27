package com.cotopaxi.bottombarwithnavigationcontainers

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import mu.KotlinLogging
import redux.api.Store

/**
 * Created by matt on 1/27/17.
 */

class FirstTabView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs), Store.Subscriber {

    private val logger = KotlinLogging.logger("Favorites View")

    var storeSubscription: Store.Subscription? = null
    var editText: EditText? = null

    override fun onFinishInflate() {
        super.onFinishInflate()

        val editText = findViewById(R.id.text_current_state) as EditText
        this.editText = editText
        editText.setText(sharedStore.state.name)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                sharedStore.dispatch(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        val musicButton = findViewById(R.id.music_button) as TextView
        musicButton.setOnTouchListener { view, event ->
            navStore.dispatch(PushViewAction(Tab.FIRST, NavViewGroup.DETAIL))
            true
        }
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        logger.info { "Visibility changed: $visibility" }
        when(visibility){
            View.GONE -> {
                storeSubscription?.unsubscribe()
                storeSubscription = null
            }
            View.VISIBLE -> {
                if (storeSubscription == null) {
                    storeSubscription = sharedStore.subscribe(this)
                    onStateChanged()
                }
            }
        }
    }

    override fun onStateChanged() {
        logger.info(sharedStore.state.name)
    }
}


class SecondTabView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs), Store.Subscriber{
    private val logger = KotlinLogging.logger("Schedules View")

    var storeSubscription: Store.Subscription? = null
    var textView: TextView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        textView = findViewById(R.id.text_current_state) as TextView
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        logger.info { "Visibility changed: $visibility" }
        when(visibility){
            View.GONE -> {
                storeSubscription?.unsubscribe()
                storeSubscription = null
            }
            View.VISIBLE -> {
                if (storeSubscription == null) {
                    storeSubscription = sharedStore.subscribe(this)
                    onStateChanged()
                }
            }
        }
    }

    override fun onStateChanged() {
        logger.info(sharedStore.state.name)
        textView?.text = sharedStore.state.name
    }

}
class DetailView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs), Store.Subscriber{
    private val logger = KotlinLogging.logger("Music View")

    var storeSubscription: Store.Subscription? = null
    var textView: TextView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        textView = findViewById(R.id.text_current_state) as TextView
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        logger.info { "Visibility changed: $visibility" }
        when(visibility){
            View.GONE -> {
                storeSubscription?.unsubscribe()
                storeSubscription = null
            }
            View.VISIBLE -> {
                if (storeSubscription == null) {
                    storeSubscription = sharedStore.subscribe(this)
                    onStateChanged()
                }
            }
        }
    }

    override fun onStateChanged() {
        logger.info(sharedStore.state.name)
        textView?.text = sharedStore.state.name
    }

}