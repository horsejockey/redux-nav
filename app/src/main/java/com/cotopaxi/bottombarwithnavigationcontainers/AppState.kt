package com.cotopaxi.bottombarwithnavigationcontainers

import redux.api.Reducer
import redux.api.Store
import redux.createStore

/**
 * Created by matt on 1/27/17.
 */

data class AppState(var name: String){

}

private val reducer = Reducer { state: AppState, action: Any ->
    when {
        action is String -> state.name = action
    }
    state
}

val sharedStore: Store<AppState> = createStore(reducer, AppState("Update me"))