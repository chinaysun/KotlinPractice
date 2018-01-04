package com.example.suny.taskmanagerapp.Tasks

import android.app.FragmentManager
import android.app.FragmentManager.OnBackStackChangedListener
import android.os.Bundle
import android.widget.PopupMenu
import com.example.suny.taskmanagerapp.BaseActivity.BaseActivity
import com.example.suny.taskmanagerapp.R
import com.example.suny.taskmanagerapp.Tasks.TasksFragments.CustomisedTasksFragment
import com.example.suny.taskmanagerapp.Tasks.TasksFragments.GeneralTasksFragment
import com.example.suny.taskmanagerapp.Tasks.TasksFragments.MyTasksFragment
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxPopupMenu
import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : BaseActivity(), OnBackStackChangedListener, android.support.v4.app.FragmentManager.OnBackStackChangedListener {

    private lateinit var viewModel: TasksActivityViewModel
    private lateinit var popupMenu: PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = TasksActivityViewModel()

        if (savedInstanceState == null) {

            createFragment()

            popupMenu = PopupMenu(this,addTaskButton)
            popupMenu.menuInflater.inflate(R.menu.addtask_popup_menu,popupMenu.menu)


            addTaskButton.show()
        }


        bindViewModel()

    }

    private fun createFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.tasks_activity,MyTasksFragment(),"MyTasks")
                .addToBackStack("MyTasksFragment")
                .commit()

        supportActionBar!!.title = "My Tasks"
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        supportFragmentManager.addOnBackStackChangedListener(this)
        shouldDisplayHomeUp()
    }

    override fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    fun shouldDisplayHomeUp() {
        //Enable Up button only  if there are entries in the back stack
        val canBack = supportFragmentManager.backStackEntryCount > 1
        supportActionBar!!.setDisplayHomeAsUpEnabled(canBack)
    }

    override fun onSupportNavigateUp(): Boolean {
        //This method is called when the up button is pressed. Just the pop back stack.
        supportFragmentManager.popBackStack()
        return true
    }

    private fun bindViewModel() {

        RxView.clicks(addTaskButton)
                .subscribe({
                    popupMenu.show()
                })

        RxPopupMenu.itemClicks(popupMenu)
                .subscribe({ menuItem ->
                    if (menuItem.itemId == R.id.generalTasks ) {
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.tasks_activity,GeneralTasksFragment(),"GeneralTasks")
                                .addToBackStack("GeneralTasksFragment")
                                .commit()

                        supportActionBar!!.title = "General Tasks"
                    }
                    else if (menuItem.itemId == R.id.createTask) {
                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.tasks_activity,CustomisedTasksFragment(),"CreateTasks")
                                .addToBackStack("CreateTasksFragment")
                                .commit()

                        supportActionBar!!.title = "Create a task"

                    }
                })


    }

    override fun getNavigationMenuItemId(): Int {
        return R.id.navigation_tasks
    }

    override fun getContentViewId(): Int {
       return R.layout.activity_tasks
    }
}
