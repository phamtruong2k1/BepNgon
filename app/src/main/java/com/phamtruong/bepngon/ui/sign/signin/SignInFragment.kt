package com.phamtruong.bepngon.ui.sign.signin

import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment<SignInViewModel, FragmentSignInBinding>(
    R.layout.fragment_sign_in,
    SignInViewModel::class.java
) {
    override fun init() {
        super.init()

    }
}