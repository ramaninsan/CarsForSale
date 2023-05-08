package com.carplayground.ui.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carplayground.dataSource.local.CarRepo
import com.carplayground.model.CarsInfoModel
import com.carplayground.room.tables.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(private val repository: CarRepo) : ViewModel() {


    private var _carDetailsList = MutableLiveData<List<CarsInfoModel>>()
    val carDetailsList: LiveData<List<CarsInfoModel>> = _carDetailsList

    private var _isDataNeedToDownload = MutableLiveData<Boolean>()
    val isDataNeedToDownload: LiveData<Boolean> = _isDataNeedToDownload

    private var _carDetailsFilteredList = MutableLiveData<List<CarsInfoModel>?>()
    val carDetailsFilteredList: LiveData<List<CarsInfoModel>?> = _carDetailsFilteredList

    init {
        getCarListFromDatabase()
    }


    private fun getCarListFromDatabase() {
        viewModelScope.launch {
            val cardList = repository.getCars()
            if (cardList.isEmpty()) {
                _isDataNeedToDownload.value = true
                return@launch
            }
            cardList.apply { first().isExpanded = true }
            _carDetailsList.value = cardList
            _carDetailsFilteredList.value = cardList

        }
    }


    /**
     * Save Car List to Local DB
     * */
    fun saveDb(list: List<Car>) {

        if (list.isNotEmpty()) {
            viewModelScope.launch {
                repository.insertCars(list)
                getCarListFromDatabase()
            }
        }
    }

    /**
     * File Car List with Model
     * */
    fun filterCarList(filter: String) {
        val filterList = _carDetailsList.value?.filter { (it.copy().carInfo.model == filter) }
        _carDetailsFilteredList.value = filterList
    }

    /**
     * Reset Car List to default state
     * */
    fun resetFilter() {
        _carDetailsFilteredList.value = _carDetailsList.value
    }


    /**
     * Expand and collapse Car List by clicking on Items
     * */
    fun expandSelectedCarInfo(carItem: CarsInfoModel) {
        val expandedList = _carDetailsFilteredList.value?.map {
            val item = it.copy()
            item.isExpanded = (it.carInfo.id == carItem.carInfo.id && carItem.isExpanded.not())
            item
        }
        _carDetailsFilteredList.value = expandedList
    }


}