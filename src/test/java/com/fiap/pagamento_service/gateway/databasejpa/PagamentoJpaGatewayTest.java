package com.fiap.cliente_service.gateway.databasejpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fiap.pagamento_service.domain.DadosPagamento;
import com.fiap.pagamento_service.domain.PagamentoStatus;
import com.fiap.pagamento_service.gateway.databasejpa.PagamentoJpaService;
import com.fiap.pagamento_service.gateway.databasejpa.entity.PagamentoEntity;
import com.fiap.pagamento_service.gateway.databasejpa.repository.PagamentoRepository;
import com.fiap.pagamento_service.gateway.pagamentoexterno.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PagamentoJpaServiceTest {

    @Mock
    private PagamentoService pagamentoService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoJpaService pagamentoJpaService;

    private DadosPagamento dadosPagamento;
    private PagamentoEntity pagamentoEntity;

    @BeforeEach
    void setUp() {
        dadosPagamento = new DadosPagamento();
        dadosPagamento.setValor(BigDecimal.valueOf(100.0));
        dadosPagamento.setClienteCartao("1234567890123456");

        pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId("PAG123");
        pagamentoEntity.setDataPagamento(LocalDateTime.now());
        pagamentoEntity.setValor(BigDecimal.valueOf(100.0));
        pagamentoEntity.setNumeroCartao("1234567890123456");
        pagamentoEntity.setStatus(PagamentoStatus.PENDENTE);
    }

    @Test
    void criarPagamento_deveRetornarIdPagamentoEChamarServicosCorretamente() {
        when(pagamentoService.solicitarPagamento(dadosPagamento)).thenReturn("PAG123");
        when(pagamentoRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntity);

        String resultado = pagamentoJpaService.criarPagamento(dadosPagamento);

        assertEquals("PAG123", resultado);
        verify(pagamentoService, times(1)).solicitarPagamento(dadosPagamento);
        verify(pagamentoRepository, times(1)).save(any(PagamentoEntity.class));
    }

    @Test
    void obterStatusPagamento_deveRetornarStatusAtualQuandoNaoHaMudanca() {
        when(pagamentoService.consultarStatus("PAG123")).thenReturn(PagamentoStatus.PENDENTE);
        when(pagamentoRepository.findById("PAG123")).thenReturn(java.util.Optional.of(pagamentoEntity));

        PagamentoStatus resultado = pagamentoJpaService.obterStatusPagamento("PAG123");

        assertEquals(PagamentoStatus.PENDENTE, resultado);
        verify(pagamentoService, times(1)).consultarStatus("PAG123");
        verify(pagamentoRepository, times(1)).findById("PAG123");
        verify(pagamentoRepository, never()).save(any());
    }

    @Test
    void obterStatusPagamento_deveAtualizarStatusQuandoMudar() {
        when(pagamentoService.consultarStatus("PAG123")).thenReturn(PagamentoStatus.SUCESSO);
        when(pagamentoRepository.findById("PAG123")).thenReturn(java.util.Optional.of(pagamentoEntity));
        when(pagamentoRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntity);

        PagamentoStatus resultado = pagamentoJpaService.obterStatusPagamento("PAG123");

        assertEquals(PagamentoStatus.SUCESSO, resultado);
        verify(pagamentoService, times(1)).consultarStatus("PAG123");
        verify(pagamentoRepository, times(1)).findById("PAG123");
        verify(pagamentoRepository, times(1)).save(pagamentoEntity);
    }

    @Test
    void obterStatusPagamento_deveRetornarStatusAtualQuandoEntidadeNaoExiste() {
        // Arrange
        when(pagamentoService.consultarStatus("PAG123")).thenReturn(PagamentoStatus.SUCESSO);
        when(pagamentoRepository.findById("PAG123")).thenReturn(java.util.Optional.empty());

        // Act
        PagamentoStatus resultado = pagamentoJpaService.obterStatusPagamento("PAG123");

        // Assert
        assertEquals(PagamentoStatus.SUCESSO, resultado);
        verify(pagamentoService, times(1)).consultarStatus("PAG123");
        verify(pagamentoRepository, times(1)).findById("PAG123");
        verify(pagamentoRepository, never()).save(any());
    }

    @Test
    void mapToEntity_deveMapearDadosPagamentoParaEntityCorretamente() {
        PagamentoEntity resultado = pagamentoJpaService.mapToEntity(dadosPagamento);

        assertNotNull(resultado.getDataPagamento());
        assertEquals(dadosPagamento.getValor(), resultado.getValor());
        assertEquals(dadosPagamento.getClienteCartao(), resultado.getNumeroCartao());
        assertNull(resultado.getId());
        assertNull(resultado.getStatus());
    }
}