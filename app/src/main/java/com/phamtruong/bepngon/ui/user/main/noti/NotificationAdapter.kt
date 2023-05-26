package com.phamtruong.bepngon.ui.user.main.noti

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.model.NotificationModel
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.baidang.DetailBaiDangActivity
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.openActivity
import com.squareup.picasso.Picasso

class NotificationAdapter(
    var context: Context,
    private var listData: ArrayList<NotificationModel>
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgAvatar: ImageView = view.findViewById(R.id.imgAvatar)
        var txtName: TextView = view.findViewById(R.id.txtName)
        var txtContent: TextView = view.findViewById(R.id.txtContent)
        var txtTime: TextView = view.findViewById(R.id.txtTime)
        var llView: LinearLayout = view.findViewById(R.id.llView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_notification, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val noti = listData[position]

        Picasso.get().load(noti.img).into(viewHolder.imgAvatar)

        viewHolder.txtName.text = noti.name
        viewHolder.txtContent.text = noti.content
        viewHolder.txtTime.text = DataUtil.showTime(noti.create_time)

        viewHolder.llView.setOnClickListener {
            clickNoti(noti.post_Id)
        }
    }

    private fun clickNoti(postId : String) {

        FirebaseDatabase.getInstance().getReference(FBConstant.ROOT).child(FBConstant.POST_F).child(postId).get().addOnCompleteListener{ task->
            if (task.isSuccessful) {
                val result = task.result
                val post = result.getValue<PostModel>()
                if (post != null) {
                    context.openActivity(
                        DetailBaiDangActivity::class.java,
                        bundleOf("post_data" to post.toJson())
                    )
                }
            }

        }.addOnFailureListener {
            context.showToast("Lỗi kết nối!")
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListData(arr : ArrayList<NotificationModel>) {
        listData.clear()
        listData.addAll(arr)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listData.size

}