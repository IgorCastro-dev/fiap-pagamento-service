package com.fiap.cliente_service.gateway.databasejpa;

import com.fiap.cliente_service.domain.Cliente;
import com.fiap.cliente_service.gateway.databasejpa.entity.ClienteEntity;
import com.fiap.cliente_service.gateway.databasejpa.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClienteJpaGatewayTest {

    private ClienteRepository clienteRepository;
    private ClienteJpaGateway clienteJpaGateway;

    @BeforeEach
    void setUp() {
        clienteRepository = mock(ClienteRepository.class);
        clienteJpaGateway = new ClienteJpaGateway();
        clienteJpaGateway.clienteRepository = clienteRepository;
    }

    @Test
    void deveCriarCliente() {
        Cliente cliente = Cliente.builder()
                .cpf("12345678900")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .endereco("Rua Teste")
                .build();

        clienteJpaGateway.criar(cliente);

        ArgumentCaptor<ClienteEntity> captor = ArgumentCaptor.forClass(ClienteEntity.class);
        verify(clienteRepository).save(captor.capture());

        ClienteEntity savedEntity = captor.getValue();
        assertEquals(cliente.getCpf(), savedEntity.getCpf());
        assertEquals(cliente.getEndereco(), savedEntity.getEndereco());
        assertEquals(cliente.getDataNascimento(), savedEntity.getDataNascimento());
    }

    @Test
    void deveBuscarClientePorCpf() {
        ClienteEntity entity = ClienteEntity.builder()
                .cpf("12345678900")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .endereco("Rua A")
                .build();

        when(clienteRepository.findByCpf("12345678900"))
                .thenReturn(Optional.of(entity));

        Cliente cliente = clienteJpaGateway.buscaPorCpf("12345678900");

        assertEquals("12345678900", cliente.getCpf());
        assertEquals("Rua A", cliente.getEndereco());
    }

    @Test
    void deveLancarExcecaoSeClienteNaoEncontrado() {
        when(clienteRepository.findByCpf("invalido")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            clienteJpaGateway.buscaPorCpf("invalido");
        });

        assertTrue(thrown.getMessage().contains("Cliente não ENCONTRADO"));
    }

    @Test
    void deveDeletarClientePorCpf() {
        ClienteEntity entity = ClienteEntity.builder()
                .cpf("123")
                .dataNascimento(LocalDate.now())
                .endereco("X")
                .build();

        when(clienteRepository.findByCpf("123")).thenReturn(Optional.of(entity));

        String resultado = clienteJpaGateway.deletaPorCpf("123");

        verify(clienteRepository).deleteByCpf("123");
        assertEquals("Cliente deletado com sucesso", resultado);
    }

    @Test
    void deveListarClientesComPageable() {
        ClienteEntity entity = ClienteEntity.builder()
                .cpf("123")
                .dataNascimento(LocalDate.now())
                .endereco("Rua XPTO")
                .build();

        Page<ClienteEntity> page = new PageImpl<>(List.of(entity));
        when(clienteRepository.findAll((Pageable) any())).thenReturn(page);

        Page<Cliente> result = clienteJpaGateway.listarClientes(PageRequest.of(0, 10));

        assertEquals(1, result.getContent().size());
        assertEquals("123", result.getContent().get(0).getCpf());
    }
}
