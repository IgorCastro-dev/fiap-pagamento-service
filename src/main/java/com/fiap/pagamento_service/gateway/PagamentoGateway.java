package com.fiap.pagamento_service.gateway;

import com.fiap.pagamento_service.domain.DadosPagamento;
import com.fiap.pagamento_service.domain.PagamentoStatus;


public interface PagamentoGateway {
    String criarPagamento(DadosPagamento dados);
    PagamentoStatus obterStatusPagamento(String pagamentoId);

}
