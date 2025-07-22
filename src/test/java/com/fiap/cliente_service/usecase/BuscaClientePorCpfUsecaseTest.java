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

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class BuscaClientePorCpfUsecaseTest {

    private Cliente cliente;

    @BeforeEach
    public void setup(){
        LocalDate date = LocalDate.parse("1999-12-09");
        Cliente cliente =
                new Cliente("155.630.387-42",
                        date,
                        "Av das Flores");
        this.cliente = cliente;
    }
    @InjectMocks
    private BuscaClientePorCpfUsecase buscaClientePorCpfUsecase;

    @Mock
    private ClienteGateway clienteGateway;

    @Test
    void give_sucess_when_cpfIsPresent_then_returnCliente() {
        String cpf = "155.630.387-42";
        Mockito.when(clienteGateway.buscaPorCpf(cpf)).thenReturn(cliente);
        Assertions.assertEquals(cliente, buscaClientePorCpfUsecase.buscarClientePorCpf(cpf));
        Mockito.verify(clienteGateway, Mockito.times(1)).buscaPorCpf(cpf);
    }
}
