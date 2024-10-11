package com.my.rewardsproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.rewardsproject.R
import com.my.rewardsproject.models.RewardsResponse
import com.my.rewardsproject.models.RewardsResponseItem

class ItemAdapter(private var itemList: List<RewardsResponseItem>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // ViewHolder for item layout
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.item_name)
        val itemId: TextView = view.findViewById(R.id.item_id)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.itemName.text = currentItem.name
        holder.itemId.text = currentItem.id.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }


    override fun getItemCount() = itemList.size

}