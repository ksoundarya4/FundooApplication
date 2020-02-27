package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.bridgelabz.fundoonotes.R
import java.util.*

class ReminderDialogFragment : DialogFragment() {

    private val calender = Calendar.getInstance()

    private val dateEditText: EditText by lazy {
        requireView().findViewById<EditText>(R.id.date_edit_text)
    }
    private val timeEditText: EditText by lazy {
        requireView().findViewById<EditText>(R.id.time_edit_text)
    }

    private val date =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_YEAR, dayOfMonth)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reminder_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle(getString(R.string.dialog_reminder_title))
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDateEditTextListener()
        setTimeEditTextListener()
    }

    private fun setTimeEditTextListener() {
        timeEditText.setOnClickListener {
            //            setTimePicker()
        }
    }

    private fun setDateEditTextListener() {
        dateEditText.setOnClickListener {
            setDatePicker()
        }
    }

    private fun setDatePicker() {
        DatePickerDialog(
            requireContext(),
            date,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_YEAR)
        ).show()
    }

    override fun onResume() {
        super.onResume()

        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}