package com.appsolute.soomapi.domain.soom.data.entity

import com.appsolute.soomapi.domain.soom.data.type.FileStatus
import com.appsolute.soomapi.domain.soom.data.type.FileType
import java.time.LocalDateTime
import javax.persistence.Embeddable


@Embeddable
class File(
    fileKey: String,
    type: FileType,
    extends: String
) {
    var fileKey: String = fileKey

    var type: FileType = type

    var extends: String = extends

    var fileStatus: FileStatus = FileStatus.ALIVE

    var createdAt: LocalDateTime = LocalDateTime.now()

    fun changeFileStatus(status: FileStatus){
        this.fileStatus = status
    }


}