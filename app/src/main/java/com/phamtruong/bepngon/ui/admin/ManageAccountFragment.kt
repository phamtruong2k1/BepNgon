package com.phamtruong.bepngon.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentManageAccountBinding
import com.phamtruong.bepngon.databinding.FragmentMenuAdminBinding


class ManageAccountFragment : BaseFragment<FragmentManageAccountBinding>() {


    override fun initViewCreated() {

    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentManageAccountBinding {
        return FragmentManageAccountBinding.inflate(inflater)
    }


}