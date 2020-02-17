/**
 * Fundoo Notes
 * @description RecyclerTouchListener implements
 * RecyclerView.OnItemTouchListener.
 * @file RecycleTouchListener.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 17/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.content.Context
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class RecyclerTouchListener(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val clickListener: RecyclerClickListener
) : RecyclerView.OnItemTouchListener {


    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}