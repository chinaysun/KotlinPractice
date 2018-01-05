package com.example.suny.taskmanagerapp.Tasks.TasksFragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker

import com.example.suny.taskmanagerapp.R
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_customised_tasks.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CustomisedTasksFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CustomisedTasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class CustomisedTasksFragment : Fragment() {

    companion object {
        private const val LOG_TAG = "CustomisedTasksFragmentLog"

        private fun getCurrentTimeStamp(): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            val now = Date()
            return simpleDateFormat.format(now)
        }
    }

    private val tickReceiver by lazy { makeBroadcastReceiver() }

    private fun makeBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                if (intent?.action === Intent.ACTION_TIME_TICK) {
                    taskStartTimeTimer.text = getCurrentTimeStamp()
                }
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_customised_tasks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerAdapter = ArrayAdapter.createFromResource(activity,R.array.task_type_array,android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taskTypeSpinner.adapter = spinnerAdapter

        bindViewModel()

    }

    override fun onResume() {
        super.onResume()
        taskStartTimeTimer.text = getCurrentTimeStamp()
        activity.registerReceiver(tickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onPause() {
        super.onPause()

        // unregister timer
        try {
            activity.unregisterReceiver(tickReceiver)
        }catch (e: IllegalArgumentException) {
            Log.e(CustomisedTasksFragment.LOG_TAG, "Time tick Receiver not registered", e)
        }
    }

    private fun bindViewModel() {

        RxView.clicks(estimatedDateBtn)
                .subscribe({

                    //get Current Date
                    val currentCalendar = Calendar.getInstance(Locale.US)
                    val mYear = currentCalendar.get(Calendar.YEAR)
                    val mMonth = currentCalendar.get(Calendar.MONTH)
                    val mDay = currentCalendar.get(Calendar.DAY_OF_MONTH)

                    val dateSetLisener = object : DatePickerDialog.OnDateSetListener {

                        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                            var trimmedMonth = "${(month + 1)}"

                            if (month + 1 < 10) {
                                trimmedMonth = "0$trimmedMonth"
                            }

                            estimatedDate.text = "$year-$trimmedMonth-$dayOfMonth"
                        }
                    }

                    val datePickerDialog = DatePickerDialog(activity, dateSetLisener,mYear,mMonth,mDay)
                    datePickerDialog.show()


                })

        RxView.clicks(estimatedTimeBtn)
                .subscribe({
                    val currentCalendar = Calendar.getInstance(Locale.US)
                    val mHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
                    val mMinute = currentCalendar.get(Calendar.MINUTE)

                    val timeSetListener = object : TimePickerDialog.OnTimeSetListener {

                        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                            var trimmedHour = "$hourOfDay"

                            if (hourOfDay < 10) {
                                trimmedHour = "0$trimmedHour"
                            }

                            estimatedTime.text = "$trimmedHour:$minute"
                        }
                    }

                    val timePicker = TimePickerDialog(activity,timeSetListener,mHour,mMinute,true)
                    timePicker.show()


                })

    }

}
