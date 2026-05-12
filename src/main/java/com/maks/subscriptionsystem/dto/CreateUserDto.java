package com.maks.subscriptionsystem.dto;

import com.maks.subscriptionsystem.etc.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Schema(
        description = "Request of creating user",
        example = """
            {
                "email": "",
                "password": "",
                "firstName": "",
                "lastName": ""
            }
            """
        )
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateUserDto {
    @Schema(description = "User email")
    @NotBlank(message = "Email is required")
    @Email(message = Constants.NOT_VALID_EMAIL_MESSAGE)
    private String email;
    @Schema(description = "User password")
    @NotBlank(message = "Password is required")
    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = Constants.NOT_VALID_PASSWORD_MESSAGE)
    private String password;
    @Schema(description = "User first name")
    @NotBlank(message = "First name is required")
    @Pattern(regexp = Constants.NAME_PATTERN, message = Constants.NOT_VALID_NAME_MESSAGE)
    private String firstName;
    @Schema(description = "User last name")
    @NotBlank(message = "Last name is required")
    @Pattern(regexp = Constants.NAME_PATTERN, message = Constants.NOT_VALID_NAME_MESSAGE)
    private String lastName;
}
