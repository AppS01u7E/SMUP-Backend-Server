package com.appsolulte.smupbackendserver.domain.soom.entity

import javax.persistence.Embeddable
import javax.persistence.Entity



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