package com.fiap.pagamento_service.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;


@AllArgsConstructor
@Getter
public enum PagamentoStatus {
    PENDENTE,
    SUCESSO,
    FALHA
}
