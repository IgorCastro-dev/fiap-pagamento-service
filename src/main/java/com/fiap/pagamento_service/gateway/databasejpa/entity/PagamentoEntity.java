package com.fiap.pagamento_service.gateway.databasejpa.entity;

import com.fiap.pagamento_service.domain.PagamentoStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "pagamento")
public class PagamentoEntity {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String numeroCartao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PagamentoStatus status;

    @Column(nullable = false)
    private BigDecimal valor;

    private LocalDateTime dataPagamento;
}
