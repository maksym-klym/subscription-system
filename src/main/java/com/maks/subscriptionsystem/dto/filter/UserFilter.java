package com.maks.subscriptionsystem.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "User filter DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserFilter {
    @Schema(description = "User email", required = false, defaultValue = " ")
    private String email;
    @Schema(description = "User first name", required = false, defaultValue = " ")
    private String firstName;
    @Schema(description = "User last name", required = false, defaultValue = " ")
    private String lastName;
}
