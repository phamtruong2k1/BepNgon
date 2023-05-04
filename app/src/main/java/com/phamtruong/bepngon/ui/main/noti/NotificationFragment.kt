package com.phamtruong.bepngon.ui.main.noti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.FragmentNotificationBinding
import com.phamtruong.bepngon.model.NotificationModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.adapter.FriendAdapter
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast

class NotificationFragment : Fragment() {

    lateinit var binding: FragmentNotificationBinding

    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        val  listDemo = ArrayList<NotificationModel>()

        listDemo.add(
            NotificationModel(
            "1",
            "1",
            "1",
            "https://firebasestorage.googleapis.com/v0/b/bepngon-63eb8.appspot.com/o/Group%207.png?alt=media&token=ee3bab49-ed33-41ad-9695-8f54ad895fb9",
            "Phạm Ánh Tuyết",
            "Đã thích bài viết của bạn",
            "09:12 03/05/2023"
        )
        )

        listDemo.add(NotificationModel(
            "1",
            "1",
            "1",
            "https://firebasestorage.googleapis.com/v0/b/bepngon-63eb8.appspot.com/o/images%2F5ccbb494-f53b-4be4-bf40-bf4b97f2c015?alt=media&token=48c85314-380c-484f-a7dd-c8fa087fd3b8",
            "Phạm Ánh Trường",
            "Đã thích bài viết của bạn",
            "09:02 03/05/2023"
        ))

        listDemo.add(NotificationModel(
            "1",
            "1",
            "1",
            "https://firebasestorage.googleapis.com/v0/b/bepngon-63eb8.appspot.com/o/images%2F5ccbb494-f53b-4be4-bf40-bf4b97f2c015?alt=media&token=48c85314-380c-484f-a7dd-c8fa087fd3b8",
            "Phạm Ánh Trường",
            "Đã bình luận một bài viết của bạn",
            "09:02 03/05/2023"
        ))

        adapter = NotificationAdapter(requireContext(), listDemo)
        binding.toolBar.txtTitle.text = "Thông báo"

        binding.rcyNotification.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNotification()
    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun getNotification() {

        mDatabase.child(FBConstant.NOTI_F).child(SharePreferenceUtils.getAccountID()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<NotificationModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<NotificationModel>()?.let {
                        listData.add(
                            it
                        )
                    }
                }
                listData.sort()
                adapter.setListData(listData)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                requireContext().showToast("Lỗi kết nối!")
            }
        })
    }


    
}