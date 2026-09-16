CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    papel VARCHAR(20) NOT NULL DEFAULT 'USER' CHECK (papel IN ('ADMIN', 'USER')),
    cep VARCHAR(9),
    cidade VARCHAR(100),
    uf VARCHAR(2),
    criado_em TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE servicos (
    id BIGSERIAL PRIMARY KEY,
    prestador_id BIGINT NOT NULL REFERENCES usuarios(id),
    titulo VARCHAR(150) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(100),
    preco NUMERIC(10,2) NOT NULL,
    situacao VARCHAR(20) NOT NULL DEFAULT 'ATIVO' CHECK (situacao IN ('ATIVO', 'PAUSADO', 'ENCERRADO')),
    criado_em TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE contratacoes (
    id BIGSERIAL PRIMARY KEY,
    servico_id BIGINT NOT NULL REFERENCES servicos(id),
    contratante_id BIGINT NOT NULL REFERENCES usuarios(id),
    situacao VARCHAR(20) NOT NULL DEFAULT 'SOLICITADA' CHECK (situacao IN ('SOLICITADA', 'ACEITA', 'CONCLUIDA', 'CANCELADA')),
    criado_em TIMESTAMP NOT NULL DEFAULT now()
);
