package com.revia.drinksapi.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.revia.drinksapi.R
import com.revia.drinksapi.model.response.Drink

class DrinkFragment : Fragment() {
    lateinit var imageView: ImageView
    lateinit var tvName: TextView
    lateinit var tvIsAlcoholic: TextView
    lateinit var tvCategory: TextView
    lateinit var tvGlass: TextView
    lateinit var tvInstruction: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_drink, container, false)
        val drink = arguments?.getParcelable<Drink>("da")
        Log.d("DrinkFragment", "onCreateView: $drink")
        if (drink != null) {
            Log.d("DrinkFragment", "onCreateView: $drink")
        }
        imageView = view.findViewById(R.id.iv_image)
        tvName = view.findViewById(R.id.tv_Name)
        tvIsAlcoholic = view.findViewById(R.id.tv_isAlcoholic)
        tvCategory = view.findViewById(R.id.tv_Category)
        tvGlass = view.findViewById(R.id.tv_Glass)
        tvInstruction = view.findViewById(R.id.tv_instruction)

        tvName.text = drink?.strDrink
        tvIsAlcoholic.text = drink?.strAlcoholic
        tvCategory.text = drink?.strCategory
        tvGlass.text = drink?.strGlass
        tvInstruction.text = drink?.strInstructions
// it is

        Glide.with(this)
            .load(Uri.parse(drink?.strDrinkThumb))
            .into(imageView)
        return view
    }
}