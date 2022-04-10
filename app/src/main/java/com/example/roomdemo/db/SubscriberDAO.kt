package com.example.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {
    /**
     * NOTE
     *   Annotation is the most important, as room will check into the annotation rather than the function name
     *   Room doesn't support database access on the main thread, therefore keeping the insert function as suspend,
     *   and we need to execute this function in the background thread, we'll use coroutine for the same
     * **/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubscriber(subscriber: Subscriber): Long // return type will be row id of type long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll()

    /**
     * ROOM facilitates us to get data from table as live data, and by default Room will executes these queries on the background
     * thread, so we don't need to manually handle them using coroutine, async etc
     * */
    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): Flow<List<Subscriber>>
}