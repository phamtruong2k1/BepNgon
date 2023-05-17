package com.phamtruong.bepngon.ui.tienich.bmi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.FragmentChiSoBmiBinding
import com.phamtruong.bepngon.databinding.FragmentLoginBinding

class ChiSoBmiFragment : Fragment() {

    lateinit var binding : FragmentChiSoBmiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChiSoBmiBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.back.setOnClickListener {
            onBack()
        }
    }

    private fun onBack() {
        requireActivity().onBackPressed()
    }
}