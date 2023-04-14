package com.phamtruong.bepngon.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.phamtruong.bepngon.ui.main.flow.FlowedFragment
import com.phamtruong.bepngon.ui.main.friend.FriendFragment
import com.phamtruong.bepngon.ui.main.home.HomeFragment
import com.phamtruong.bepngon.ui.main.menu.MenuFragment
import com.phamtruong.bepngon.ui.main.noti.NotificationFragment

class TabViewMainAdapter (private val myContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> FlowedFragment()
            2 -> FriendFragment()
            3 -> NotificationFragment()
            4 -> MenuFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 5
    }
}