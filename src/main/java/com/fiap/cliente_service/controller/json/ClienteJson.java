package com.fiap.cliente_service.controller.json;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ClienteJson {
    @CPF
    private String cpf;
    @NotBlank
    private LocalDate dataNascimento;
    @NotBlank
    private String endereco;
}
