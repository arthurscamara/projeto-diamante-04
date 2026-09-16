package com.campusgigs.api.service;

import com.campusgigs.api.dto.LoginRequest;
import com.campusgigs.api.dto.LoginResponse;
import com.campusgigs.api.model.Usuario;
import com.campusgigs.api.repository.UsuarioRepository;
import com.campusgigs.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse autenticar(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        String token = jwtService.gerarToken(usuario);
        return LoginResponse.of(token);
    }
}