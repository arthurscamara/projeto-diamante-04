package com.campusgigs.api.exception;

import java.time.LocalDateTime;

public record ErroResponse(
        int status,
        String mensagem,
        LocalDateTime timestamp
) {

    public static ErroResponse of(int status, String mensagem) {
        return new ErroResponse(status, mensagem, LocalDateTime.now());
    }
}