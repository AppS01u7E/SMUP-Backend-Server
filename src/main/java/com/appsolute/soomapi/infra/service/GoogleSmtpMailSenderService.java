package com.appsolute.soomapi.infra.service;

import com.appsolute.soomapi.infra.exception.MailSendingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GoogleSmtpMailSenderService implements MailSenderService{
    private final JavaMailSender jms;
    private final TemplateEngine templateEngine;

    @Override
    public void sendHtmlEmail(String to, String title, String templatePath, HashMap<String, Objects> models) {
        MimeMessage message = jms.createMimeMessage();
        Context context = new Context();
        models.forEach(context::setVariable);
        String content = templateEngine.process(templatePath, context);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(title);
            helper.setTo(to);
            helper.setText(content, true);
        } catch (MessagingException e) {
            throw new MailSendingException("Google SMTP 를 통해 메일 발송중 오류가 발생하였습니다!", e);
        }

        jms.send(message);
    }
}
