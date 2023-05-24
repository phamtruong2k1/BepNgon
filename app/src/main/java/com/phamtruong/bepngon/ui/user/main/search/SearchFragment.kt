package com.phamtruong.bepngon.ui.user.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.phamtruong.bepngon.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        initView()

        initListener()
        return binding.root
    }

    private fun initListener() {

    }

    private fun initView() {
        binding.toolBar.txtTitle.text = "Tìm kiếm"

    }


}