CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    afiliacao VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE espaco (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    localizacao VARCHAR(255),
    capacidade INT,
    recursos_disponiveis VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE atividade (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    tipo INT,
    descricao TEXT,
    data VARCHAR(255),
    horario_inicial VARCHAR(255),
    horario_final VARCHAR(255),
    espaco_id BIGINT,
    usuario_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (espaco_id) REFERENCES espaco(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE evento (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    sigla VARCHAR(255),
    caminho VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE edicao_evento (
    id BIGINT NOT NULL AUTO_INCREMENT,
    ano INT,
    numero INT,
    data_inicial VARCHAR(255),
    data_final VARCHAR(255),
    cidade VARCHAR(255),
    evento_id BIGINT,
    usuario_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (evento_id) REFERENCES evento(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);
