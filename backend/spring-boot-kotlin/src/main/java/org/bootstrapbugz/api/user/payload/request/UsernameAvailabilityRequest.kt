package org.bootstrapbugz.api.user.payload.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.bootstrapbugz.api.shared.constants.Regex

data class UsernameAvailabilityRequest(
    @NotBlank(message = "{username.required}")
    @field:Pattern(regexp = Regex.USERNAME, message = "{username.invalid}")
    val username: String?
)
