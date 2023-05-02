package com.phamtruong.bepngon.ui.personalpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.ActivityWithoutPageBinding
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.sever.ProfileFBListener
import com.phamtruong.bepngon.sever.ProfileFBUtil
import com.phamtruong.bepngon.ui.adapter.EventClickPostsAdapterListener
import com.phamtruong.bepngon.ui.adapter.PostsAdapter
import com.phamtruong.bepngon.ui.chat.ChatActivity
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.setOnSafeClick
import com.phamtruong.bepngon.view.show
import com.squareup.picasso.Picasso

class WithoutPageActivity : AppCompatActivity() , SwipeRefreshLayout.OnRefreshListener,
    EventClickPostsAdapterListener {

    lateinit var binding: ActivityWithoutPageBinding

    lateinit var adapter: PostsAdapter

    var idUser = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithoutPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PostsAdapter(this@WithoutPageActivity, ArrayList<PostModel>(), this)
        binding.rcyBaiDang.adapter = adapter

        binding.toolBar.txtTitle.text = "Trang cá nhân"
        binding.toolBar.imgBack.show()
        binding.toolBar.imgBack.setOnSafeClick { onBackPressed() }

        idUser = intent.getStringExtra("idUser").toString()

        getDataUser()

        binding.swipLayout.setOnRefreshListener(this)

        initListener()
    }

    private fun initListener() {
        binding.llChat.setOnClickListener {
            openActivity(ChatActivity::class.java,
                bundleOf(
                    "idUser" to SharePreferenceUtils.getAccountID(),
                    "idYour" to idUser,
                )
            )
        }
    }

    private fun getDataUser() {
        ProfileFBUtil.getProfile(idUser, object : ProfileFBListener {
            override fun actionSuccess(profileModel: ProfileModel) {
                updateUiProfile(profileModel)
            }

            override fun actionFail() {

            }
        })
    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun getPostData() {
        val query: Query =
            mDatabase.child(FBConstant.POST_F).orderByChild("accountId").equalTo(
                idUser
            )
        query.get().addOnSuccessListener { dataSnapshot->
            if (dataSnapshot.exists()) {
                val listData = ArrayList<PostModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<PostModel>()?.let {
                        listData.add(
                            it
                        )
                    }
                }
                adapter.setListData(listData)
            }
            binding.swipLayout.isRefreshing = false
        }.addOnFailureListener {
            binding.swipLayout.isRefreshing = false
        }
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

        getPostData()
    }

    override fun onRefresh() {
        getDataUser()
    }

    override fun clickPost(post: PostModel) {

    }
}