package com.carplayground.room.tables

import androidx.room.*

@Entity(tableName = "cars")
data class Car(
    val consList: List<String>,
    val customerPrice: Double,
    val make: String,
    val marketPrice: Double,
    val model: String,
    val prosList: List<String>,
    val rating: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)


