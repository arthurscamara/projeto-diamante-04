package com.campusgigs.api.dto;

import com.campusgigs.api.model.Papel;
import com.campusgigs.api.model.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Papel papel,
        String cep,
        String cidade,
        String uf,
        LocalDateTime criadoEm
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapel(),
                usuario.getCep(),
                usuario.getCidade(),
                usuario.getUf(),
                usuario.getCriadoEm()
        );
    }
}