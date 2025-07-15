package com.fiap.cliente_service.gateway.databasejpa;

import com.fiap.cliente_service.domain.Cliente;
import com.fiap.cliente_service.gateway.ClienteGateway;
import com.fiap.cliente_service.gateway.databasejpa.entity.ClienteEntity;
import com.fiap.cliente_service.gateway.databasejpa.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteJpaGateway implements ClienteGateway {
    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public Cliente criar(Cliente cliente) {
        clienteRepository.save(mapToEntity(cliente));
        return cliente;
    }


    private ClienteEntity mapToEntity(Cliente cliente){
        return ClienteEntity.builder()
                .cpf(cliente.getCpf())
                .dataNascimento(cliente.getDataNascimento())
                .endereco(cliente.getEndereco())
                .build();
    }

}
