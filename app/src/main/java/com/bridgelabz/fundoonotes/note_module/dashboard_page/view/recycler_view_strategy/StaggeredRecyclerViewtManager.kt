/**
 * Fundoo Notes
 * @description StaggeredRecyclerViewtManager class has number of rows and
 * orientation which is used to set Recycler view to staggered grid layout manager.
 * @file StaggeredRecyclerViewManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 21/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredRecyclerViewtManager(private val numberOfRows: Int, private val orientation: Int) :
    RecyclerViewStrategy {
    override fun setRecyclerViewLayout(recyclerView: RecyclerView): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(numberOfRows, orientation)
    }
}