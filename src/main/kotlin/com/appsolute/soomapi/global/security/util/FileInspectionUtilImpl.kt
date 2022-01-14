package com.appsolute.soomapi.global.security.util

import com.appsolute.soomapi.global.security.util.type.AllowedExtendType
import org.springframework.web.multipart.MultipartFile
import java.io.File

class FileInspectionUtilImpl: FileInspectionUtil {


    override fun inspcet(file: MultipartFile) {

        if (checkExtend(file.originalFilename!!))




    }

    private fun checkExtend(fileName: String): Boolean{
        val extend: String = fileName.substring(fileName.indexOf(".") + 1, fileName.length)

        if ()

    }

}