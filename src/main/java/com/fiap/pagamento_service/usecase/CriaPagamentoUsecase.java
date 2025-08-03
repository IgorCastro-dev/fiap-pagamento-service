package com.fiap.pagamento_service.usecase;

import com.fiap.pagamento_service.domain.DadosPagamento;
import com.fiap.pagamento_service.gateway.PagamentoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriaPagamentoUsecase {
    @Autowired
    private PagamentoGateway pagamentoGateway;

    public String CriarPagamento(DadosPagamento dados){
        return pagamentoGateway.criarPagamento(dados);
    }
}
