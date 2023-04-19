package com.phamtruong.bepngon.ui.main.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.FragmentFriendBinding

class FriendFragment : Fragment() {

    private lateinit var binding : FragmentFriendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFriendBinding.inflate(inflater, container, false)

        binding.toolBar.txtTitle.text = "Bạn bè"

        return binding.root
    }
}