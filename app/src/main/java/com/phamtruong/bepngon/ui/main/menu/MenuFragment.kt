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
import com.phamtruong.bepngon.ui.sign.SignActivity
import com.phamtruong.bepngon.util.Constant
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.setOnSafeClick
import com.squareup.picasso.Picasso

class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    companion object {
        const val REQ_ONE_TAP = 1111

        const val EMAIL = "phamtruong28092001@gmail.com"
        const val FeedBack = "FeedBack"
        const val HELP = "Help"

        const val ROOT = "root"
        const val PROFILE = "profile"
        const val ACCOUNT = "account"
    }

    override fun initViewCreated() {
        binding.toolBar.txtTitle.text = "Menu"
        binding.toolBar.imgChat.gone()
        binding.toolBar.imgSearch.gone()
        binding.toolBar.imgChat.setOnSafeClick {
            requireContext().openActivity(ChatActivity::class.java)
        }

        initView()

        initListener()

    }

    val mDatabase = FirebaseDatabase.getInstance().getReference(ROOT)
    private fun initView() {
        val id = SharePreferenceUtils.getAccountID()
        mDatabase.child(PROFILE).child(id).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                val account = result.getValue<ProfileModel>()
                if (account != null) {
                    updateUI(account)
                } else {
                    requireContext().showToast("Thông tin đăng nhập không đúng!")
                }
            }
        }.addOnFailureListener {
            requireContext().showToast("Có lỗi!")
        }
    }

    private fun initListener() {
        binding.btnLogout.setOnClickListener {
            SharePreferenceUtils.setAccountID(null)
            SharePreferenceUtils.setUserName(null)
            SharePreferenceUtils.setPassword(null)
            requireContext().openActivity(SignActivity::class.java)
            requireActivity().finish()
        }
        binding.llHelp.setOnClickListener { support(requireContext()) }
        binding.llFeedback.setOnClickListener { feedBack(requireContext()) }
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


    fun support(context: Context) {
        val mailIntent = Intent(Intent.ACTION_VIEW)
        val data =
            Uri.parse("mailto:?SUBJECT=$FeedBack&body=&to=$EMAIL")
        mailIntent.data = data
        context.startActivity(Intent.createChooser(mailIntent, "Send mail..."))
    }

    fun feedBack(context: Context) {
        val mailIntent = Intent(Intent.ACTION_VIEW)
        val data =
            Uri.parse("mailto:?SUBJECT=$FeedBack&body=&to=$EMAIL")
        mailIntent.data = data
        context.startActivity(Intent.createChooser(mailIntent, "Send mail..."))
    }


}