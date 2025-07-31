package com.authservice;

import com.authservice.adapter.rest.AuthController;
import com.authservice.application.usecase.LoginUserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthFlowTest extends IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldRegisterAndLoginSuccessfully() {
        var email = "test@app.com";
        var password = "pass";

        // Register
        var registerRequest = new AuthController.RegisterRequest(email, password);
        var registerResponse = restTemplate.postForEntity("/api/auth/register", registerRequest, Void.class);
        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Login
        var loginRequest = new AuthController.LoginRequest(email, password);
        var loginResponse = restTemplate.postForEntity("/api/auth/login", loginRequest, LoginUserUseCase.LoginResponse.class);
        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody().accessToken()).isNotBlank();
        assertThat(loginResponse.getBody().refreshToken()).isNotBlank();
    }
}