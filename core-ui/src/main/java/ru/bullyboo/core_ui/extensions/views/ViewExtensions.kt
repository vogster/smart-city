package ru.bullyboo.core_ui

import android.view.View

fun View.makeVisible() {
    if(visibility != View.VISIBLE){
        visibility = View.VISIBLE
    }
}

fun View.makeInvisible() {
    if(visibility != View.INVISIBLE){
        visibility = View.INVISIBLE
    }
}

fun View.makeGone() {
    if(visibility != View.GONE){
        visibility = View.GONE
    }
}