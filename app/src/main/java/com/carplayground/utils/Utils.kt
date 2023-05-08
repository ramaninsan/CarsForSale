package com.carplayground.utils

import com.carplayground.R


object Utils{
    fun getCarImageByModel(carID: String) : Int{
        return when(carID){
            "Land Rover" -> R.drawable.ic_aange_rover
            "Alpine" -> R.drawable.ic_alpine_roadster
            "BMW" -> R.drawable.ic_bmw
            "Mercedes Benz" -> R.drawable.ic_mercedez_benz
            else -> {  R.drawable.ic_tacoma}
        }
    }

    fun formatNumberToReadableAmount(number: Int): String {
        return when {
            number in 1000..999999 -> "${number / 1000}K"
            number in 1000000..999999999 -> "${number / 1000000}M"
            number >= 1000000000 -> "${number / 1000000000}B"
            else -> number.toString()
        }
    }

}


val AppOrangeColor = 0xfffc6015