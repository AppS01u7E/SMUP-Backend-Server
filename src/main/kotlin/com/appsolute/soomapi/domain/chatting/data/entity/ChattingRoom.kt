package com.appsolute.soomapi.domain.chatting.data.entity

import com.appsolute.soomapi.domain.chatting.data.type.ChattingRoomType
import com.appsolute.soomapi.domain.account.data.entity.user.User
import com.appsolute.soomapi.domain.soom.data.entity.Group
import javax.persistence.*


@Entity
class ChattingRoom(
    id: String,
    name: String,
    type: ChattingRoomType

) {
    @Id
    var id = id

    var name = name
    @OneToMany(fetch = FetchType.LAZY)
    var memberList: MutableList<User> = ArrayList<User>()

    var type: ChattingRoomType = type

    @OneToOne(fetch = FetchType.LAZY)
    var group: Group? = null

    fun setMembers(memberList: MutableList<User>): ChattingRoom {
        this.memberList.replaceAll(memberList)
        return this
    }

}

private fun <E> MutableList<E>.replaceAll(memberList: MutableList<E>) {
    this.replaceAll(memberList)

}
