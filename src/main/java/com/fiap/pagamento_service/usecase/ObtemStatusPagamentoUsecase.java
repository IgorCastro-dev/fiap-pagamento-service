package com.fiap.pagamento_service.usecase;


import com.fiap.pagamento_service.domain.PagamentoStatus;
import com.fiap.pagamento_service.gateway.PagamentoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ObtemStatusPagamentoUsecase {
    @Autowired
    private PagamentoGateway pagamentoGateway;

    public PagamentoStatus buscaStatus(String pagamentoId){
        return pagamentoGateway.obterStatusPagamento(pagamentoId);
    }
}
