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

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class RecyclerTouchListener(
//    context: Context,
//    recyclerView: RecyclerView,
    private val clickListener: OnNoteClickListener
) : RecyclerView.OnItemTouchListener {

//    private val gestureDetector =
//        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
//            override fun onSingleTapUp(e: MotionEvent?): Boolean {
//                return true
//            }
//
//            override fun onLongPress(e: MotionEvent?) {
//                val childView = recyclerView.findChildViewUnder(e!!.x, e.y)
//                if (childView != null)
//                    clickListener.onClick(
//                        view = childView,
//                        position = recyclerView.getChildAdapterPosition(childView)
//                    )
//            }
//        })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//        val childView = rv.findChildViewUnder(e.x, e.y)
//        if (childView != null && gestureDetector.onTouchEvent(e)) {
//            clickListener.onClick(
//                view = childView,
//                position = rv.getChildAdapterPosition(childView)
//            )
//        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}