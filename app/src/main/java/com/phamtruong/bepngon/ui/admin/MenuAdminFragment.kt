package com.phamtruong.bepngon.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentMenuAdminBinding
import com.phamtruong.bepngon.databinding.FragmentMenuBinding

class MenuAdminFragment : BaseFragment<FragmentMenuAdminBinding>() {



    override fun initViewCreated() {

    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMenuAdminBinding {
        return FragmentMenuAdminBinding.inflate(inflater)
    }
}