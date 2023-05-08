package com.carplayground.dataSource.local

import com.carplayground.model.CarsInfoModel
import com.carplayground.room.tables.Car
import com.carplayground.room.CarDatabase
import javax.inject.Inject

class CarRepo @Inject constructor(private val db : CarDatabase) : CarsDataSource {
    override suspend fun getCars(): List<CarsInfoModel> {
        return db.carDao().getAllCars().map { CarsInfoModel(false,it) }
    }

    override suspend fun insertCars(cars: List<Car>) {
         db.carDao().insertAll(cars)
    }


}