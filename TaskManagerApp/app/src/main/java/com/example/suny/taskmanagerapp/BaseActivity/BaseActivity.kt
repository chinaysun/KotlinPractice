package com.example.suny.taskmanagerapp.BaseActivity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.suny.taskmanagerapp.R
import android.content.Intent
import com.example.suny.taskmanagerapp.Achievements.AchievementsActivity
import com.example.suny.taskmanagerapp.Statistics.StatisticsActivity
import com.example.suny.taskmanagerapp.Tasks.TasksActivity


abstract class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationView: BottomNavigationView

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView.postDelayed({
            val itemId = item.itemId
            if (itemId == R.id.navigation_tasks) {
                startActivity(Intent(this, TasksActivity::class.java))
            } else if (itemId == R.id.navigation_achievements) {
                startActivity(Intent(this, AchievementsActivity::class.java))
            } else if (itemId == R.id.navigation_statistics) {
                startActivity(Intent(this, StatisticsActivity::class.java))
            }
            finish()
        }, 200)
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())

        navigationView = findViewById(R.id.navigation)
        navigationView.setOnNavigationItemSelectedListener(this)

    }

    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }

    private fun updateNavigationBarState() {
        val actionId = getNavigationMenuItemId()
        selectBottomNavigationBarItem(actionId)
    }

    private fun selectBottomNavigationBarItem(itemId:Int) {
        val menu: Menu = navigationView.menu

        var i = 0
        val size = menu.size()
        while (i < size) {
            val item = menu.getItem(i)
            val shouldBeChecked = item.itemId == itemId
            if (shouldBeChecked) {
                item.isChecked = true
                break
            }
            i++
        }

    }

    abstract fun getContentViewId():Int
    abstract fun getNavigationMenuItemId(): Int

}

