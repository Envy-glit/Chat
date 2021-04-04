package com.shkiper.chat.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.shkiper.chat.extensions.mutableLiveData
import com.shkiper.chat.models.data.User
import com.shkiper.chat.models.data.UserItem
import com.shkiper.chat.repositories.MainRepository
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    private val query = mutableLiveData("")
    private val userItems = mutableLiveData(mainRepository.users.value!!.map{it.toUserItem()})
    private val selectedItems = Transformations.map(userItems){users -> users.filter {it.isSelected}}

    fun getUsers(): LiveData<List<UserItem>>{
        val result = MediatorLiveData<List<UserItem>>()

        val filterF = {
            val queryStr = query.value!!
            val users = userItems.value!!

            result.value = if(queryStr.isEmpty()) users
            else users.filter { it.fullName.contains(queryStr,true) }
        }

        result.addSource(userItems){filterF.invoke()}
        result.addSource(query){filterF.invoke()}

        return result
    }

    fun getSelectedData(): LiveData<List<UserItem>> = selectedItems

    fun handleSelectedItem(userId: String){
        userItems.value = userItems.value!!.map {
            if(it.id == userId) it.copy(isSelected = !it.isSelected)
            else it
        }
    }

    fun handleRemoveChip(userId: String) {
        userItems.value = userItems.value!!.map {
            if (it.id == userId) it.copy(isSelected = false)
            else it
        }
    }

    fun handleSearchQuery(text: String?) {
        query.value = text
    }


    fun handleCreatedChat() {
        if(selectedItems.value!!.size > 1){
            mainRepository.createGroupChat(mainRepository.findUsersById(selectedItems.value!!.map { it.id }).toMutableList(), "Test")
        }
        else{
            mainRepository.createChat(mainRepository.findUser(selectedItems.value!!.first().id)!!)
        }
    }

    fun handleCreatedGroupChat(titleOfChat: String){
        mainRepository.createGroupChat(mainRepository.findUsersById(selectedItems.value!!.map { it.id }).toMutableList(), "Test")
    }


}