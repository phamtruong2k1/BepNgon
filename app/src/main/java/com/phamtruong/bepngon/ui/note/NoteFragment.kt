package com.phamtruong.bepngon.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentHomeBinding
import com.phamtruong.bepngon.databinding.FragmentNoteBinding

class NoteFragment : BaseFragment<FragmentNoteBinding>() {

    companion object {
        const val REQ_ONE_TAP = 1111
    }

    private lateinit var auth: FirebaseAuth

    override fun initViewCreated() {
        auth = Firebase.auth
        val currentUser = auth.currentUser
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNoteBinding {
        return FragmentNoteBinding.inflate(inflater, container, false)
    }

}