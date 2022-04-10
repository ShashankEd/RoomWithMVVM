package com.example.roomdemo.viiewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SubscriberViewModel(private val subscriberRepository: SubscriberRepository) : ViewModel() {

    val subscribers = subscriberRepository.subscribers
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteAllButtonText = MutableLiveData<String>()

    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteAllButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!

        if(isUpdateOrDelete) {

            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!

            update(subscriberToUpdateOrDelete)
        } else {
            insert(Subscriber(0,name,email))
        }
        //clear the livedata
        inputName.value = ""
        inputEmail.value = ""

    }
    fun clearAllorDelete() {
        if(isUpdateOrDelete) {
            delete(subscriber = subscriberToUpdateOrDelete)
        } else {
            deleteAll()
        }
    }

    fun insert(subscriber: Subscriber): Job =
        //now in order to call a suspend function we can use Viewmodel coroutine scope
        viewModelScope.launch {
            //call the insert suspend function
            subscriberRepository.insert(subscriber)
        }

    fun update(subscriber: Subscriber): Job = viewModelScope.launch {
        //update
        subscriberRepository.update(subscriber)
        //clear the text and change the button
        inputEmail.value = ""
        inputName.value = ""
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteAllButtonText.value = "Clear All"
    }

    fun delete(subscriber: Subscriber): Job =
        //now in order to call a suspend function we can use Viewmodel coroutine scope
        viewModelScope.launch {
            //call the insert suspend function
            subscriberRepository.delete(subscriber)
            //clear the text and change the button
            inputEmail.value = ""
            inputName.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteAllButtonText.value = "Clear All"
        }

    fun deleteAll(): Job =
        //now in order to call a suspend function we can use Viewmodel coroutine scope
        viewModelScope.launch {
            //call the insert suspend function
            subscriberRepository.deleteAll()
        }


    fun getSaveSubscribers() = liveData {
        subscriberRepository.subscribers.collect {
            emit(it)
        }
    }

    //On click on the list item, we'll dislay the name and email in the text fields and save button will
    //become update and clear will become delete
    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputEmail.value = subscriber.email
        inputName.value = subscriber.name
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteAllButtonText.value = "Delete"
    }

}