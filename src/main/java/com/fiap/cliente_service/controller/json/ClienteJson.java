package com.fiap.cliente_service.controller.json;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ClienteJson {
    @CPF
    private String cpf;
    @NotNull
    @Past(message = "A data de nascimento deve ser no passado.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @NotBlank
    private String endereco;
}
