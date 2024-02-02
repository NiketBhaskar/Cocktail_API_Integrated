package com.revia.drinksapi.model.response

import android.os.Parcel
import android.os.Parcelable

data class Drink(
    val dateModified: String?,
    val idDrink: String?,
    val strAlcoholic: String?,
    val strCategory: String?,
    val strCreativeCommonsConfirmed: String?,
    val strDrink: String?,
    val strDrinkAlternate: Any,
    val strDrinkThumb: String?,
    val strGlass: String?,
    val strIBA: String?,
    val strImageAttribution: String?,
    val strImageSource: String?,
    val strIngredient1: String?,
    val strIngredient10: Any,
    val strIngredient11: Any,
    val strIngredient12: Any,
    val strIngredient13: Any,
    val strIngredient14: Any,
    val strIngredient15: Any,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: Any,
    val strIngredient9: Any,
    val strInstructions: String?,
    val strInstructionsDE: String?,
    val strInstructionsES: Any,
    val strInstructionsFR: Any,
    val strInstructionsIT: String?,
    val strInstructionsZH_HANS: Any,
    val strInstructionsZH_HANT: Any,
    val strMeasure1: String?,
    val strMeasure10: Any,
    val strMeasure11: Any,
    val strMeasure12: Any,
    val strMeasure13: Any,
    val strMeasure14: Any,
    val strMeasure15: Any,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: Any,
    val strMeasure9: Any,
    val strTags: String?,
    val strVideo: Any
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("strDrinkAlternate"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("strIngredient10"),
        TODO("strIngredient11"),
        TODO("strIngredient12"),
        TODO("strIngredient13"),
        TODO("strIngredient14"),
        TODO("strIngredient15"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("strIngredient8"),
        TODO("strIngredient9"),
        parcel.readString(),
        parcel.readString(),
        TODO("strInstructionsES"),
        TODO("strInstructionsFR"),
        parcel.readString(),
        TODO("strInstructionsZH_HANS"),
        TODO("strInstructionsZH_HANT"),
        parcel.readString(),
        TODO("strMeasure10"),
        TODO("strMeasure11"),
        TODO("strMeasure12"),
        TODO("strMeasure13"),
        TODO("strMeasure14"),
        TODO("strMeasure15"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("strMeasure8"),
        TODO("strMeasure9"),
        parcel.readString(),
        TODO("strVideo")
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Drink> {
        override fun createFromParcel(parcel: Parcel): Drink {
            return Drink(parcel)
        }

        override fun newArray(size: Int): Array<Drink?> {
            return arrayOfNulls(size)
        }
    }
}