package com.phamtruong.bepngon.ui.user.main.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.ActivitySearchBinding
import com.phamtruong.bepngon.databinding.LayoutBottomSheetPostBinding
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ReactionModel
import com.phamtruong.bepngon.model.SaveModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.sever.FirebaseDatabaseUtil
import com.phamtruong.bepngon.ui.adapter.EventClickPostsAdapterListener
import com.phamtruong.bepngon.ui.adapter.PostsAdapter
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.show

class SearchActivity : AppCompatActivity() , EventClickPostsAdapterListener {

    private lateinit var binding : ActivitySearchBinding
    lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PostsAdapter(this, ArrayList<PostModel>(), this)
        binding.rcyKetQua.adapter = adapter

        initView()

        initSearch()
    }

    private fun initSearch() {
        binding.edtSearch.doOnTextChanged { text, start, before, count ->
            if (text.toString().length > 2) {
                searchPost(text.toString())
            } else {
                adapter.setListData(ArrayList())
            }
        }
    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun searchPost(data: String) {
        mDatabase.child(FBConstant.POST_F).get().addOnSuccessListener { dataSnapshot->
            val listData = ArrayList<PostModel>()
            for (postSnapshot in dataSnapshot.children) {
                postSnapshot.getValue<PostModel>()?.let {
                    if (DataUtil.checkSearch(it.tag, data))
                        listData.add(
                            it
                        )
                }
            }
            adapter.setListData(listData)
        }.addOnFailureListener {
            //showToast("Lỗi kết nối!")
        }
    }

    private fun initView() {
        binding.toolBar.txtTitle.text = "Tìm kiếm"
        binding.toolBar.imgBack.show()
        binding.toolBar.imgBack.setOnClickListener {
            onBackPressed()
        }
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