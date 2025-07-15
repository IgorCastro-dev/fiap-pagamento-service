package com.fiap.cliente_service.controller;

import com.fiap.cliente_service.controller.json.ClienteJson;
import com.fiap.cliente_service.domain.Cliente;
import com.fiap.cliente_service.usecase.CriarClienteUsecase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    private CriarClienteUsecase criarClienteUsecase;

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody ClienteJson clienteJson){
        return ResponseEntity.status(HttpStatus.CREATED).body(criarClienteUsecase.salvaCliente(mapToEntity(clienteJson)));
    }

    private Cliente mapToEntity(ClienteJson clienteJson){
        return Cliente.builder()
                .cpf(clienteJson.getCpf())
                .dataNascimento(clienteJson.getDataNascimento())
                .endereco(clienteJson.getEndereco())
                .build();
    }

}
