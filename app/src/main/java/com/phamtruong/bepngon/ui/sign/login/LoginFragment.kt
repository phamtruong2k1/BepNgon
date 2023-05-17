package com.phamtruong.bepngon.ui.sign.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.FragmentLoginBinding
import com.phamtruong.bepngon.ui.main.MainActivity
import com.phamtruong.bepngon.sever.AccountFBUtil
import com.phamtruong.bepngon.ui.admin.MainAdminActivity
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
                AccountFBUtil.logIn(
                    requireContext(),
                    binding.edtTaiKhoan.text.toString().trim(),
                    binding.edtMatKhau.text.toString().trim()
                ) {
                    requireContext().openActivity(MainAdminActivity::class.java, true)
                }
            }
        }

        binding.txtSignUp.setOnSafeClick {
            findNavController().navigate(R.id.action_loginFragment_to_logUpFragment)
        }
    }

}