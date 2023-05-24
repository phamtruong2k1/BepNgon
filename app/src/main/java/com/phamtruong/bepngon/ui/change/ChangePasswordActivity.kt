package com.phamtruong.bepngon.ui.change

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityChangePasswordBinding
import com.phamtruong.bepngon.databinding.ActivityProfileBinding
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.txtChangePassword.setOnClickListener {
            if (checkNull()) {
                showToast("Dữ liệu không thể bỏ trống!")
            } else if (checkConfirm()) {
                showToast("Xác nhận mật khẩu không giống!")
            } else if (checkOldPass()) {
                showToast("Mật khẩu cũ không giống!")
            } else {


                showToast("Ok la!")
                onBackPressed()
            }
        }
    }

    private fun checkNull(): Boolean {
        if (binding.edtOldPass.text.toString().trim().isEmpty()) {
            return true
        }
        if (binding.edtNewPass.text.toString().trim().isEmpty()) {
            return true
        }
        if (binding.edtConfirmNewPass.text.toString().trim().isEmpty()) {
            return true
        }
        return false
    }

    private fun checkConfirm(): Boolean {
        if (binding.edtConfirmNewPass.text.toString() == binding.edtNewPass.text.toString()) {
            return false
        }
        return true
    }

    private fun checkOldPass(): Boolean {
        if (binding.edtOldPass.text.toString() == SharePreferenceUtils.getPassword()) {
            return false
        }
        return true
    }
}