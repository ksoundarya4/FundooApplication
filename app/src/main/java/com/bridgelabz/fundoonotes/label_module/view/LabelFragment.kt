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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.label_module.model.Label
import com.bridgelabz.fundoonotes.label_module.viewmodel.LabelViewModel
import com.bridgelabz.fundoonotes.label_module.viewmodel.LabelViewModelFactory
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.lable_module.LabelTableManagerImpl
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LabelFragment : Fragment() {
    private val labelFactory by lazy {
        LabelViewModelFactory(LabelTableManagerImpl(DatabaseHelper(requireContext())))
    }
    private val labelViewModel by lazy {
        requireActivity().run {
            ViewModelProvider(
                this,
                labelFactory
            ).get(LabelViewModel::class.java)
        }
    }
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
    private val saveButton by lazy {
        requireView().findViewById<ImageButton>(R.id.save_label)
    }
    private val labelEditText by lazy {
        requireView().findViewById<EditText>(R.id.edit_label)
    }
    private val recyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.label_recyclerView)
    }
    private val labelAdapter = LabelViewAdapter(ArrayList())

    private var labels = ArrayList<Label>()

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
        labelViewModel.getLabelLiveData().observe(requireActivity(), Observer { observeLabels(it) })
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = labelAdapter
    }

    private fun observeLabels(labelList: ArrayList<Label>) {
        labels = labelList
        labelAdapter.setLabelList(labels)
        labelAdapter.notifyDataSetChanged()
    }

    private fun setOnButtonClickListeners() {
        clearButton.setOnClickListener {
            labelEditText.text.clear()
        }
        saveButton.setOnClickListener {
            val label = labelEditText.editableText.toString()
            labelViewModel.saveLabel(Label(label = label))
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