package com.example.suny.taskmanagerapp.Tasks.TasksFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.suny.taskmanagerapp.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CustomisedTasksFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CustomisedTasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class CustomisedTasksFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_customised_tasks, container, false)
    }


}
