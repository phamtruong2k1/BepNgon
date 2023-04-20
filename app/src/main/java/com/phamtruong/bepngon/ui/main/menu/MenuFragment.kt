package com.phamtruong.bepngon.ui.main.menu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentMenuBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.account.AccountFirebaseUtil
import com.phamtruong.bepngon.ui.chat.ChatActivity
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.ui.personalpage.ProfileActivity
import com.phamtruong.bepngon.ui.sign.SignActivity
import com.phamtruong.bepngon.util.Constant
import com.phamtruong.bepngon.util.DataHelper
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.setOnSafeClick
import com.squareup.picasso.Picasso

class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    companion object {
        const val EMAIL = "phamtruong28092001@gmail.com"
        const val FeedBack = "FeedBack"
        const val HELP = "Help"
    }

    override fun initViewCreated() {
        binding.toolBar.txtTitle.text = "Menu"
        initView()
        DataHelper.profileUser.observe(viewLifecycleOwner){
            updateUI(it)
        }

        initListener()
    }

    private fun initView() {

    }

    private fun initListener() {
        binding.btnLogout.setOnClickListener {
            SharePreferenceUtils.setAccountID(null)
            SharePreferenceUtils.setUserName(null)
            SharePreferenceUtils.setPassword(null)
            requireContext().openActivity(SignActivity::class.java, true)
        }
        binding.llHelp.setOnClickListener { support(requireContext()) }

        binding.llFeedback.setOnClickListener { feedBack(requireContext()) }

        binding.llPolicy.setOnClickListener {
            /*requireContext().openActivity(PersonalPageActivity::class.java)*/
        }

        binding.llPersonalPage.setOnClickListener {
            requireContext().openActivity(PersonalPageActivity::class.java)
        }
    }

    private fun updateUI(profileModel: ProfileModel) {
        profileModel.let {
            binding.userName.text = profileModel.name
            Picasso.get().load(profileModel.avt).into(binding.userAvatar)
        }
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater)
    }


    private fun support(context: Context) {
        val mailIntent = Intent(Intent.ACTION_VIEW)
        val data =
            Uri.parse("mailto:?SUBJECT=$FeedBack&body=&to=$EMAIL")
        mailIntent.data = data
        context.startActivity(Intent.createChooser(mailIntent, "Send mail..."))
    }

    private fun feedBack(context: Context) {
        val mailIntent = Intent(Intent.ACTION_VIEW)
        val data =
            Uri.parse("mailto:?SUBJECT=$FeedBack&body=&to=$EMAIL")
        mailIntent.data = data
        context.startActivity(Intent.createChooser(mailIntent, "Send mail..."))
    }


}