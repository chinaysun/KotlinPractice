package com.example.suny.mychatapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.suny.mychatapp.fragments.ChatsFragment
import com.example.suny.mychatapp.fragments.UserFragment

/**
 * Created by suny on 20/12/17.
 */
class SectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
       when( position ) {
           0 -> { return UserFragment() }
           1 -> { return ChatsFragment() }
       }

        return null!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {

        when (position) {
            0 -> return "USERS"
            1 -> return "CHATS"
        }

        return null!!
    }
}