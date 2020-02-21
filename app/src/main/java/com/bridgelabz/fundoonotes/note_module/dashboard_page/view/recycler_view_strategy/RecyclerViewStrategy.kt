/**
 * Fundoo Notes
 * @description RecyclerViewStrategy interface sets the
 * Recycler view layout.
 * @file RecyclerViewStrategy.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 21/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy

import androidx.recyclerview.widget.RecyclerView

interface RecyclerViewStrategy {
    fun setRecyclerViewLayout(recyclerView: RecyclerView): RecyclerView.LayoutManager
}