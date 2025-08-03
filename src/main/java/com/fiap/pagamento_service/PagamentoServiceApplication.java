package com.fiap.pagamento_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gerencia Cliente",version = "V1",description = "Uma Api de gerenciamento de cliente"))
public class PagamentoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagamentoServiceApplication.class, args);
	}

}
