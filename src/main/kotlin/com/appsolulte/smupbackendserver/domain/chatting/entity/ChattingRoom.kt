package com.appsolulte.smupbackendserver.domain.chatting.entity

import com.appsolulte.smupbackendserver.domain.account.entity.User
import com.appsolulte.smupbackendserver.domain.soom.entity.Group
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

    fun setMembers(memberList: MutableList<User>): ChattingRoom{
        this.memberList.replaceAll(memberList)
        return this
    }

}

private fun <E> MutableList<E>.replaceAll(memberList: MutableList<E>) {
    this.replaceAll(memberList)

}
