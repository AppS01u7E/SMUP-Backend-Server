package com.appsolulte.smupbackendserver.domain.account.entity


import com.appsolute.soomapi.domain.soom.data.entity.Group
import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType
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