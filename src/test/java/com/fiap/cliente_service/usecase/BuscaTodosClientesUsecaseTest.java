package com.fiap.cliente_service.usecase;

import com.fiap.cliente_service.domain.Cliente;
import com.fiap.cliente_service.gateway.ClienteGateway;
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
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BuscaTodosClientesUsecaseTest {

    private Page<Cliente> page;

    @BeforeEach
    public void setup(){
        LocalDate date = LocalDate.parse("1999-12-09");
        Cliente cliente1 =
                new Cliente("155.630.387-41",
                        date,
                        "Av das Flores");

        Cliente cliente2 =
                new Cliente("155.630.387-42",
                        date,
                        "Av das Flores");

        List<Cliente> clientes = Arrays.asList(cliente1,cliente2);
        Pageable pageable = PageRequest.of(1,1);
        this.page = new PageImpl<>(clientes, pageable, clientes.size());
    }
    @InjectMocks
    private BuscaTodosClientesUsecase buscaTodosClientesUsecase;

    @Mock
    private ClienteGateway clienteGateway;

    @Test
    void give_sucess_when_AllIsOk_then_returnPage() {
        Pageable pageable = PageRequest.of(1,1);
        Mockito.when(clienteGateway.listarClientes(pageable)).thenReturn(page);
        Assertions.assertEquals(page, buscaTodosClientesUsecase.listarClientes(pageable));
        Mockito.verify(clienteGateway, Mockito.times(1)).listarClientes(pageable);
    }
}
