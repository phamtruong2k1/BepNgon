package com.phamtruong.bepngon.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.phamtruong.bepngon.ui.home.HomeFragment
import com.phamtruong.bepngon.ui.note.NoteFragment

class TabViewMainAdapter (private val myContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> NoteFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 6
    }
}