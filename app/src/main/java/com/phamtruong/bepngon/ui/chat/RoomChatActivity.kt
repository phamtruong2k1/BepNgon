package com.phamtruong.bepngon.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.ActivityRoomChatBinding
import com.phamtruong.bepngon.model.chat.RoomChatModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.util.SharePreferenceUtils

class RoomChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomChatBinding
    private lateinit var adapter: RoomChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RoomChatAdapter(this, ArrayList(), object : RoomChatAdapterListener{
            override fun click(roomChatModel: RoomChatModel) {

            }
        })

        binding.msgShow.adapter = adapter

        getAllRoom()
    }

    private fun getAllRoom() {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.CHAT_F)
        mDatabase.child(FBConstant.ROOM_F).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<RoomChatModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<RoomChatModel>()?.let {
                        if ( (it.accountId_1 == SharePreferenceUtils.getAccountID()
                            || it.accountId_2 == SharePreferenceUtils.getAccountID()
                        ) && it.status) {
                            listData.add(
                                it
                            )
                        }
                    }
                }
                adapter.setListData(listData)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                
            }
        })
    }
}