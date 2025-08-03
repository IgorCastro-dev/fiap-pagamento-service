package com.fiap.pagamento_service.gateway.databasejpa.repository;

import com.fiap.pagamento_service.gateway.databasejpa.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoEntity, String> {

}
