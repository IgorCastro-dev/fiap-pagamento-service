package com.fiap.cliente_service.usecase;

import com.fiap.cliente_service.domain.Cliente;
import com.fiap.cliente_service.gateway.ClienteGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscaClientePorCpfUsecase {
    @Autowired
    private ClienteGateway clienteGateway;

    public Cliente buscarClientePorCpf(String cpf){
        return clienteGateway.buscaPorCpf(cpf);
    }
}
