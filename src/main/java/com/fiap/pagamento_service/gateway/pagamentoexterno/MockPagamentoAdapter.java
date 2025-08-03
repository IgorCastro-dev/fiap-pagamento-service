package com.fiap.pagamento_service.gateway.pagamentoexterno;

import com.fiap.pagamento_service.domain.DadosPagamento;
import com.fiap.pagamento_service.domain.Pagamento;
import com.fiap.pagamento_service.domain.PagamentoStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MockPagamentoAdapter implements PagamentoService {

    private final Map<String, Pagamento> pagamentos = new ConcurrentHashMap<>();


    public String solicitarPagamento(DadosPagamento dados) {
        String id = UUID.randomUUID().toString();
        PagamentoStatus statusFinal = dados.getClienteCartao() != null
                && dados.getClienteCartao().endsWith("0")
                ? PagamentoStatus.FALHA
                : PagamentoStatus.PENDENTE;
        pagamentos.put(id, new Pagamento(id,statusFinal, LocalDateTime.now()));
        return id;
    }

    public PagamentoStatus consultarStatus(String pagamentoId) {
        Pagamento pagamento = pagamentos.get(pagamentoId);
        if (pagamento == null) {
            return null;
        }
        LocalDateTime criadoEm = pagamento.getDateTime();
        if (criadoEm.plusSeconds(25).isBefore(LocalDateTime.now()) && pagamento.getStatus() != PagamentoStatus.FALHA) {
            pagamento.setStatus(PagamentoStatus.SUCESSO);
        }
        return pagamento.getStatus();
    }



}