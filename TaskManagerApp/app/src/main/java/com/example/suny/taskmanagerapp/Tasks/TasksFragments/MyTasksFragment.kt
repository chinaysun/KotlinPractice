package com.example.suny.taskmanagerapp.Tasks.TasksFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.suny.taskmanagerapp.R

/**
 * A simple [Fragment] subclass.
 */

class MyTasksFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_my_tasks, container, false)
    }

}
