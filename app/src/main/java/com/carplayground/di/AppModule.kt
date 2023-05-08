package com.carplayground.di

import android.content.Context
import androidx.room.Room
import com.carplayground.dataSource.local.CarRepo
import com.carplayground.room.CarDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule{

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : CarDatabase{
        val builder = Room.databaseBuilder(context = context,CarDatabase::class.java,"car_db")
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideCarRepo(db : CarDatabase) : CarRepo{
        return CarRepo(db)
    }

}