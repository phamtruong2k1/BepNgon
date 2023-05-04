package com.phamtruong.bepngon.ui.personalpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.ActivityWithoutPageBinding
import com.phamtruong.bepngon.databinding.LayoutBottomSheetPostBinding
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.model.ReactionModel
import com.phamtruong.bepngon.model.SaveModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.sever.FirebaseDatabaseUtil
import com.phamtruong.bepngon.sever.ProfileFBListener
import com.phamtruong.bepngon.sever.ProfileFBUtil
import com.phamtruong.bepngon.ui.adapter.EventClickPostsAdapterListener
import com.phamtruong.bepngon.ui.adapter.PostsAdapter
import com.phamtruong.bepngon.ui.chat.ChatActivity
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.gone
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

        //initData()
    }

    /*private fun initData() {
        mDatabase.child(FBConstant.FOLLOW_F).orderByChild("accountId")
            .equalTo(SharePreferenceUtils.getAccountID())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        adapter.setListData(ArrayList())
                        binding.rcySavePost.removeAllViews()
                        for (postSnapshot in dataSnapshot.children) {
                            postSnapshot.getValue<SaveModel>()?.let {
                                getPostData(it.postId)
                            }
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }*/

    private fun initListener() {
        binding.llChat.setOnClickListener {
            openActivity(ChatActivity::class.java,
                bundleOf(
                    "idUser" to SharePreferenceUtils.getAccountID(),
                    "idYour" to idUser,
                )
            )
        }

        binding.llFollow.setOnClickListener {

        }

        binding.llFollowed.setOnClickListener {

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
        mDatabase.child(FBConstant.POST_F).get().addOnSuccessListener { dataSnapshot->
            if (dataSnapshot.exists()) {
                val listData = ArrayList<PostModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<PostModel>()?.let {
                        if (it.accountId == idUser)listData.add(
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

    override fun clickPost(post : PostModel, position : Int) {
        showBottomSheet(post.accountId == SharePreferenceUtils.getAccountID(), post, position)
    }

    private fun showBottomSheet(boolean: Boolean, post : PostModel, position : Int) {
        val bottomSheetBinding = LayoutBottomSheetPostBinding.inflate(layoutInflater)
        val moreBottomSheet =
            BottomSheetDialog(this)
        moreBottomSheet.setContentView(bottomSheetBinding.root)

        if (boolean) {
            bottomSheetBinding.llDelete.show()
        } else {
            bottomSheetBinding.llDelete.gone()
        }

        val querySave: Query = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
            .child(FBConstant.SAVE_F)
            .orderByChild("accountId").equalTo(SharePreferenceUtils.getAccountID())
        querySave.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (appleSnapshot in dataSnapshot.children) {
                        val reactionModel = appleSnapshot.getValue<ReactionModel>()
                        if (reactionModel?.postId == post.postId) {
                            bottomSheetBinding.llSave.gone()
                            bottomSheetBinding.llSaved.show()
                            return
                        }
                    }
                    bottomSheetBinding.llSave.show()
                    bottomSheetBinding.llSaved.gone()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                bottomSheetBinding.llSave.show()
                bottomSheetBinding.llSaved.gone()
            }
        })

        bottomSheetBinding.llDelete.setOnClickListener {
            FirebaseDatabase.getInstance().getReference(FirebaseDatabaseUtil.ROOT)
                .child(FBConstant.POST_F).child(post.postId).removeValue().addOnSuccessListener {
                    moreBottomSheet.dismiss()
                    adapter.notifyItemRemoved(position)
                }
        }

        bottomSheetBinding.llSave.setOnClickListener {
            val saveModel = SaveModel(
                DataUtil.ConvertToMD5(DataUtil.getTime()),
                SharePreferenceUtils.getAccountID(),
                post.postId,
                DataUtil.getTime()
            )
            FirebaseDatabase.getInstance().getReference(FirebaseDatabaseUtil.ROOT)
                .child(FBConstant.SAVE_F)
                .child(saveModel.saveId).setValue(saveModel).addOnSuccessListener {
                    moreBottomSheet.dismiss()
                }
        }

        bottomSheetBinding.llSaved.setOnClickListener {
            val query2: Query = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
                .child(FBConstant.SAVE_F)
                .orderByChild("accountId").equalTo(SharePreferenceUtils.getAccountID())
            query2.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (appleSnapshot in dataSnapshot.children) {
                            val reactionModel = appleSnapshot.getValue<ReactionModel>()
                            if (reactionModel?.postId == post.postId) {
                                appleSnapshot.ref.removeValue()
                                moreBottomSheet.dismiss()
                                return
                            }
                        }

                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

        moreBottomSheet.show()
    }
}