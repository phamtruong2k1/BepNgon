package com.phamtruong.bepngon.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentHomeBinding
import com.phamtruong.bepngon.databinding.FragmentNoteBinding
import com.phamtruong.bepngon.ui.note.NoteViewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    R.layout.fragment_home,
    HomeViewModel::class.java
) {

}