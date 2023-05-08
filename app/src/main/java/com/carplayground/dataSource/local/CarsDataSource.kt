package com.carplayground.dataSource.local

import com.carplayground.model.CarsInfoModel
import com.carplayground.room.tables.Car

interface CarsDataSource {
    suspend fun getCars() : List<CarsInfoModel>
    suspend fun insertCars(cars : List<Car>)
}