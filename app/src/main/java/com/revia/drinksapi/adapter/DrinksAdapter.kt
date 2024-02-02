package com.revia.drinksapi.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.revia.drinksapi.R
import com.revia.drinksapi.model.response.Drink


class DrinksAdapter(var context: Context, var list: ArrayList<Drink>, var listener: OnItemClickListener)
    :RecyclerView.Adapter<DrinksAdapter.DrinksViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {
       return DrinksViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        val drink: Drink =list[position]
        holder.drinkName.text=drink.strDrink
        holder.poytaxtTextview.text=drink.strAlcoholic
        // it is
        val requestOptions = RequestOptions()
            .placeholder(R.color.card1)
            .error(R.color.card1)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()

        Glide.with(context)
            .applyDefaultRequestOptions(requestOptions)
            .load(Uri.parse(drink.strDrinkThumb))
            //.transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.drinkImage)

        holder.initialize(list[position],listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(listRestaurant: ArrayList<Drink>){
        val oldItems=list
        val diffResult: DiffUtil.DiffResult= DiffUtil.calculateDiff(
            RestaurantItemDiffCalback(oldItems,listRestaurant))

        list=listRestaurant
        diffResult.dispatchUpdatesTo(this)
    }
    class RestaurantItemDiffCalback(
        var oldItems :List<Drink>,
        var newItems:List<Drink>
    ): DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldItems[oldItemPosition].strDrink==newItems.get(oldItemPosition).strDrink)
        }

        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems.get(newItemPosition)
        }
    }
    class DrinksViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
       val drinkName: TextView = itemView.findViewById<TextView>(R.id.drink_name)!!
       val poytaxtTextview: TextView =itemView.findViewById<TextView>(R.id.poytaxt_textview)
       val drinkImage: ImageView =itemView.findViewById<ImageView>(R.id.drink_image)

        fun initialize(item:Drink,action:OnItemClickListener){
            itemView.setOnClickListener {
                action.onItemClick(item,adapterPosition)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(item: Drink, position: Int)
    }

}