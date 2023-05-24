package com.phamtruong.bepngon.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentMenuAdminBinding
import com.phamtruong.bepngon.databinding.FragmentMenuBinding
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.ui.change.ChangePasswordActivity
import com.phamtruong.bepngon.ui.personalpage.ProfileActivity
import com.phamtruong.bepngon.ui.sign.SignActivity
import com.phamtruong.bepngon.util.DataHelper
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.openActivity
import com.squareup.picasso.Picasso

class MenuAdminFragment : BaseFragment<FragmentMenuAdminBinding>() {



    override fun initViewCreated() {
        initView()
        DataHelper.profileUser.observe(viewLifecycleOwner){
            updateUI(it)
        }

        initListener()
    }

    private fun initListener() {
        binding.btnLogout.setOnClickListener {
            SharePreferenceUtils.setAccountID(null)
            SharePreferenceUtils.setUserName(null)
            SharePreferenceUtils.setPassword(null)
            requireContext().openActivity(SignActivity::class.java, true)
        }

        binding.llChangePassword.setOnClickListener {
            requireContext().openActivity(ChangePasswordActivity::class.java)
        }

        binding.llChangeProfile.setOnClickListener {
            requireContext().openActivity(ProfileActivity::class.java)
        }
    }

    private fun updateUI(profileModel: ProfileModel) {
        profileModel.let {
            binding.userName.text = profileModel.name
            Picasso.get().load(profileModel.avt).into(binding.userAvatar)
        }
    }

    private fun initView() {
        binding.toolBar.txtTitle.text = "Menu"
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMenuAdminBinding {
        return FragmentMenuAdminBinding.inflate(inflater)
    }
}