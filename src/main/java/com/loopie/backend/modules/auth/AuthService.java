package com.loopie.backend.modules.auth;

import com.loopie.backend.modules.user.User;
import com.loopie.backend.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final java.util.Map<String, User> tokenStore = new java.util.concurrent.ConcurrentHashMap<>();

    public AuthResponse login(LoginRequest request) {
        // Buscar usuario por username o email
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(request.getUsername());
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Verificar contraseña
            if (user.getPassword().equals(request.getPassword())) {
                String token = "fakeToken_" + UUID.randomUUID().toString();
                tokenStore.put(token, user); // Store token

                return AuthResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .rol(user.getRol())
                        .token(token)
                        .build();
            }
        }
        throw new RuntimeException("Credenciales inválidas");
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username ya existe");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email ya existe");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .direccion(request.getDireccion())
                .rol(request.getRol() != null ? request.getRol() : "usuario")
                .build();

        return userRepository.save(user);
    }

    public Optional<User> getUserByToken(String token) {
        return Optional.ofNullable(tokenStore.get(token));
    }
}
