package com.phamtruong.bepngon.ui.baidang

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityDetailBaiDangBinding
import com.phamtruong.bepngon.model.CommentModel
import com.phamtruong.bepngon.model.NotificationModel
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.model.ReactionModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.sever.FirebaseDatabaseUtil
import com.phamtruong.bepngon.ui.adapter.CommentAdapter
import com.phamtruong.bepngon.ui.adapter.EventClickCommentListener
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.ui.personalpage.WithoutPageActivity
import com.phamtruong.bepngon.util.DataHelper
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.gone
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
        binding.txtTime.text = DataUtil.showTime(postData.create_time)
        binding.txtTag.text = postData.tag

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

                    showDialog()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun showDialog() {
        val dialog = Dialog(this@DetailBaiDangActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.layout_dialog_report_post)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.show()
    }

    private fun getComment(post : PostModel) {
        binding.rlReaction.setOnClickListener {
            if (binding.imgHeart.isVisible) {
                val reactionModel = ReactionModel(
                    DataUtil.getIdByTime(),
                    SharePreferenceUtils.getAccountID(),
                    post.postId,
                    DataUtil.getTime()
                )
                FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
                    .child(FBConstant.REACTION_F).child(
                        reactionModel.reactionId
                    ).setValue(reactionModel).addOnSuccessListener {
                        binding.imgHeart.gone()
                        binding.imgHeartFill.show()
                        addNotification(postModel!!, "Đã thích một bài viết của bạn.")
                    }

            } else {
                val query2: Query = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
                    .child(FBConstant.REACTION_F)
                    .orderByChild("accountId").equalTo(SharePreferenceUtils.getAccountID())
                query2.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (appleSnapshot in dataSnapshot.children) {
                                val reactionModel = appleSnapshot.getValue<ReactionModel>()
                                if (reactionModel?.postId == post.postId) {
                                    appleSnapshot.ref.removeValue()
                                    binding.imgHeart.show()
                                    binding.imgHeartFill.gone()
                                    return
                                }
                            }

                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {}
                })
            }
        }

        val query2: Query = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
            .child(FBConstant.REACTION_F)
            .orderByChild("accountId").equalTo(SharePreferenceUtils.getAccountID())
        query2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (appleSnapshot in dataSnapshot.children) {
                        val reactionModel = appleSnapshot.getValue<ReactionModel>()
                        if (reactionModel?.postId == post.postId) {
                            binding.imgHeart.gone()
                            binding.imgHeartFill.show()
                            return
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })


        val reference = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)

        val query: Query =
            reference.child(FBConstant.REACTION_F).orderByChild("postId").equalTo(post.postId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    binding.numberLike.text = String.format("%d", dataSnapshot.childrenCount)
                } else {
                    binding.numberLike.text = "0"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun addNotification(post: PostModel, content : String) {
        if (post.accountId == SharePreferenceUtils.getAccountID())
            return
        val notifi = NotificationModel(
            DataUtil.ConvertToMD5(DataUtil.getTime()),
            post.accountId,
            post.postId,
            DataHelper.profileUser.value?.avt?: "",
            DataHelper.profileUser.value?.name?: "",
            content,
            DataUtil.getTime()
        )
        FirebaseDatabase.getInstance().getReference(FirebaseDatabaseUtil.ROOT)
            .child(FBConstant.NOTI_F).child(post.accountId)
            .child(notifi.create_time)
            .setValue(notifi)
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
            addNotification(postModel!!, "Đã bình luận một bài viết của bạn.")
        }
    }

    fun insertComment(commentModel: CommentModel) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
        mDatabase.child(FBConstant.COMMENT_F).child(
            commentModel.commentId
        ).setValue(commentModel)
    }

}