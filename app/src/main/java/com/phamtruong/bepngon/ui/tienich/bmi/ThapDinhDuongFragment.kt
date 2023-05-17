package com.phamtruong.bepngon.ui.tienich.bmi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.FragmentChiSoBmiBinding
import com.phamtruong.bepngon.databinding.FragmentThapDinhDuongBinding

class ThapDinhDuongFragment : Fragment() {

    lateinit var binding : FragmentThapDinhDuongBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentThapDinhDuongBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {

    }

}