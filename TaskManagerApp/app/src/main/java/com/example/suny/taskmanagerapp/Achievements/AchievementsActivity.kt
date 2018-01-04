package com.example.suny.taskmanagerapp.Achievements

import com.example.suny.taskmanagerapp.BaseActivity.BaseActivity
import com.example.suny.taskmanagerapp.R

class AchievementsActivity : BaseActivity() {

    override fun getNavigationMenuItemId(): Int {
        return R.id.navigation_achievements
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_achievements
    }
}
