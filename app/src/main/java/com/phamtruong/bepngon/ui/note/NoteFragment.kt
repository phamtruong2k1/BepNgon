package com.phamtruong.bepngon.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentNoteBinding

class NoteFragment : BaseFragment<NoteViewModel, FragmentNoteBinding>(
    R.layout.fragment_note,
    NoteViewModel::class.java
) {

}