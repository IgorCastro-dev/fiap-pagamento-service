package com.fiap.pagamento_service.controller;

import com.fiap.pagamento_service.controller.json.DadosPagamentoJson;
import com.fiap.pagamento_service.domain.DadosPagamento;
import com.fiap.pagamento_service.domain.PagamentoStatus;
import com.fiap.pagamento_service.usecase.CriaPagamentoUsecase;
import com.fiap.pagamento_service.usecase.ObtemStatusPagamentoUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private CriaPagamentoUsecase criaPagamentoUsecase;

    @Autowired
    private ObtemStatusPagamentoUsecase obtemStatusPagamentoUsecase;

    @Operation(description = "Cria um novo pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor ao criar o pagamento")
    })
    @PostMapping
    public ResponseEntity<String> criarPagamento(@Valid @RequestBody DadosPagamentoJson dadosPagamentoJson) {
        DadosPagamento dadosPagamento = mapToEntity(dadosPagamentoJson);
        String pagamentoId = criaPagamentoUsecase.CriarPagamento(dadosPagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoId);
    }

    @Operation(description = "Obtém o status de um pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do pagamento retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor ao obter status do pagamento")
    })
    @GetMapping("/status/{pagamentoId}")
    public ResponseEntity<PagamentoStatus> obterStatusPagamento(@PathVariable String pagamentoId) {
        PagamentoStatus status = obtemStatusPagamentoUsecase.buscaStatus(pagamentoId);
        return ResponseEntity.ok(status);
    }


    private DadosPagamento mapToEntity(DadosPagamentoJson json) {
        return new DadosPagamento(
                json.getValor(),
                json.getClienteCpf(),
                json.getClienteCartao()
        );
    }
}

