package com.phamtruong.bepngon.ui.baidang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.ActivityDetailBaiDangBinding
import com.phamtruong.bepngon.model.CommentModel
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.CommentFBUtil
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.adapter.CommentAdapter
import com.phamtruong.bepngon.ui.adapter.EventClickCommentListener
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.ui.personalpage.WithoutPageActivity
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.show
import com.squareup.picasso.Picasso


class DetailBaiDangActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailBaiDangBinding

    private var postModel : PostModel? = null

    lateinit var adapterComment : CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBaiDangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postModel = PostModel.toPostModel(intent.getStringExtra("post_data").toString())

        adapterComment = CommentAdapter(this@DetailBaiDangActivity, ArrayList(), object : EventClickCommentListener{
            override fun click(commentModel: CommentModel) {

            }
        })

        postModel?.let {
            getPost(it)
            getComment(it)
            listerComment(it)
        }

        binding.rcyComment.adapter = adapterComment

        initListener()
    }

    private fun initListener() {
        binding.imgBack.setOnClickListener { finish() }
        binding.imgAvatar.setOnClickListener {
            if (postModel?.accountId == SharePreferenceUtils.getAccountID()) {
                openActivity(
                    PersonalPageActivity::class.java
                )
            } else {
                openActivity(WithoutPageActivity::class.java, bundleOf("idUser" to postModel?.accountId))
            }
        }

        binding.imgSendComment.setOnClickListener {
            postComment()
        }
    }

    private fun getPost(postData : PostModel) {
        binding.txtContent.text = postData.content

        if (postData.img != ""){
            binding.layoutImage.show()
            Picasso.get().load(postData.img).into(binding.image01)
        }

        FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
            .child(FBConstant.PROFILE).child(postData.accountId).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    val profileModel = result.getValue<ProfileModel>()
                    if (profileModel != null) {
                        binding.txtName.text = profileModel.name
                        Picasso.get().load(profileModel.avt).into(binding.imgAvatar)
                    }
                }
            }
    }

    private fun listerComment(post : PostModel) {
        val reference = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)

        val query: Query = reference.child(FBConstant.COMMENT_F).orderByChild("postId").equalTo(post.postId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val listData = ArrayList<CommentModel>()
                    for (issue in dataSnapshot.children) {
                        issue.getValue<CommentModel>()?.let {
                            listData.add(
                                it
                            )
                        }
                    }

                    adapterComment.setListData(listData)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun getComment(postData : PostModel) {

    }

    private fun postComment() {
        if (binding.edtComment.text.toString().trim().isNotEmpty()) {
            insertComment(
                CommentModel(
                    DataUtil.getTime(),
                    SharePreferenceUtils.getAccountID(),
                    postModel!!.postId,
                    binding.edtComment.text.toString(),
                    DataUtil.getTime()
                )
            )
            binding.edtComment.setText("")
        }
    }

    fun insertComment(commentModel: CommentModel) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
        mDatabase.child(FBConstant.COMMENT_F).child(
            commentModel.commentId
        ).setValue(commentModel)
    }

}