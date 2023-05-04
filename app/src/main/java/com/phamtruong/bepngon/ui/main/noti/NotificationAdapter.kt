package com.phamtruong.bepngon.ui.main.noti

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.model.NotificationModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.ui.adapter.FriendAdapter
import com.phamtruong.bepngon.util.DataUtil
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
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListData(arr : ArrayList<NotificationModel>) {
        listData.clear()
        listData.addAll(arr)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listData.size

}