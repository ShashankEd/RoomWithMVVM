package com.example.roomdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class],version = 1)
abstract class SubscriberDatabase: RoomDatabase() {

    //create reference for DAO interface
    abstract val subscriberDAO: SubscriberDAO

    //We want to have only one instance of our Database class, therefore we'll use singleton using companion objects

    companion object {
        @Volatile // this will make this field instantly available to all the threads
        private var INSTANCE: SubscriberDatabase? = null
        fun getInstance(context: Context): SubscriberDatabase {
            synchronized(this){
                var instance:SubscriberDatabase? = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDatabase::class.java,
                        "subscriber_data_database"
                    ).build()

                }
                return instance
            }
        }
    }
}