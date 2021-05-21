package com.shkiper.chat.model.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shkiper.chat.extensions.humanizeDiff
import com.shkiper.chat.util.Utils
import java.util.*

@Entity(tableName = "Users")
data class User (
    @PrimaryKey
    var id:String = "",
    var firstName:String = "",
    var lastName:String = "",
    var avatar:String? = null,
    var lastVisit: Date? = null,
    var isOnline:Boolean = false,
    var email: String = ""
) {


    fun toUserItem(): UserItem {
        val lastActivity = when{
            lastVisit == null -> "Еще ниразу не заходил"
            isOnline -> "online"
            else -> "Последний раз был ${lastVisit!!.humanizeDiff()}"
        }

        return UserItem(
            id,
            "$firstName $lastName",
            Utils.toInitials(firstName, lastName),
            avatar,
            lastActivity,
            false,
            isOnline
        )
    }


}