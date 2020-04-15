/**
 * Fundoo Notes
 * @description An implementation of {@link ItemTouchHelper.Callback} that
 * enables basic drag & drop and swipe-to-dismiss.
 * Drag events are automatically started by an item long-press.
 * @file NoteTouchHelperCallback.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 13/04/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class NoteTouchHelperCallback(
    private val adapter: NoteTouchHelper
) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (recyclerView.layoutManager is GridLayoutManager) {
            val dragFlag: Int = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN).or(ItemTouchHelper.LEFT)
                .or(ItemTouchHelper.RIGHT)
            val swipeFlags = 0
            return makeMovementFlags(dragFlag, swipeFlags)
        } else {
            val dragFlag = ItemTouchHelper.UP.or(ItemTouchHelper.DOWN)
            val swipeFlag = ItemTouchHelper.START.or(ItemTouchHelper.END)
            return makeMovementFlags(dragFlag, swipeFlag)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if (viewHolder.itemViewType != target.itemViewType) return false

        adapter.onNoteMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onNoteSwipe(viewHolder.adapterPosition)
    }
}