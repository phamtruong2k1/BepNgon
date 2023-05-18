package com.phamtruong.bepngon.ui.tienich.bmi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phamtruong.bepngon.databinding.FragmentThapDinhDuongBinding
import com.phamtruong.bepngon.view.show

class ThapDinhDuongFragment : Fragment() {

    lateinit var binding : FragmentThapDinhDuongBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentThapDinhDuongBinding.inflate(inflater, container, false)

        initData()

        initListener()

        return binding.root
    }

    private fun initData() {
        val answer = requireArguments().getInt("data")

        when (answer) {
            1 -> {
                binding.txtFullName.text = "Dành cho người gầy"
                binding.viewThap1.show()
            }
            2 -> {
                binding.txtFullName.text = "Dành cho người trưởng thành"
                binding.viewThap2.show()
            }
            else -> {
                binding.txtFullName.text = "Dành cho người béo"
                binding.viewThap3.show()
            }
        }
    }

    private fun initListener() {
        binding.back.setOnClickListener {
            onBack()
        }
    }

    private fun onBack() {
        findNavController().popBackStack()
    }

}