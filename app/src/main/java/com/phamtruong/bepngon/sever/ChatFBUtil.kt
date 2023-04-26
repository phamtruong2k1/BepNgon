package com.phamtruong.bepngon.sever

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.chat.MessageModel
import com.phamtruong.bepngon.model.chat.RoomChatModel

object ChatFBUtil {

    fun insertRoomChat(roomChatModel: RoomChatModel) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.CHAT_F)
        mDatabase.child(FBConstant.ROOM_F).child(
            roomChatModel.room_id
        ).setValue(roomChatModel)
    }

    fun insertMessage(messageModel: MessageModel) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.CHAT_F)
        mDatabase.child(FBConstant.MESSAGE)
            .child(messageModel.room_id)
            .child(messageModel.message_id)
            .setValue(messageModel)
    }


    fun getAllRoom(listener: FBListRoomListener) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.CHAT_F)
        mDatabase.child(FBConstant.ROOM_F).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<RoomChatModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<RoomChatModel>()?.let {
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
}

interface FBListRoomListener {
    fun actionSuccess(listData : ArrayList<RoomChatModel>)
    fun actionFail()
}