package com.phamtruong.bepngon.ui.sign.login

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.FragmentLoginBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.ui.main.MainActivity
import com.phamtruong.bepngon.util.Constant
import com.phamtruong.bepngon.sever.FirebaseDatabaseUtil
import com.phamtruong.bepngon.sever.account.AccountFirebaseUtil
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.setOnSafeClick

class LoginFragment : Fragment() {

    lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.llSignIn.setOnSafeClick {
            if (binding.edtTaiKhoan.text.toString().trim().isEmpty()){
                requireContext().showToast("Tài khoản trống!")
            } else if (binding.edtMatKhau.text.toString().trim().isEmpty()){
                requireContext().showToast("Mật khẩu khoản trống!")
            } else {
                AccountFirebaseUtil.logIn(
                    requireContext(),
                    binding.edtTaiKhoan.text.toString().trim(),
                    binding.edtMatKhau.text.toString().trim()
                ) {
                    requireContext().openActivity(MainActivity::class.java, true)
                }
            }
        }

        binding.txtSignUp.setOnSafeClick {
            findNavController().navigate(R.id.action_loginFragment_to_logUpFragment)
        }
    }

}