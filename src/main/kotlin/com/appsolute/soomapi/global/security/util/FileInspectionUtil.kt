package com.appsolute.soomapi.global.security.util

import org.springframework.web.multipart.MultipartFile
import java.io.File

interface FileInspectionUtil {


    fun inspcet(file: MultipartFile)


}