package com.fiap.pagamento_service.controller.json;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DadosPagamentoJson {
    private BigDecimal valor;
    private String clienteCpf;
    private String clienteCartao;
}
