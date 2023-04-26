package com.phamtruong.bepngon.ui.personalpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.databinding.ActivityWithoutPageBinding
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.ProfileFBListener
import com.phamtruong.bepngon.sever.ProfileFBUtil
import com.phamtruong.bepngon.sever.account.AccountFBUtil
import com.phamtruong.bepngon.view.setOnSafeClick
import com.phamtruong.bepngon.view.show

class WithoutPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityWithoutPageBinding

    var idUser = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithoutPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolBar.txtTitle.text = "Trang cá nhân"
        binding.toolBar.imgBack.show()
        binding.toolBar.imgBack.setOnSafeClick { onBackPressed() }

        idUser = intent.getStringExtra("idUser").toString()

        getDataUser()

    }

    private fun getDataUser() {
        ProfileFBUtil.getProfile(idUser, object : ProfileFBListener {
            override fun actionSuccess(profileModel: ProfileModel) {
                binding.toolBar.txtTitle.text = profileModel.name
            }

            override fun actionFail() {

            }
        })
    }
}