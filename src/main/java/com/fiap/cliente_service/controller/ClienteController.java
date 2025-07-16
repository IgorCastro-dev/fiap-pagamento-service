package com.fiap.cliente_service.controller;

import com.fiap.cliente_service.controller.json.ClienteJson;
import com.fiap.cliente_service.domain.Cliente;
import com.fiap.cliente_service.usecase.BuscaClientePorCpfUsecase;
import com.fiap.cliente_service.usecase.BuscaTodosClientesUsecase;
import com.fiap.cliente_service.usecase.CriarClienteUsecase;
import com.fiap.cliente_service.usecase.DeletaClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cadastro de Cliente", description = "Um endpoint de cadastro de cliente")
public class ClienteController {
    @Autowired
    private CriarClienteUsecase criarClienteUsecase;
    @Autowired
    private BuscaClientePorCpfUsecase buscaClientePorCpfUsecase;
    @Autowired
    private DeletaClienteUseCase deletaClienteUseCase;
    @Autowired
    private BuscaTodosClientesUsecase buscaTodosClientesUsecase;

    @Operation(description = "Cria um cliente novo")
    @ApiResponse(responseCode = "201",description = "Cliente cadastrado com sucesso")
    @ApiResponse(responseCode = "500",description = "Erro no servidor ao cria o cliente")
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody ClienteJson clienteJson){
        return ResponseEntity.status(HttpStatus.CREATED).body(criarClienteUsecase.salvaCliente(mapToEntity(clienteJson)));
    }

    @Operation(description = "Buscar cliente por cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "400", description = "Não existe cliente com esse cpf"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor ao buscar o cliente")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@Valid @CPF @PathVariable String cpf){
        return ResponseEntity.status(HttpStatus.OK).body(buscaClientePorCpfUsecase.buscarClientePorCpf(cpf));
    }

    @Operation(description = "Deleta cliente por cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente deletado"),
            @ApiResponse(responseCode = "400", description = "Não existe cliente com esse cpf"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor ao deletar o cliente")
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletarPorCpf(@Valid @CPF @PathVariable String cpf){
        return ResponseEntity.status(HttpStatus.OK).body(deletaClienteUseCase.deletaCLientePorCpf(cpf));
    }

    @Operation(description = "Buscar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor ao buscar os clientes")
    })
    @GetMapping
    public ResponseEntity<Page<Cliente>> buscaClientes(@RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(buscaTodosClientesUsecase.listarClientes(pageable));
    }

    private Cliente mapToEntity(ClienteJson clienteJson){
        return Cliente.builder()
                .cpf(clienteJson.getCpf())
                .dataNascimento(clienteJson.getDataNascimento())
                .endereco(clienteJson.getEndereco())
                .build();
    }

}
