package com.phamtruong.bepngon.ui.main.menu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentMenuBinding
import com.phamtruong.bepngon.ui.chat.ChatActivity
import com.phamtruong.bepngon.ui.sign.SignActivity
import com.phamtruong.bepngon.util.Constant
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
    }

    private lateinit var auth: FirebaseAuth

    override fun initViewCreated() {
        binding.toolBar.txtTitle.text = "Menu"
        binding.toolBar.imgChat.gone()
        binding.toolBar.imgSearch.gone()
        binding.toolBar.imgChat.setOnSafeClick {
            requireContext().openActivity(ChatActivity::class.java)
        }
        auth = Firebase.auth
        val currentUser = auth.currentUser
        updateUI(currentUser)

        initListener()
    }

    private fun initListener() {
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            requireContext().openActivity(SignActivity::class.java)
            requireActivity().finish()
        }
        binding.llHelp.setOnClickListener { support(requireContext()) }
        binding.llFeedback.setOnClickListener { feedBack(requireContext()) }
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.apply {
            photoUrl?.let { Picasso.get().load(it).into(binding.userAvatar) }
            binding.userName.text = displayName
        } ?: kotlin.run {

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