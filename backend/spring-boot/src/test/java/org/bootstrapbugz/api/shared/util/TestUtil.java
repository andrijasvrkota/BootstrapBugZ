package org.bootstrapbugz.api.shared.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.bootstrapbugz.api.auth.payload.dto.SignInDTO;
import org.bootstrapbugz.api.auth.payload.request.SignInRequest;
import org.bootstrapbugz.api.auth.security.user.details.UserPrincipal;
import org.bootstrapbugz.api.shared.constants.Path;
import org.bootstrapbugz.api.shared.error.ErrorMessage;
import org.bootstrapbugz.api.user.model.Role;
import org.bootstrapbugz.api.user.model.Role.RoleName;
import org.bootstrapbugz.api.user.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public class TestUtil {
  private TestUtil() {}

  public static void checkErrorMessages(ErrorMessage expectedResponse, ResultActions resultActions)
      throws Exception {
    final var actualResponse =
        new JSONObject(resultActions.andReturn().getResponse().getContentAsString());
    assertThat(actualResponse.getInt("status")).isEqualTo(expectedResponse.getStatus());
    assertThat(actualResponse.getString("error")).isEqualTo(expectedResponse.getError());
    assertThat(actualResponse.getJSONArray("details"))
        .usingRecursiveComparison()
        .ignoringCollectionOrder()
        .isEqualTo(new JSONArray(expectedResponse.getDetails().toString()));
  }

  public static SignInDTO signIn(
      MockMvc mockMvc, ObjectMapper objectMapper, SignInRequest signInRequest) throws Exception {
    final var resultActions =
        mockMvc
            .perform(
                post(Path.AUTH + "/sign-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signInRequest)))
            .andExpect(status().isOk());
    return objectMapper.readValue(
        resultActions.andReturn().getResponse().getContentAsString(), SignInDTO.class);
  }

  public static void setAuth(Authentication auth, SecurityContext securityContext, User user) {
    when(securityContext.getAuthentication()).thenReturn(auth);
    SecurityContextHolder.setContext(securityContext);
    when(auth.getPrincipal()).thenReturn(UserPrincipal.create(user));
  }

  public static User getAdminUser() {
    return new User()
        .setId(1L)
        .setFirstName("Admin")
        .setLastName("Admin")
        .setUsername("admin")
        .setEmail("admin@localhost")
        .setActivated(true)
        .setRoles(Set.of(new Role(RoleName.USER), new Role(RoleName.ADMIN)));
  }

  public static User getTestUser() {
    return new User()
        .setId(2L)
        .setFirstName("Test")
        .setLastName("Test")
        .setUsername("test")
        .setEmail("test@localhost")
        .setActivated(true)
        .setRoles(Set.of(new Role(RoleName.USER)));
  }
}
