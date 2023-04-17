package com.phamtruong.bepngon.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityRoomChatBinding

class RoomChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}