package com.fiap.cliente_service.gateway.databasejpa.repository;

import com.fiap.cliente_service.gateway.databasejpa.entity.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity,String> {
    Optional<ClienteEntity> findByCpf(String cpf);
    void deleteByCpf(String cpf);

    Page<ClienteEntity> findAll(Pageable pageable);
}
