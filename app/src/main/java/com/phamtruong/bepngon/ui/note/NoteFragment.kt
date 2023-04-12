package com.phamtruong.bepngon.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentHomeBinding
import com.phamtruong.bepngon.databinding.FragmentNoteBinding

class NoteFragment : BaseFragment<FragmentNoteBinding>() {
    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNoteBinding {
        return FragmentNoteBinding.inflate(inflater, container, false)
    }

}