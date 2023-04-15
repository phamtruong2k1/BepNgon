package com.phamtruong.bepngon.ui.main.flow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentFlowedBinding

class FlowedFragment : BaseFragment<FragmentFlowedBinding>() {
    override fun initViewCreated() {

    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFlowedBinding {
        return FragmentFlowedBinding.inflate(inflater)
    }

}