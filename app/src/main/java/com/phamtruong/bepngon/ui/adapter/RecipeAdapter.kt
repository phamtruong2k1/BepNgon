package com.phamtruong.bepngon.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.view.show
import com.squareup.picasso.Picasso

class RecipeAdapter(
    var context: Context,
    private var listData: List<PostModel>,
    val listener: EventClickRecipeAdapterListener
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgAvatar: ImageView = view.findViewById(R.id.imgAvatar)
        var imgMore: ImageView = view.findViewById(R.id.imgMore)
        var imgLike: ImageView = view.findViewById(R.id.imgLike)
        var imgDislike: ImageView = view.findViewById(R.id.imgDislike)
        var imgComment: ImageView = view.findViewById(R.id.imgComment)

        var txtName : TextView = view.findViewById(R.id.txtName)
        var txtTime : TextView = view.findViewById(R.id.txtTime)
        var txtContent : TextView = view.findViewById(R.id.txtContent)
        var numberLike : TextView = view.findViewById(R.id.numberLike)
        var numberDislike : TextView = view.findViewById(R.id.numberDislike)


        var layoutImage : LinearLayout = view.findViewById(R.id.layoutImage)
        var image01: ImageView = view.findViewById(R.id.image01)
        var llImage02 : LinearLayout = view.findViewById(R.id.llImage02)
        var image02: ImageView = view.findViewById(R.id.image02)
        var image03: ImageView = view.findViewById(R.id.image03)


    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_bai_viet, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val post = listData[position]
        viewHolder.numberLike.text = post.like.toString()
        viewHolder.numberDislike.text = post.disLike.toString()
        viewHolder.txtContent.text = post.content

        if (post.img != ""){
            viewHolder.layoutImage.show()
            Picasso.get().load(post.img).into(viewHolder.image01)
        }

        viewHolder.imgLike.setOnClickListener {

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListData(arr : List<PostModel>) {
        listData = arr
        notifyDataSetChanged()
    }

    override fun getItemCount() = listData.size

}

interface EventClickRecipeAdapterListener {
    fun click(idTab : Int)
}