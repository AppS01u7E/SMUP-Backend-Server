package com.appsolute.soomapi.domain.soom.data.entity

import com.appsolute.soomapi.domain.soom.data.type.FileType
import javax.persistence.Embeddable


@Embeddable
class File(
    fileUrl: String,
    type: FileType,
    extends: String
) {
    var fileUrl = fileUrl

    var type = type

    var extends = extends


}