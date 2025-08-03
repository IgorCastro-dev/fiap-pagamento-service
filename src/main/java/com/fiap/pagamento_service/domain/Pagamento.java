package com.fiap.pagamento_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    private String id;
    private PagamentoStatus status;
    private LocalDateTime dateTime;
}