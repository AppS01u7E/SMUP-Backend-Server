package com.appsolute.soomapi.domain.soom.data.entity

import com.appsolute.soomapi.domain.soom.data.type.GroupType
import com.appsolute.soomapi.global.school.data.type.SchoolType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime



@RedisHash(timeToLive = 604800)
class GeneGroupRequest(
    name: String,
    memberId: String,
    groupType: GroupType,
    schoolType: SchoolType,
    description: String
) {
    @Id
    val id = memberId+name
    var name: String = name
    val requestAt = LocalDateTime.now()
    @Indexed
    val type = groupType
    @Indexed
    val school = schoolType
    var des: String = description


}