package com.fiap.cliente_service.gateway.databasejpa.repository;

import com.fiap.cliente_service.gateway.databasejpa.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity,String> {
}
