package com.example.roomdemo.db

class SubscriberRepository(private val subscriberDAO: SubscriberDAO) {


    val subscribers = subscriberDAO.getAllSubscribers()

    //call the insert
    suspend fun insert(subscriber: Subscriber) {
        subscriberDAO.insertSubscriber(subscriber)
    }

    //for update
    suspend fun update(subscriber: Subscriber) {
        subscriberDAO.updateSubscriber(subscriber)
    }

    //delete
    suspend fun delete(subscriber: Subscriber) {
        subscriberDAO.deleteSubscriber(subscriber)
    }

    //delete all
    suspend fun deleteAll() {
        subscriberDAO.deleteAll()
    }

}