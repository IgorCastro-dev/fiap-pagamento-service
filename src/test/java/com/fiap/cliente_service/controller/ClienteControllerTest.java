package com.fiap.cliente_service.controller;

import com.fiap.cliente_service.controller.json.ClienteJson;
import com.fiap.cliente_service.domain.Cliente;
import com.fiap.cliente_service.usecase.BuscaClientePorCpfUsecase;
import com.fiap.cliente_service.usecase.BuscaTodosClientesUsecase;
import com.fiap.cliente_service.usecase.CriarClienteUsecase;
import com.fiap.cliente_service.usecase.DeletaClienteUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private CriarClienteUsecase criarClienteUsecase;

    @Mock
    private BuscaClientePorCpfUsecase buscaClientePorCpfUsecase;

    @Mock
    private DeletaClienteUseCase deletaClienteUseCase;

    @Mock
    private BuscaTodosClientesUsecase buscaTodosClientesUsecase;

    private Cliente cliente;
    private ClienteJson clienteJson;
    private Page<Cliente> page;

    @BeforeEach
    void setup() {
        clienteJson = new ClienteJson();
        clienteJson.setCpf("12345678901");
        clienteJson.setEndereco("Rua Teste");
        clienteJson.setDataNascimento(LocalDate.of(1990, 1, 1));

        cliente = Cliente.builder()
                .cpf(clienteJson.getCpf())
                .endereco(clienteJson.getEndereco())
                .dataNascimento(clienteJson.getDataNascimento())
                .build();

        page = new PageImpl<>(List.of(cliente), PageRequest.of(0, 1), 1);
    }

    @Test
    void givenValidClienteJson_whenCriarCliente_thenReturnCreatedCliente() {
        Mockito.when(criarClienteUsecase.salvaCliente(Mockito.any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.criarCliente(clienteJson);

        Assertions.assertEquals(201, response.getStatusCodeValue());
        Assertions.assertEquals(cliente.getCpf(), response.getBody().getCpf());
        Mockito.verify(criarClienteUsecase, Mockito.times(1)).salvaCliente(Mockito.any(Cliente.class));
    }

    @Test
    void givenCpf_whenBuscarPorCpf_thenReturnCliente() {
        Mockito.when(buscaClientePorCpfUsecase.buscarClientePorCpf("12345678901")).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.buscarPorCpf("12345678901");

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(cliente.getCpf(), response.getBody().getCpf());
        Mockito.verify(buscaClientePorCpfUsecase, Mockito.times(1)).buscarClientePorCpf("12345678901");
    }

    @Test
    void givenCpf_whenDeletarPorCpf_thenReturnSuccessMessage() {
        Mockito.when(deletaClienteUseCase.deletaCLientePorCpf("12345678901")).thenReturn("Cliente deletado com sucesso");

        ResponseEntity<String> response = clienteController.deletarPorCpf("12345678901");

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals("Cliente deletado com sucesso", response.getBody());
        Mockito.verify(deletaClienteUseCase, Mockito.times(1)).deletaCLientePorCpf("12345678901");
    }

    @Test
    void givenPageAndSize_whenBuscaClientes_thenReturnPage() {
        Mockito.when(buscaTodosClientesUsecase.listarClientes(Mockito.any()))
                .thenReturn(page);

        ResponseEntity<Page<Cliente>> response = clienteController.buscaClientes(0, 1);

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(page, response.getBody());
        Mockito.verify(buscaTodosClientesUsecase, Mockito.times(1)).listarClientes(Mockito.any());
    }
}
