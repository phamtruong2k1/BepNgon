package com.phamtruong.bepngon.ui.personalpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityDangBaiBinding
import com.phamtruong.bepngon.databinding.ActivityPersonalPageBinding
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.sever.ProfileFBListener
import com.phamtruong.bepngon.sever.ProfileFBUtil
import com.phamtruong.bepngon.ui.adapter.EventClickPostsAdapterListener
import com.phamtruong.bepngon.ui.adapter.PostsAdapter
import com.phamtruong.bepngon.util.DataHelper
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.setOnSafeClick
import com.phamtruong.bepngon.view.show
import com.squareup.picasso.Picasso

class PersonalPageActivity : AppCompatActivity() , SwipeRefreshLayout.OnRefreshListener,
    EventClickPostsAdapterListener {

    lateinit var binding: ActivityPersonalPageBinding
    lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolBar.txtTitle.text = "Trang cá nhân"
        binding.toolBar.imgBack.show()
        binding.toolBar.imgBack.setOnSafeClick { onBackPressed() }

        adapter = PostsAdapter(this@PersonalPageActivity, ArrayList<PostModel>(), this)
        binding.rcyBaiDang.adapter = adapter

        DataHelper.profileUser.observe(this){
            updateUiProfile(it)
            getPostData()
        }

        binding.btnChange.setOnSafeClick {
            openActivity(ProfileActivity::class.java)
        }

        binding.swipLayout.setOnRefreshListener(this)

    }

    private fun getDataUser() {
        ProfileFBUtil.getProfile(SharePreferenceUtils.getAccountID(), object : ProfileFBListener {
            override fun actionSuccess(profileModel: ProfileModel) {
                DataHelper.profileUser.postValue(profileModel)
            }

            override fun actionFail() {

            }
        })
    }


    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun getPostData() {
        mDatabase.child(FBConstant.POST_F).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val listData = ArrayList<PostModel>()
                    for (postSnapshot in snapshot.children) {
                        postSnapshot.getValue<PostModel>()?.let {
                            if (it.accountId == SharePreferenceUtils.getAccountID())
                            listData.add(
                                it
                            )
                        }
                    }
                    adapter.setListData(listData)
                }
                binding.swipLayout.isRefreshing = false
            }

            override fun onCancelled(error: DatabaseError) {
                binding.swipLayout.isRefreshing = false
            }
        })
    }

    private fun updateUiProfile(it: ProfileModel) {
        Picasso.get().load(it.avt).into(binding.userAvatar)
        binding.toolBar.txtTitle.text = it.name
        binding.userName.text = it.name
        binding.txtAddress.text = it.address
        binding.txtBirthDay.text = it.birthDay
        binding.txtPhone.text = it.phoneNumber
        binding.txtGmail.text = it.gmail
        binding.txtGender.text = if (it.gender) "Nam" else "Nữ"
    }

    override fun onRefresh() {
        getDataUser()
    }

    override fun clickPost(post: PostModel) {

    }
}