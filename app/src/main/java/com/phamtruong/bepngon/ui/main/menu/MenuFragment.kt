package com.phamtruong.bepngon.ui.main.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentMenuBinding
import com.phamtruong.bepngon.model.UserProfile
import com.phamtruong.bepngon.ui.sign.SignActivity
import com.phamtruong.bepngon.util.FirebaseDatabaseUtil
import com.phamtruong.bepngon.view.openActivity
import com.squareup.picasso.Picasso

class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    companion object {
        const val REQ_ONE_TAP = 1111
    }

    private lateinit var auth: FirebaseAuth

    override fun initViewCreated() {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        updateUI(currentUser)
        /*FirebaseDatabaseUtil.getProfile()
        FirebaseDatabaseUtil.addNewProfile(UserProfile("Trường", 20, "xyz@gmail.com"))*/

        initListener()
    }

    private fun initListener() {
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            requireContext().openActivity(SignActivity::class.java)
            requireActivity().finish()
        }
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


}