package com.fiap.cliente_service.gateway.databasejpa;

import com.fiap.cliente_service.domain.Cliente;
import com.fiap.cliente_service.gateway.ClienteGateway;
import com.fiap.cliente_service.gateway.databasejpa.entity.ClienteEntity;
import com.fiap.cliente_service.gateway.databasejpa.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ClienteJpaGateway implements ClienteGateway {
    @Autowired
    ClienteRepository clienteRepository;
    @Override
    public Cliente criar(Cliente cliente) {
        clienteRepository.save(mapToEntity(cliente));
        return cliente;
    }

    @Override
    public Cliente buscaPorCpf(String cpf) {
        ClienteEntity clienteEntity = clienteRepository.findByCpf(cpf)
                .orElseThrow(
                () -> new RuntimeException("Cliente não ENCONTRADO POR ESSE CPF")
        );
        return mapToDto(clienteEntity);
    }

    @Override
    public String deletaPorCpf(String cpf) {
        buscaPorCpf(cpf);
        clienteRepository.deleteByCpf(cpf);
        return "Cliente deletado com sucesso";
    }
    public Page<Cliente> listarClientes(Pageable pageable) {
        return mapToDTOS(clienteRepository.findAll(pageable));
    }


    private ClienteEntity mapToEntity(Cliente cliente){
        return ClienteEntity.builder()
                .cpf(cliente.getCpf())
                .dataNascimento(cliente.getDataNascimento())
                .endereco(cliente.getEndereco())
                .build();
    }

    private Cliente mapToDto(ClienteEntity clienteEntity){
        return Cliente.builder()
                .cpf(clienteEntity.getCpf())
                .dataNascimento(clienteEntity.getDataNascimento())
                .endereco(clienteEntity.getEndereco())
                .build();
    }

    private Page<Cliente> mapToDTOS(Page<ClienteEntity> clienteEntities){
        return clienteEntities.map(this::mapToDto);
    }

}
