package com.bridgelabz.fundoonotes.label_module.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.bridgelabz.fundoonotes.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LabelFragment : Fragment() {

    private val bottomAppBar by lazy {
        requireActivity().findViewById<BottomAppBar>(R.id.bottom_app_bar)
    }
    private val floatingActionButton by lazy {
        requireActivity().findViewById<FloatingActionButton>(R.id.fab)
    }
    private val toolbar by lazy {
        requireView().findViewById<Toolbar>(R.id.toolbar_label)
    }
    private val clearButton by lazy {
        requireView().findViewById<ImageButton>(R.id.clear_label)
    }
    private val labelEditText by lazy {
        requireView().findViewById<EditText>(R.id.edit_label)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_label, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideActivityToolbar()
        hideBottomAppToolbar()
        setUpToolbar()
        setOnButtonClickListeners()
    }

    private fun setOnButtonClickListeners() {
        clearButton.setOnClickListener {
            labelEditText.text.clear()
        }
    }

    private fun setUpToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        toolbar.setTitle(R.string.edit_labels)
    }

    private fun hideBottomAppToolbar() {
        bottomAppBar.performHide()
        floatingActionButton.hide()
    }

    private fun hideActivityToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar!!.show()
        bottomAppBar.performShow()
        floatingActionButton.show()
    }
}