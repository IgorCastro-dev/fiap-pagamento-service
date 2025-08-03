package com.fiap.pagamento_service.gateway.pagamentoexterno;

import com.fiap.pagamento_service.domain.DadosPagamento;
import com.fiap.pagamento_service.domain.PagamentoStatus;

public interface PagamentoService {
    String solicitarPagamento(DadosPagamento dados);
    PagamentoStatus consultarStatus(String pagamentoId);
}
