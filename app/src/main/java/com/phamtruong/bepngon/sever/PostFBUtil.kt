package com.phamtruong.bepngon.sever

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.model.PostModel

object PostFBUtil {

    fun insertPost(postModel: PostModel) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
        mDatabase.child(FBConstant.POST_F).child(
            postModel.postId
        ).setValue(postModel)
    }


    fun getAllPost(listener: FBListProfileListener) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
        mDatabase.child(FBConstant.POST_F).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<PostModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<PostModel>()?.let {
                        listData.add(
                            it
                        )
                    }
                }
                listener.actionSuccess(listData)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                listener.actionFail()
            }
        })
    }

    fun getMyPost(idUser : String, listener: FBListProfileListener) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
        mDatabase.child(FBConstant.POST_F).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<PostModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<PostModel>()?.let {
                        listData.add(
                            it
                        )
                    }
                }
                listener.actionSuccess(listData)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                listener.actionFail()
            }
        })
    }

    fun getPostById(idPost : String, listener: FBProfileListener) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
        mDatabase.child(FBConstant.POST_F).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.getValue<PostModel>()?.let {
                    listener.actionSuccess(it)
                } ?: kotlin.run {
                    listener.actionFail()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                listener.actionFail()
            }
        })
    }
}

interface FBListProfileListener {
    fun actionSuccess(listData : ArrayList<PostModel>)
    fun actionFail()
}

interface FBProfileListener {
    fun actionSuccess(postModel : PostModel)
    fun actionFail()
}