package com.maks.subscriptionsystem.dto;

import com.maks.subscriptionsystem.etc.Constants;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateUserDto {
    @Pattern(regexp = Constants.EMAIL_PATTERN, message = Constants.NOT_VALID_EMAIL_MESSAGE)
    private String email;
    @Pattern(regexp = Constants.PASSWORD_PATTERN, message = Constants.NOT_VALID_PASSWORD_MESSAGE)
    private String password;
    private String firstName;
    private String lastName;
}
