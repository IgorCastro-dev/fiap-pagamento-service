package com.fiap.cliente_service.usecase;

import com.fiap.cliente_service.gateway.ClienteGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletaClienteUseCase {
    @Autowired
    private ClienteGateway clienteGateway;

    public String deletaCLientePorCpf(String cpf){
        return clienteGateway.deletaPorCpf(cpf);
    }
}
