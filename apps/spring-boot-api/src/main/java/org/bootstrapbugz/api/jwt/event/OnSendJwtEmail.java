package org.bootstrapbugz.api.jwt.event;

import java.io.Serial;

import org.bootstrapbugz.api.jwt.util.JwtPurpose;
import org.bootstrapbugz.api.user.model.User;
import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class OnSendJwtEmail extends ApplicationEvent {
  @Serial private static final long serialVersionUID = 6234594744610595282L;
  private final User user;
  private final String token;
  private final JwtPurpose purpose;

  public OnSendJwtEmail(User user, String token, JwtPurpose purpose) {
    super(user);
    this.user = user;
    this.token = token;
    this.purpose = purpose;
  }
}
