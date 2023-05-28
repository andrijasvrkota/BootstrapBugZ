package org.bootstrapbugz.api.shared.util;

import static org.springframework.security.web.http.SecurityHeaders.bearerToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bootstrapbugz.api.auth.payload.dto.SignInDTO;
import org.bootstrapbugz.api.auth.payload.request.SignInRequest;
import org.bootstrapbugz.api.shared.constants.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class IntegrationTestUtil {
  private IntegrationTestUtil() {}

  public static HttpHeaders authHeader(String accessToken) {
    final var headers = new HttpHeaders();
    bearerToken(accessToken).accept(headers);
    return headers;
  }

  public static SignInDTO signIn(MockMvc mockMvc, ObjectMapper objectMapper, String username)
      throws Exception {
    final var signInRequest = new SignInRequest(username, "qwerty123");
    final var response =
        mockMvc
            .perform(
                post(Path.AUTH + "/sign-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signInRequest)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    return objectMapper.readValue(response, SignInDTO.class);
  }
}
