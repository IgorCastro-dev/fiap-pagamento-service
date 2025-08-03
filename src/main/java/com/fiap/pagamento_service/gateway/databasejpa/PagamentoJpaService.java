package com.fiap.pagamento_service.gateway.databasejpa;

import com.fiap.pagamento_service.domain.DadosPagamento;
import com.fiap.pagamento_service.domain.PagamentoStatus;
import com.fiap.pagamento_service.gateway.PagamentoGateway;
import com.fiap.pagamento_service.gateway.pagamentoexterno.PagamentoService;
import com.fiap.pagamento_service.gateway.databasejpa.entity.PagamentoEntity;
import com.fiap.pagamento_service.gateway.databasejpa.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PagamentoJpaService implements PagamentoGateway{
    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public String criarPagamento(DadosPagamento dados) {
        String pagamentoId = pagamentoService.solicitarPagamento(dados);
        PagamentoEntity pagamentoEntity = mapToEntity(dados);
        pagamentoEntity.setId(pagamentoId);
        pagamentoEntity.setStatus(PagamentoStatus.PENDENTE);
        pagamentoRepository.save(pagamentoEntity);
        return pagamentoId;
    }

    public PagamentoStatus obterStatusPagamento(String pagamentoId) {
        PagamentoStatus statusAtual = pagamentoService.consultarStatus(pagamentoId);
        PagamentoEntity pagamentoEntity = pagamentoRepository.findById(pagamentoId).orElse(null);
        if (pagamentoEntity != null && pagamentoEntity.getStatus() != statusAtual) {
            pagamentoEntity.setStatus(statusAtual);
            pagamentoRepository.save(pagamentoEntity);
        }

        return statusAtual;
    }

    private PagamentoEntity mapToEntity(DadosPagamento dados){
        return PagamentoEntity.builder()
                .dataPagamento(LocalDateTime.now())
                .valor(dados.getValor())
                .numeroCartao(dados.getClienteCartao())
                .build();
    }
}
