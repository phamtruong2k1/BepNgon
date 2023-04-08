package com.phamtruong.bepngon.ui.sign.signup

import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignUpBinding>(
    R.layout.fragment_sign_up,
    SignUpViewModel::class.java
){
    override fun init() {
        super.init()

    }
}