package com.phamtruong.bepngon.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.phamtruong.bepngon.R

class NotificationAdapterImagePostAdapter(
    var context: Context,
    private var listData: ArrayList<Uri>,
    val listener: EventClickNotificationListener
) : RecyclerView.Adapter<NotificationAdapterImagePostAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgImage: ImageView = view.findViewById(R.id.imgImage)
        var imgDelete: ImageView = view.findViewById(R.id.imgDelete)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_notification, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val image = listData[position]

        viewHolder.imgImage.setImageBitmap(MediaStore.Images.Media.getBitmap(context.contentResolver, image))

        viewHolder.imgDelete.setOnClickListener {

            listener.click(position)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListData(arr : ArrayList<Uri>) {
        listData = arr
        notifyDataSetChanged()
    }

    override fun getItemCount() = listData.size

}

interface EventClickNotificationListener {
    fun click(pos : Int)
}