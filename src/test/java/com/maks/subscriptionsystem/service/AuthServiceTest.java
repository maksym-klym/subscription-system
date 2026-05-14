package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.AuthUserDto;
import com.maks.subscriptionsystem.dto.CredentialsDto;
import com.maks.subscriptionsystem.dto.LoginResponse;
import com.maks.subscriptionsystem.security.DatabaseUserService;
import com.maks.subscriptionsystem.security.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    DatabaseUserService databaseUserService;
    @Mock
    UserService userService;
    @Mock
    JwtTokenService jwtTokenService;
    @InjectMocks
    AuthService authService;

    @Test
    void shouldLoginSuccessfully() {
        String email = "test@mail.com";
        String password = "pass";

        CredentialsDto credentialsDto = new CredentialsDto(email, password);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);

        AuthUserDto authUserDto = new AuthUserDto();

        when(databaseUserService.loadUserByUsername(email))
                .thenReturn(userDetails);

        when(jwtTokenService.generateAccessToken(userDetails))
                .thenReturn("jwt-token");

        when(userService.getAuthUser(email))
                .thenReturn(authUserDto);

        LoginResponse response = authService.login(credentialsDto);

        assertThat(response.getAccessToken()).isEqualTo("jwt-token");
        assertThat(response.getUser()).isEqualTo(authUserDto);

        ArgumentCaptor<Authentication> authCaptor =
                ArgumentCaptor.forClass(Authentication.class);

        verify(authenticationManager).authenticate(authCaptor.capture());

        verify(databaseUserService).loadUserByUsername(email);
        verify(jwtTokenService).generateAccessToken(userDetails);
        verify(userService).getAuthUser(email);
    }

    @Test
    void shouldThrowExceptionWhenAuthenticationFails() {
        CredentialsDto credentialsDto =
                new CredentialsDto("test@mail.com", "wrong");

        doThrow(new BadCredentialsException("bad credentials"))
                .when(authenticationManager)
                .authenticate(any());

        assertThatThrownBy(() -> authService.login(credentialsDto))
                .isInstanceOf(BadCredentialsException.class);

        verify(databaseUserService, never()).loadUserByUsername(any());
        verify(jwtTokenService, never()).generateAccessToken(any());
        verify(userService, never()).getAuthUser(any());
    }

    @Test
    void shouldThrowWhenUserNotFoundAfterAuthentication() {
        String email = "test@mail.com";

        CredentialsDto credentialsDto =
                new CredentialsDto(email, "pass");

        when(authenticationManager.authenticate(any()))
                .thenReturn(mock(Authentication.class));

        when(databaseUserService.loadUserByUsername(email))
                .thenThrow(new UsernameNotFoundException("not found"));

        assertThatThrownBy(() -> authService.login(credentialsDto))
                .isInstanceOf(UsernameNotFoundException.class);

        verify(jwtTokenService, never()).generateAccessToken(any());
        verify(userService, never()).getAuthUser(any());
    }
}
