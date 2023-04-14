package com.phamtruong.bepngon.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentMenuBinding
import com.phamtruong.bepngon.model.UserProfile
import com.phamtruong.bepngon.util.FirebaseDatabaseUtil
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
        FirebaseDatabaseUtil.getProfile()
        FirebaseDatabaseUtil.addNewProfile(UserProfile("Trường", 20, "xyz@gmail.com"))
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.apply {
            photoUrl?.let { Picasso.get().load(it).into(binding.userAvatar) }
            binding.userName.text = displayName
            binding.userGmail.text = email
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