package com.phamtruong.bepngon.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.ActivityChatBinding
import com.phamtruong.bepngon.model.chat.MessageModel
import com.phamtruong.bepngon.model.chat.RoomChatModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.util.DataUtil

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding

    lateinit var adapter: ChatAdapter

    var idUser = ""
    var idYour = ""

    var idRoom = ""
    var account1 = ""
    var account2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        idUser = intent.getStringExtra("idUser").toString()
        idYour = intent.getStringExtra("idYour").toString()

        createDataRoom(idUser, idYour)

        adapter = ChatAdapter(this, ArrayList())

        binding.msgShow.adapter = adapter

        initListener()

        initMessage()
    }

    private fun initMessage() {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.CHAT_F)
        mDatabase.child(FBConstant.MESSAGE).child(idRoom)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val listData = ArrayList<MessageModel>()
                    for (postSnapshot in dataSnapshot.children) {
                        postSnapshot.getValue<MessageModel>()?.let {
                            listData.add(
                                it
                            )
                        }
                    }
                    adapter.setListData(listData)
                    if (listData.size > 0 && !isStatusRoom) {
                        insertRoom(true)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
    }

    private fun initListener() {
        binding.btnSend.setOnClickListener {
            if (binding.edtMessage.text.toString().trim().isNotEmpty()) {
                sendMessage(
                    MessageModel(
                        DataUtil.getTime(),
                        idRoom,
                        idUser,
                        binding.edtMessage.text.toString(),
                        DataUtil.getTime()
                    )
                )
            }
        }
    }


    private fun createDataRoom(idUser: String, idYour: String) {
        if (idUser > idYour) {
            idRoom = DataUtil.ConvertToMD5(idUser+idYour)
            account1 = idUser
            account2 = idYour
        } else {
            idRoom = DataUtil.ConvertToMD5(idYour+idUser)
            account1 = idYour
            account2 = idUser
        }
        insertRoom()
    }

    private var isStatusRoom = false
    private fun insertRoom(boolean: Boolean = false) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.CHAT_F)
        mDatabase.child(FBConstant.ROOM_F).child(
            idRoom
        ).setValue(RoomChatModel(idRoom, account1, account2, boolean)).addOnSuccessListener {
            isStatusRoom = boolean
        }
    }

    private fun sendMessage(messageModel: MessageModel) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.CHAT_F)
        mDatabase.child(FBConstant.MESSAGE)
            .child(messageModel.room_id)
            .child(messageModel.message_id)
            .setValue(messageModel).addOnSuccessListener {
                insertRoom(true)
            }
    }

}
