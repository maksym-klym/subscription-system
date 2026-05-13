package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.AuthUserDto;
import com.maks.subscriptionsystem.dto.CredentialsDto;
import com.maks.subscriptionsystem.dto.LoginResponse;
import com.maks.subscriptionsystem.security.DatabaseUserService;
import com.maks.subscriptionsystem.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final DatabaseUserService databaseUserService;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(CredentialsDto credentialsDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentialsDto.getEmail(),
                credentialsDto.getPassword()
        ));
        UserDetails userDetails = databaseUserService.loadUserByUsername(credentialsDto.getEmail());

        String accessToken = jwtTokenService.generateAccessToken(userDetails);
        AuthUserDto authUserDto = userService.getAuthUser(userDetails.getUsername());
        return new LoginResponse(authUserDto, accessToken);
    }
}
