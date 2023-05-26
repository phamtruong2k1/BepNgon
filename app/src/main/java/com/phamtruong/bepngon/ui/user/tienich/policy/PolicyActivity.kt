package com.phamtruong.bepngon.ui.user.tienich.policy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityChangePasswordBinding
import com.phamtruong.bepngon.databinding.ActivityPolicyBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast

class PolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}