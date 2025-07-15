package com.fiap.cliente_service.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;


@AllArgsConstructor
@Getter
@Builder
public class Cliente {
    private String cpf;
    private LocalDate dataNascimento;
    private String endereco;
}
