package com.phamtruong.bepngon.ui.main.menu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentMenuBinding
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.ui.save.SavePostActivity
import com.phamtruong.bepngon.ui.sign.SignActivity
import com.phamtruong.bepngon.ui.tienich.bmi.ChiSoBmiActivity
import com.phamtruong.bepngon.ui.tienich.chedoan.CheDoAnActivity
import com.phamtruong.bepngon.ui.tienich.random.RandomActivity
import com.phamtruong.bepngon.util.DataHelper
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.openActivity
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

        binding.llSaved.setOnClickListener {
            requireContext().openActivity(SavePostActivity::class.java)
        }

        binding.llRandom.setOnClickListener {
            requireContext().openActivity(RandomActivity::class.java)
        }

        binding.llCheDoAn.setOnClickListener {
            requireContext().openActivity(CheDoAnActivity::class.java)
        }

        binding.llBmi.setOnClickListener {
            requireContext().openActivity(ChiSoBmiActivity::class.java)
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
        context.startActivity(Intent.createChooser(mailIntent, "Gửi mail..."))
    }

    private fun feedBack(context: Context) {
        val mailIntent = Intent(Intent.ACTION_VIEW)
        val data =
            Uri.parse("mailto:?SUBJECT=$FeedBack&body=&to=$EMAIL")
        mailIntent.data = data
        context.startActivity(Intent.createChooser(mailIntent, "Gửi mail..."))
    }


}