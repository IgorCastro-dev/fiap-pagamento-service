CREATE TABLE pagamento (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    numero_cartao VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    valor DECIMAL(19,2) NOT NULL,
    data_pagamento DATETIME
);

