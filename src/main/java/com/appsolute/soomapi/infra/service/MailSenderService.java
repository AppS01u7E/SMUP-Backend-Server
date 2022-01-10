package com.appsolute.soomapi.infra.service;

import java.util.Map;

public interface MailSenderService {
    void sendHtmlEmail(String to, String title, String templatePath, Map<String, Object> models);
}
