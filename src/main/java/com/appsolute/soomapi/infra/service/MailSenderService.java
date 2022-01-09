package com.appsolute.soomapi.infra.service;

import java.util.HashMap;
import java.util.Objects;

public interface MailSenderService {
    void sendHtmlEmail(String to, String title, String templatePath, HashMap<String, Objects> models);
}
