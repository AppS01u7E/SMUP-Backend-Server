package com.appsolute.soomapi.infra.service.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.appsolute.soomapi.infra.env.configuration.AmazonS3Config
import com.appsolute.soomapi.infra.env.property.S3Property
import com.appsolute.soomapi.infra.exception.FileConvertFailExceptoin
import org.apache.juli.logging.LogFactory
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*

class S3UtilImpl(
    private val amazonS3Client: AmazonS3Client,
    private val s3Property: S3Property
): S3Util {

    private val log = LogFactory.getLog(javaClass)

    override fun upload(input: MultipartFile, dirName: String): String{
        val uploadFile = convert(input)
        return upload(uploadFile, dirName)
    }

    override fun getUrl(fileKey: String): String{
        val expiration = Date(Date().time + s3Property.expired)

        val urlRequest: GeneratePresignedUrlRequest = GeneratePresignedUrlRequest(s3Property.bucket, fileKey).withMethod(HttpMethod.GET).withExpiration(expiration)
        val url = amazonS3Client.generatePresignedUrl(urlRequest)

        return url.toString()
    }


    private fun upload(file: File, dirName: String): String {
        val fileName = dirName + "/" + UUID.randomUUID().toString() + file.name
        val fileKey = putS3(file, fileName)
        removeUploadedFile(file)
        return fileKey
    }


    private fun putS3(file: File, fileName: String): String{
        amazonS3Client.putObject(PutObjectRequest(s3Property.bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead))
        return fileName
    }


    private fun removeUploadedFile(target: File){
        if (target.delete()){
            log.info("${target.name} delete success")
            return;
        }
        log.warn("${target.name} delete Failed")
    }


    private fun convert(file: MultipartFile): File {
        val convertFile = File(System.getProperty("user.dir") + "/" + file.originalFilename)

        if (convertFile.createNewFile()){
            val fos = FileOutputStream(convertFile)

            fos.write(file.bytes)

            return convertFile
        }
        else throw FileConvertFailExceptoin()
    }

}