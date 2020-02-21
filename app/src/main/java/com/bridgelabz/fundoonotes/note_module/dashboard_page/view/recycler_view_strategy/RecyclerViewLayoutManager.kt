/**
 * Fundoo Notes
 * @description RecyclerViewLayoutManager class sets valid layout
 * based on the requirements.
 * @file RecyclerViewLayoutManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 21/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy

import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLayoutManager {
    lateinit var recyclerView: RecyclerView

    fun addRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    fun setRecyclerView(recyclerViewStrategy: RecyclerViewStrategy): RecyclerView.LayoutManager {
        return recyclerViewStrategy.setRecyclerViewLayout(recyclerView)
    }
}