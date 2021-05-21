package com.shkiper.chat.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import androidx.room.Dao
import com.shkiper.chat.model.data.Chat

@Dao
interface ChatDao {

    @Query("SELECT * FROM Chats")
    fun getChats(): Observable<List<Chat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(chat: Chat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllChats(chats: List<Chat>)

}