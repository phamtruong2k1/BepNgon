package com.phamtruong.bepngon.ui.save

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivitySavePostBinding
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.adapter.EventClickPostsAdapterListener
import com.phamtruong.bepngon.ui.adapter.PostsAdapter
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.view.show

class SavePostActivity : AppCompatActivity() , EventClickPostsAdapterListener {

    private lateinit var binding : ActivitySavePostBinding
    lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PostsAdapter(this, ArrayList<PostModel>(), this)
        binding.rcySavePost.adapter = adapter

        initView()

        initData()

    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun initData() {
        mDatabase.child(FBConstant.POST_F).get().addOnSuccessListener { dataSnapshot->
            val listData = ArrayList<PostModel>()
            for (postSnapshot in dataSnapshot.children) {
                postSnapshot.getValue<PostModel>()?.let {
//                    if (DataUtil.checkSearch(it.tag, data))
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
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun clickPost(post: PostModel) {

    }
}