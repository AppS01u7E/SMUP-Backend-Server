package com.appsolute.soomapi.infra.env.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.appsolute.soomapi.infra.env.property.S3Property
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AmazonS3Config(
    private val s3Property: S3Property
) {

    @Bean
    fun amazonS3Client(): AmazonS3 {
        val awsCreds: BasicAWSCredentials = BasicAWSCredentials(s3Property.accessKey, s3Property.secretKey)
        return AmazonS3ClientBuilder.standard()
            .withRegion(s3Property.region)
            .withCredentials(AWSStaticCredentialsProvider(awsCreds))
            .build()
    }



}