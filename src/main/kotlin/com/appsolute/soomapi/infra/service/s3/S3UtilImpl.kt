package com.appsolute.soomapi.infra.service.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.appsolute.soomapi.global.security.CurrentUser
import com.appsolute.soomapi.infra.env.property.S3Property
import com.appsolute.soomapi.infra.exception.FileConvertFailException
import com.appsolute.soomapi.infra.exception.FileSecurityDetectedException
import com.appsolute.soomapi.infra.service.MailSenderService
import org.apache.juli.logging.LogFactory
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.sax.BodyContentHandler
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


@Service
class S3UtilImpl(
    private val s3Property: S3Property,
    private val current: CurrentUser,
    private val email: MailSenderService
): S3Util {
    private val amazonS3Client = AmazonS3Client()
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

    private fun scanFile(file: File){
        val handler: BodyContentHandler = BodyContentHandler()
        val parser: AutoDetectParser = AutoDetectParser()
        val metadata: Metadata = Metadata()

        try {
            parser.parse(FileInputStream(file), handler, metadata)
        } catch (e: IOException){
            throw FileConvertFailException(file.name)
        } catch (e: Exception){
            log.error("threat has detected on scanning file!!\n" +
                    " Uploader: ${current.getUser().uuid} / ${current.getUser().getLastName()}")

            file.delete()
            email.sendHtmlEmail(s3Property.managerEmail, "보안 위협 감지", null, null)
            throw FileSecurityDetectedException(file.name)
        }
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
        target.deleteOnExit()
        log.warn("${target.name} delete Failed")
    }


    private fun convert(file: MultipartFile): File {
        val convertFile = File(System.getProperty("user.dir") + "/" + file.originalFilename)

        if (convertFile.createNewFile()){
            val fos = FileOutputStream(convertFile)

            fos.write(file.bytes)

            return convertFile
        }
        else throw FileConvertFailException(file.name)
    }

}