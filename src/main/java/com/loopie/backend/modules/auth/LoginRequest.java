package com.loopie.backend.modules.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
