package com.campusgigs.api.service;

import com.campusgigs.api.dto.CadastroUsuarioRequest;
import com.campusgigs.api.dto.UsuarioResponse;
import com.campusgigs.api.model.Papel;
import com.campusgigs.api.model.Usuario;
import com.campusgigs.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse cadastrar(CadastroUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse email");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .papel(Papel.USER)
                .cep(request.cep())
                .build();

        Usuario salvo = usuarioRepository.save(usuario);

        return UsuarioResponse.from(salvo);
    }

    public UsuarioResponse buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return UsuarioResponse.from(usuario);
    }
}