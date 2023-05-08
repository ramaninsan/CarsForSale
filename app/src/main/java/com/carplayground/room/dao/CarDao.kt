package com.carplayground.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.carplayground.room.tables.Car

@Dao
interface CarDao {
    @Insert
    suspend fun insertAll(cars: List<Car>)
    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<Car>
}