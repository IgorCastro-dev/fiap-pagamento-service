package com.fiap.cliente_service.gateway;

import com.fiap.cliente_service.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClienteGateway {

    Cliente criar(Cliente cliente);
    Cliente buscaPorCpf(String cpf);
    String deletaPorCpf(String cpf);
    Page<Cliente> listarClientes(Pageable pageable);
}
