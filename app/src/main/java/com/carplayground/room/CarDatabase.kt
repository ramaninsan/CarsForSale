package com.carplayground.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.carplayground.room.converter.AppConverters
import com.carplayground.room.dao.CarDao
import com.carplayground.room.tables.Car


@Database(entities = [Car::class], version = 1, exportSchema = false)
@TypeConverters(AppConverters::class)
public abstract class CarDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
}