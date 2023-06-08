package com.example.vein



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class OffersAdapter(val context:Context,val diseaseList:ArrayList<DonationOffer>)
    : RecyclerView.Adapter<OffersAdapter.OrderListViewHolder>() {
    private lateinit var onItemClick: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(pos:Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        onItemClick=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val view :View =LayoutInflater.from(context).inflate(R.layout.recycler_content,parent,false)
        return OrderListViewHolder(view,onItemClick)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        val currentDisease = diseaseList[position]
        holder.name.text = "Name: "+currentDisease.name
        holder.address.text = "type: "+currentDisease.bloodType
        holder.phone.text = currentDisease.phone
        holder.city.text = currentDisease.city
    }

    override fun getItemCount(): Int {
        return diseaseList.size
    }
    class OrderListViewHolder(item: View,listener: onItemClickListener) :RecyclerView.ViewHolder(item){

        val name = item.findViewById<TextView>(R.id.name)
        val address = item.findViewById<TextView>(R.id.address)
        val phone = item.findViewById<TextView>(R.id.phone)
        val city = item.findViewById<TextView>(R.id.city)
        init {
            itemView.setOnClickListener {
                listener.onItemClick( adapterPosition)
            }
        }

    }

}