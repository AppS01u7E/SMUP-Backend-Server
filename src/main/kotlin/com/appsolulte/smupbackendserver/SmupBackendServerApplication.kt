package com.appsolulte.smupbackendserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class SmupBackendServerApplication

fun main(args: Array<String>) {
    runApplication<SmupBackendServerApplication>(*args)
}
