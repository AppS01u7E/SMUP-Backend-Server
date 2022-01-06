package com.appsolulte.smupbackendserver.domain.account.entity

import com.appsolulte.smupbackendserver.domain.soom.dto.response.GroupAuthType
import com.appsolulte.smupbackendserver.domain.soom.entity.Group
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class GroupInfo(
    user: User,
    group: Group,
    auth: GroupAuthType
) {
    @Id
    var id = UUID.randomUUID().toString()
    @ManyToOne
    var user = user
    @OneToOne
    var group = group

    var joinedAt = LocalDateTime.now()

    var auth = auth


    fun changeAuth(auth: GroupAuthType): GroupInfo{
        this.auth = auth
        return this
    }

    fun removeAuth(): GroupInfo{
        this.auth = GroupAuthType.MEMBER
        return this
    }


}