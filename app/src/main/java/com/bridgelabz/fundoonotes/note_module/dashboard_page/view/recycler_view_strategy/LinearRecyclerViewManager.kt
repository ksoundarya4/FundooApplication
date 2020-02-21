/**
 * Fundoo Notes
 * @description LineraRecyclerViewManager class
 * is used to set Recycler view to Linear layout manager.
 * @file LinerRecyclerViewManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 21/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearRecyclerViewManager(private val context: Context) : RecyclerViewStrategy {
    override fun setRecyclerViewLayout(recyclerView: RecyclerView): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }
}