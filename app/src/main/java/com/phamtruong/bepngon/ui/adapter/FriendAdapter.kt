package com.phamtruong.bepngon.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.model.ProfileModel
import com.squareup.picasso.Picasso

class FriendAdapter(
    var context: Context,
    private var listData: ArrayList<ProfileModel>,
    val listener: EventClickFriendAdapterListener
) : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgAvatar: ImageView = view.findViewById(R.id.imgAvatar)
        var imgMore: ImageView = view.findViewById(R.id.imgMore)
        var txtName: TextView = view.findViewById(R.id.txtName)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_friend, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val friend = listData[position]

        Picasso.get().load(friend.avt).into(viewHolder.imgAvatar)

        viewHolder.txtName.text = friend.name

        viewHolder.imgMore.setOnClickListener {
            listener.clickMoreFriend(friend)
        }

        viewHolder.imgAvatar.setOnClickListener {
            listener.clickAvatarFriend(friend.accountId)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListData(arr : ArrayList<ProfileModel>) {
        listData.clear()
        listData.addAll(arr)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listData.size

}

interface EventClickFriendAdapterListener {
    fun clickMoreFriend(friend : ProfileModel)
    fun clickAvatarFriend(accountID : String)
}