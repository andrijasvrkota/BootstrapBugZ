package org.bootstrapbugz.api.auth.event.email;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.google.common.io.Files;

import org.bootstrapbugz.api.shared.email.service.EmailService;
import org.bootstrapbugz.api.user.model.User;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfirmRegistrationEmail implements JwtEmail {
  @Override
  public void sendEmail(
      EmailService emailService, Environment environment, User user, String token) {
    try {
      File template = new ClassPathResource("templates/email/confirm-registration.html").getFile();
      String body = Files.asCharSource(template, StandardCharsets.UTF_8).read();
      String link = environment.getProperty("ui.app.url") + "/confirm-registration?token=" + token;
      body =
          body.replace("$name", user.getUsername())
              .replace("$link", link)
              .replace("$appName", Objects.requireNonNull(environment.getProperty("app.name")));
      emailService.sendHtmlEmail(user.getEmail(), "Confirm Registration", body);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }
}
