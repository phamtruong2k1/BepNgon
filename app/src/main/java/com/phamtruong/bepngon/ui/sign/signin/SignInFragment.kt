package com.phamtruong.bepngon.ui.sign.signin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentNoteBinding
import com.phamtruong.bepngon.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding {
        return FragmentSignInBinding.inflate(inflater, container, false)
    }

}