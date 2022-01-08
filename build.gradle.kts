import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
}

group = "com.Appsolulte"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    //spring boot
    implementation("org.springframework.boot:spring-boot-starter-data-redis") //redis
    implementation("org.springframework.boot:spring-boot-starter-validation") //validation
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") //data jpa
    implementation("org.springframework.boot:spring-boot-starter-mail") //mailing
    implementation("org.springframework.boot:spring-boot-starter-security") //security
    implementation("org.springframework.boot:spring-boot-starter-web") //web
    developmentOnly("org.springframework.boot:spring-boot-devtools") //devtool
    //kotlin
    /*
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") //jackson for kotilin
     */
    //driver
    runtimeOnly("mysql:mysql-connector-java") //mysql
    //logging
    implementation("org.apache.logging.log4j:log4j-api:2.17.0")
    //firebase
    implementation("com.google.firebase:firebase-admin:6.8.1")
    //utils
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.google.guava:guava:18.0")
    //jwt token
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    //lombok for java
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
