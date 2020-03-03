package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.notification_util.AlertReceiver
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReminderDialogFragment : DialogFragment() {

    private val calender = Calendar.getInstance()

    private val dateEditText: EditText by lazy {
        requireView().findViewById<EditText>(R.id.date_edit_text)
    }
    private val timeEditText: EditText by lazy {
        requireView().findViewById<EditText>(R.id.time_edit_text)
    }
    private val saveReminder: Button by lazy {
        requireView().findViewById<Button>(R.id.button_save_reminder)
    }
    private val cancelReminder: Button by lazy {
        requireView().findViewById<Button>(R.id.button_cancel_reminder)
    }
    private val date =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_YEAR, dayOfMonth)
            updateDateEditText()
            setAlarm(calender.time)
        }

    private fun setAlarm(delay: Date) {

        Log.d("delayReminder", delay.toString())
        Log.d("delayTimeReminder", delay.time.toString())

        val alarmManager: AlarmManager =
            (requireActivity() as AppCompatActivity).getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(), AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireActivity(),
            777,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.set(AlarmManager.RTC_WAKEUP, delay.time, pendingIntent)
    }

    private val onReminderListener by lazy {
        targetFragment as OnReminderListener
    }

    private val time = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calender.set(Calendar.MINUTE, minute)
        calender.set(Calendar.SECOND, 0)
        updateTimeEditText()
    }

    private fun updateTimeEditText() {
        val time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calender.time)
        timeEditText.setText(time)
    }

    private fun updateDateEditText() {
        val dateFormat = getString(R.string.reminder_date_format)
        val setDateFormat = SimpleDateFormat(dateFormat, Locale.US)
        dateEditText.setText(setDateFormat.format(calender.time))
        Log.d("ReminderDate", calender.time.toString())

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
        setOnSaveReminderButtonListener()
        setOnCancelReminderButtonListener()
    }

    private fun setOnCancelReminderButtonListener() {
        cancelReminder.setOnClickListener { this.dialog!!.cancel() }

    }

    private fun setOnSaveReminderButtonListener() {
        saveReminder.setOnClickListener {
            val date = dateEditText.editableText.toString()
            val time = timeEditText.editableText.toString()
            if (date.isNotEmpty() && time.isNotEmpty()) {
                onReminderListener.onReminderSubmit(date, time)
            }
            this.dialog!!.cancel()

        }
    }

    private fun setTimeEditTextListener() {
        timeEditText.setOnClickListener {
            setTimePicker()
        }
    }

    private fun setTimePicker() {
        TimePickerDialog(
            requireContext(), time,
            calender.get(Calendar.DAY_OF_YEAR),
            calender.get(Calendar.MINUTE), true
        ).show()
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