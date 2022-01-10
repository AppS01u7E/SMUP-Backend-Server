package com.appsolute.soomapi.global.domain.chatting.data.entity

import com.appsolute.soomapi.domain.chatting.data.type.ChattingRoomType
import com.appsolute.soomapi.domain.account.data.entity.User
import com.appsolute.soomapi.domain.soom.data.entity.Group
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne


@Entity
class ChattingRoom(
    id: String,
    name: String,
    type: ChattingRoomType

) {
    @Id
    var id = id

    var name = name
    @OneToMany
    var memberList: MutableList<User> = ArrayList<User>()

    var type: ChattingRoomType = type

    @OneToOne
    var group: Group? = null

    fun setMembers(memberList: MutableList<User>): ChattingRoom {
        this.memberList.replaceAll(memberList)
        return this
    }

}

private fun <E> MutableList<E>.replaceAll(memberList: MutableList<E>) {
    this.replaceAll(memberList)

}
