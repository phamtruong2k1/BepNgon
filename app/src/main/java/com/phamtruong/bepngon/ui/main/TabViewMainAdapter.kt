package com.phamtruong.bepngon.ui.main

import android.app.Notification
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.phamtruong.bepngon.ui.flow.FlowedFragment
import com.phamtruong.bepngon.ui.friend.FriendFragment
import com.phamtruong.bepngon.ui.home.HomeFragment
import com.phamtruong.bepngon.ui.menu.MenuFragment
import com.phamtruong.bepngon.ui.note.NoteFragment
import com.phamtruong.bepngon.ui.noti.NotificationFragment

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