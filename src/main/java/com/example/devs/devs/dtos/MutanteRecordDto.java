package com.example.devs.devs.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MutanteRecordDto(
        @NotBlank String senha,
        @NotBlank String nome,
        @NotBlank String poder,
        @NotNull int idade,
        @NotNull int inimigosDerrotados,
        @NotNull boolean estaNaEscola) {
}