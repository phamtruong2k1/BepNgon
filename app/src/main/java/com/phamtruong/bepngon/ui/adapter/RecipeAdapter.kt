package com.phamtruong.bepngon.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.model.RecipeModel

class RecipeAdapter(
    var context: Context,
    private var listData: List<RecipeModel>,
    val listener: EventClickRecipeAdapterListener
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
//        var img: ImageView = view.findViewById(R.id.imgEijoy)
//        var name : TextView = view.findViewById(R.id.txtEijoy)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_bai_viet, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val recipe = listData[position]
//        viewHolder.name.text = context.getString(eijoy.name)
//
//        viewHolder.img.setImageDrawable(ResourcesCompat.getDrawable(context.resources, eijoy.img, null))
//
//        viewHolder.img.setOnTouchScale( {
//            PlaySound.soundClick(context)
//            listener.clickEijoyTab(eijoy.id)
//        }, 0.9f, false)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListData(arr : List<RecipeModel>) {
        listData = arr
        notifyDataSetChanged()
    }

    override fun getItemCount() = 9

}

interface EventClickRecipeAdapterListener {
    fun click(idTab : Int)
}