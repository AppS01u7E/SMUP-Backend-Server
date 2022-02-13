package com.appsolute.soomapi.domain.account.data.entity.user


import com.appsolute.soomapi.domain.soom.data.entity.Soom
import com.appsolute.soomapi.domain.soom.data.type.GroupAuthType
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class GroupInfo(
    user: User,
    soom: Soom,
    auth: MutableList<GroupAuthType>
) {
    @Id
    var id = user.uuid + soom.id + "groupInfo"
    @ManyToOne
    var user = user
    @OneToOne
    var group = soom

    var joinedAt = LocalDateTime.now()

    @ElementCollection
    var auth: MutableList<GroupAuthType> = auth

    fun addAuth(groupAuthType: GroupAuthType){
        if (!this.auth.contains(groupAuthType)) {
            this.auth.add(groupAuthType)
        }
    }

    fun removeAuth(groupAuthType: GroupAuthType) {
        if (this.auth.contains(groupAuthType)){
            this.auth.remove(groupAuthType)
        }
    }

    fun changeAuth(groupInfo: GroupInfo) {
        val auth = this.auth
        this.auth = groupInfo.auth
        groupInfo.auth = auth
    }

}