package com.fiap.cliente_service.usecase;

import com.fiap.cliente_service.domain.Cliente;
import com.fiap.cliente_service.gateway.ClienteGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class BuscaTodosClientesUsecase {
    @Autowired
    private ClienteGateway clienteGateway;

    public Page<Cliente> listarClientes(Pageable pageable){
        return clienteGateway.listarClientes(pageable);
    }
}
