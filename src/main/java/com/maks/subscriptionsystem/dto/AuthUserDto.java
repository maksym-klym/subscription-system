package com.maks.subscriptionsystem.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthUserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
