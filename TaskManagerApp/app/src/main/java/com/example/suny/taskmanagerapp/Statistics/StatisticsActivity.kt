package com.example.suny.taskmanagerapp.Statistics

import com.example.suny.taskmanagerapp.BaseActivity.BaseActivity
import com.example.suny.taskmanagerapp.R

class StatisticsActivity : BaseActivity() {

    override fun getNavigationMenuItemId(): Int {
        return R.id.navigation_statistics
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_statistics
    }
}
