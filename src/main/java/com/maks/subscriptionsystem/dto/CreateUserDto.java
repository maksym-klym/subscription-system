package com.maks.subscriptionsystem.dto;

import com.maks.subscriptionsystem.etc.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Schema(description = "Request of creating user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateUserDto {
    @Schema(description = "User email")
    @Pattern(regexp = Constants.EMAIL_PATTERN, message = Constants.NOT_VALID_EMAIL_MESSAGE)
    private String email;
    @Schema(description = "User password")
    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = Constants.NOT_VALID_PASSWORD_MESSAGE)
    private String password;
    @Schema(description = "User first name")
    private String firstName;
    @Schema(description = "User last name")
    private String lastName;
}
