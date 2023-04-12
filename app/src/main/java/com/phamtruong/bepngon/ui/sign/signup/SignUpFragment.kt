package com.phamtruong.bepngon.ui.sign.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentNoteBinding
import com.phamtruong.bepngon.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(inflater, container, false)
    }

}