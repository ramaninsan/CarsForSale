package com.carplayground.dataSource.filesystem

import android.content.Context
import com.carplayground.R
import com.carplayground.room.tables.Car
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object FetchDataFromFile {

     fun fetchAndSaveDataToLocalDb(context: Context) : List<Car>{
         val jsonString = fetchJsonFromFile(context)
        return Gson().fromJson(jsonString, Array<Car>::class.java).toList()
    }


    private fun fetchJsonFromFile(context: Context) : String{
        val inputStream: InputStream = context.resources.openRawResource(R.raw.carsdata)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val jsonString = StringBuilder()

        try {
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                jsonString.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return jsonString.toString()
    }



}