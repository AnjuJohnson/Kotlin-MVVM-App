package com.example.mvvmsampleapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmsampleapp.data.db.entities.Quote
import com.example.mvvmsampleapp.data.db.entities.User


@Database(
    entities = [User::class, Quote::class],
    version = 1
)


abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getQuoteDao(): QuoteDao

    //to create the app database
    companion object {


        //this varaiable is visible to all other threads
        //instance of appdatabase initial value is null
        @Volatile
        private var instance: AppDatabase? = null

        //LOCK make sure that we do not create 2 instance of our database
        private val LOCK = Any()


        //  this first will check if this instance is not null,so if this is not null it will return the instance.
        //  If it is null it will go to synchrnizzed block
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            //again check instance is null,if it is null go to builddatabase
            instance ?: buildDatabase(context).also {
                //passing the return value of builddataabase function to instance if the instance  is null
                instance=it }
        }
//return the db
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            )
                .build()

    }
}