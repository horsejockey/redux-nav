package com.cotopaxi.bottombarwithnavigationcontainers

import redux.api.Reducer
import redux.api.Store
import redux.createStore

/**
 * Created by matt on 1/27/17.
 */

// Nav View Groups
enum class Tab {
    FIRST, SECOND
}

enum class NavViewGroup {
    ROOT, DETAIL
}

// Nav Actions
data class ChangeTabAction(val tab: Tab)
data class PushViewAction(val tab: Tab, val viewGroup: NavViewGroup)
data class PopViewAction(val tab: Tab)
data class UnwindAction(val tab: Tab, val viewGroup: NavViewGroup)
class BackButtonAction()

// Navigation State
data class NavigationState(var favoritesBackStack: MutableList<NavViewGroup>, var tab: Tab){
    fun currentBackStack() : MutableList<NavViewGroup>? {
        when(tab) {
            Tab.FIRST -> return favoritesBackStack
        }
        return null
    }
}

// Reducer
private val navReducer = Reducer { state: NavigationState, action: Any ->

    fun push(backStack: MutableList<NavViewGroup>, viewGroup: NavViewGroup) : MutableList<NavViewGroup>{
        if (backStack.last() != viewGroup) {
            backStack.add(viewGroup)
        }
        return backStack
    }

    fun pop(backStack: MutableList<NavViewGroup>)  : MutableList<NavViewGroup>{
        if (backStack.size > 1){
            backStack.removeAt(backStack.lastIndex)
        }
        return backStack
    }

    fun unwindTo(backStack: MutableList<NavViewGroup>, viewGroup: NavViewGroup) : MutableList<NavViewGroup> {
        if (backStack.indexOf(viewGroup) >= 0){
            val index = backStack.indexOf(viewGroup)
            return backStack.subList(0,index)
        }
        return backStack
    }

    when {
        action is ChangeTabAction -> state.tab = action.tab

        action is BackButtonAction -> {
            val backstack = state.currentBackStack()
            if (backstack != null){
                pop(backstack)
            }
        }

        action is PushViewAction -> {
            when {
                action.tab == Tab.FIRST ->  push(state.favoritesBackStack, action.viewGroup)
            }
        }

        action is PopViewAction -> {
            when {
                action.tab == Tab.FIRST-> pop(state.favoritesBackStack)
            }
        }

        action is UnwindAction -> {
            when {
                action.tab == Tab.FIRST-> unwindTo(state.favoritesBackStack, action.viewGroup)
            }
        }
    }
    state
}

// Globally Shared Store
val navStore: Store<NavigationState> = createStore(navReducer, NavigationState(mutableListOf(NavViewGroup.ROOT), Tab.FIRST))
