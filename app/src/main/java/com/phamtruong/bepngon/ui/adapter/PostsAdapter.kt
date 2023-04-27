package com.phamtruong.bepngon.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.model.ReactionModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.baidang.DetailBaiDangActivity
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.ui.personalpage.WithoutPageActivity
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.hide
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.show
import com.squareup.picasso.Picasso

class PostsAdapter(
    var context: Context,
    private var listData: List<PostModel>,
    val listener: EventClickPostsAdapterListener
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgAvatar: ImageView = view.findViewById(R.id.imgAvatar)
        var imgMore: ImageView = view.findViewById(R.id.imgMore)
        var imgComment: ImageView = view.findViewById(R.id.imgComment)

        var txtName: TextView = view.findViewById(R.id.txtName)
        var txtTime: TextView = view.findViewById(R.id.txtTime)
        var txtContent: TextView = view.findViewById(R.id.txtContent)
        var numberLike: TextView = view.findViewById(R.id.numberLike)


        var layoutImage: LinearLayout = view.findViewById(R.id.layoutImage)
        var image01: ImageView = view.findViewById(R.id.image01)
        var llImage02: LinearLayout = view.findViewById(R.id.llImage02)
        var image02: ImageView = view.findViewById(R.id.image02)
        var image03: ImageView = view.findViewById(R.id.image03)


        var rlReaction: RelativeLayout = view.findViewById(R.id.rlReaction)
        var imgHeart: ImageView = view.findViewById(R.id.imgHeart)
        var imgHeartFill: ImageView = view.findViewById(R.id.imgHeartFill)


        var viewRoot: LinearLayout = view.findViewById(R.id.viewRoot)


    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_bai_viet, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val post = listData[position]

        viewHolder.txtContent.text = post.content

        if (post.img != "") {
            viewHolder.layoutImage.show()
            Picasso.get().load(post.img).into(viewHolder.image01)
        } else {
            viewHolder.layoutImage.gone()
        }

        FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
            .child(FBConstant.PROFILE).child(post.accountId).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    val profileModel = result.getValue<ProfileModel>()
                    if (profileModel != null) {
                        viewHolder.txtName.text = profileModel.name
                        Picasso.get().load(profileModel.avt).into(viewHolder.imgAvatar)
                    }
                }
            }

        viewHolder.rlReaction.setOnClickListener {
            if (viewHolder.imgHeart.isVisible) {
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
                        viewHolder.imgHeart.gone()
                        viewHolder.imgHeartFill.show()
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
                                    viewHolder.imgHeart.show()
                                    viewHolder.imgHeartFill.gone()
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
                            viewHolder.imgHeart.gone()
                            viewHolder.imgHeartFill.show()
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
                    viewHolder.numberLike.text = String.format("%d", dataSnapshot.childrenCount)
                } else {
                    viewHolder.numberLike.text = "0"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        viewHolder.imgAvatar.setOnClickListener {
            if (post.accountId == SharePreferenceUtils.getAccountID()) {
                context.openActivity(
                    PersonalPageActivity::class.java
                )
            } else {
                context.openActivity(
                    WithoutPageActivity::class.java,
                    bundleOf("idUser" to post.accountId)
                )
            }

        }

        viewHolder.viewRoot.setOnClickListener {
            context.openActivity(
                DetailBaiDangActivity::class.java,
                bundleOf("post_data" to post.toJson())
            )
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListData(arr: List<PostModel>) {
        listData = arr
        notifyDataSetChanged()
    }

    override fun getItemCount() = listData.size

}

interface EventClickPostsAdapterListener {
    fun clickPost(post: PostModel)
}