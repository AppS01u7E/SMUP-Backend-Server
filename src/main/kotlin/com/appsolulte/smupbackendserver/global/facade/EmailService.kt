package com.appsolulte.smupbackendserver.global.facade

import com.appsolulte.smupbackendserver.global.configuration.GlobalProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.UnsupportedEncodingException
import java.util.*
import javax.mail.MessagingException

@Service
class EmailService(
    private val globalProperties: GlobalProperties,
    private val mailSender: JavaMailSenderImpl
) {

    private val FROM = globalProperties.email.account
    private val PASSWORD = globalProperties.email.password


    @Throws(MessagingException::class, UnsupportedEncodingException::class)
    fun sendEmail(email: String?, random: String): String? {
        mailSender.username = FROM
        mailSender.password = PASSWORD
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        val properties = Properties()
        properties.setProperty("mail.smtp.auth", "true")
        properties.setProperty("mail.smtp.starttls.enable", "true")
        mailSender.javaMailProperties = properties
        val body = StringBuilder()
        body.append(
            "<body>\n" +
                    "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                    "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                    "    <link href=\"https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap\" rel=\"stylesheet\">\n" +
                    "    \n" +
                    "    <div style=\"\n" +
                    "    align-items: center;\n" +
                    "    width: 700px;\n" +
                    "    font-family: 'Noto Sans KR', sans-serif;\">\n" +
                    "\n" +
                    "    <div style=\"            \n" +
                    "    background-color: #4993FA;\n" +
                    "    width: 1100px;\n" +
                    "    height: 60px;\">\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div style=\"\n" +
                    "    background-color: #eeeeee;\n" +
                    "    width: 1100px;\n" +
                    "    height: 750px;\n" +
                    "    padding: 0px 200px;\n" +
                    "    box-sizing: border-box;\n" +
                    "    padding-top: 80px;\">\n" +
                    "\n" +
                    "    <div style=\"            \n" +
                    "    background-color: white;\n" +
                    "    height: 400px;\n" +
                    "    width: 700px;\n" +
                    "    align-items: center;\n" +
                    "    border-radius: 10px;\n" +
                    "    position: relative;\">\n" +
                    "\n" +
                    "        <img style=\"height: 60px; width: 150px; position: relative; margin-left: 280px; margin-top: 20px;\" src=\"https://sns2ata.s3.ap-northeast-2.amazonaws.com/SERVER_DATA/258342860_906801423594090_4534721152852898421_n.png\"></img>\n" +
                    "        <h1 style=\"margin: 0; position: relative; margin-top: 20px; margin-left: 290px; width: 150px;\">인증번호</h1>\n" +
                    "        <p style=\"position: relative; margin-top: 20px; margin-left: 150px; width: 200px; text-align: center; position: relative; margin-left: 250px;\">고객님의 인증 번호는 다음과 같습니다.</p>\n" +
                    "        <div style=\"margin-top: 50px;\">\n" +
                    "        <strong style=\"        \n" +
                    "        background-color: rgb(249, 249, 249);\n" +
                    "        border: 1px solid rgb(235, 235, 235);\n" +
                    "        padding: 10px 30px;\n" +
                    "        letter-spacing: 5px; color: dimgray;\n" +
                    "        font-size: 30px; position: relative;\n" +
                    "        margin-left: 260px;\n" +
                    "        width: 150px;\">" + random + "" +
                    "        </strong>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div style=\"position: relative; margin-left: 90px; margin-top: 50px\">\n" +
                    "        <p style=\"text-align: center; position: relative; margin-top: 10px; color: gray; width: 500px;\">본인이 시도한 것이 아니라면, 지원팀에 문의해 주세요.</p>\n" +
                    "        <p style=\"text-align: center; position: relative; margin-top: 20px; color: gray; width: 500px;\">© 2021 SNS, All Rights Reserved\n지원팀: dsm.info.appsolute@gmail.com</p>\n" +
                    "        <p style=\"text-align: center; position: relative; margin-top: 30px; color: gray; width: 500px;\">대한민국 대전광역시 유성구 장동 가정북로 76</p>\n" +
                    "    </div>\n" +
                    "    </div>\n" +
                    "    </div> \n" +
                    "</body>\n"
        )
        val message = mailSender.createMimeMessage()
        val mimeMessageHelper = MimeMessageHelper(message, true, "UTF-8")
        mimeMessageHelper.setFrom((FROM)!!, "SMUP_Information")
        mimeMessageHelper.setTo((email)!!)
        mimeMessageHelper.setSubject("인증번호")
        mimeMessageHelper.setText(body.toString(), true)
        mailSender.send(message)
        return random
    }


}