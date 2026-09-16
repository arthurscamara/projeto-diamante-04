package com.campusgigs.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CriarServicoRequest(
        @NotBlank(message = "Título é obrigatório")
        String titulo,

        String descricao,

        String categoria,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        BigDecimal preco
) {
}