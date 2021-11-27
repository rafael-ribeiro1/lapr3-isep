
-- ** criação de tabelas **
CREATE TABLE Cliente (
            id_cliente INTEGER CONSTRAINT pkIdCliente PRIMARY KEY,
            nome VARCHAR(255) CONSTRAINT nnNomeCliente NOT NULL,
            nif INTEGER CONSTRAINT ckNifCliente CHECK(REGEXP_LIKE(nif, '^\d{9}$')),
            creditos INTEGER DEFAULT 0,
            id_cartao INTEGER CONSTRAINT  nnClienteIdCartao NOT NULL,
            id_endereco INTEGER CONSTRAINT nnClienteIdEndereco NOT NULL);

CREATE TABLE Estado_Cliente (
    id_Estado INTEGER generated as identity CONSTRAINT pkEstadoClienteId  PRIMARY KEY,
    descricao varchar(255) CONSTRAINT nnDescricaoEstadoCliente not null
);

CREATE TABLE Endereco (
            id_endereco INTEGER GENERATED AS IDENTITY CONSTRAINT pkIdEnderecoEndereco PRIMARY KEY,
            rua VARCHAR(255) CONSTRAINT nnRuaEndereco NOT NULL,
            num_Porta VARCHAR(255) CONSTRAINT nnNumPortaEndereco NOT NULL,
            cod_Postal VARCHAR(255) CONSTRAINT nnCodPostalEndereco NOT NULL,
            localidade VARCHAR(255) CONSTRAINT nnLocalidadeEndereco NOT NULL,
            pais VARCHAR(255) CONSTRAINT nnPaisEndereco NOT NULL,
            latitude REAL CONSTRAINT nnLatitudeEndereco NOT NULL,
            longitude Real CONSTRAINT nnLongitudeEndereco NOT NULL,
            altitude real CONSTRAINT nnAltitudeEndereco NOT NULL
);

CREATE TABLE Cartao_Credito (
            id_Cartao INTEGER GENERATED AS IDENTITY  CONSTRAINT pkCartaoCredito PRIMARY KEY,
            numero VARCHAR(16) CONSTRAINT nnNumeroCartaoCredito NOT NULL ,
            ccv INTEGER CONSTRAINT nnCCVCartaoCredito NOT NULL ,
            data_Validade date CONSTRAINT nnDataValidadeCartaoCredito NOT NULL
);

CREATE TABLE Utilizador (
            id_user   INTEGER GENERATED AS IDENTITY Constraint pkIdUser PRIMARY KEY,
            username    VARCHAR(255) CONSTRAINT nnUsernameUser NOT NULL UNIQUE,
            password    VARCHAR(255) CONSTRAINT nnPasswordUser NOT NULL,
            tipo_user Integer CONSTRAINT nnTipoUserUser not null,
            email VARCHAR(255) CONSTRAINT ckEmailUser CHECK(REGEXP_LIKE(email, '^.*@.*$'))
                CONSTRAINT nnEmailUser NOT NULL UNIQUE
);

CREATE TABLE Tipo_User (
            Id_tipo    INTEGER GENERATED AS IDENTITY CONSTRAINT pkId_TipoUser   PRIMARY KEY,
            descricao  VARCHAR(255)  CONSTRAINT nnDescricaoTipoUser    NOT NULL
);

CREATE TABLE Meio_Transporte (
    id_transporte INTEGER GENERATED AS IDENTITY CONSTRAINT pkIdMeioTransporte PRIMARY KEY,
    capacidade_Bateria INTEGER CONSTRAINT nnCapacidadeBateriaTransporte NOT NULL
        constraint ckCapacidadeBateriaTransporte check ( capacidade_Bateria>0),
    id_Estado INTEGER CONSTRAINT  nnIdEstadoTransporte NOT NULL,
    carga_atual INTEGER CONSTRAINT nnCargaAtualTransporte NOT NULL
        constraint ckCargaAtualTransporte check ( carga_atual>=0 AND carga_atual <= 100),
    altura NUMERIC(6,4) CONSTRAINT nnAlturaTransporte NOT NULL
        constraint ckAlturaTransporte check (altura > 0),
    largura NUMERIC(6,4) CONSTRAINT nnLarguraTransporte NOT NULL
        constraint ckLarguraTransporte check (largura > 0),
    peso NUMERIC(6,4) CONSTRAINT  nnPesoTransporte NOT NULL
        constraint ckPesoTransporte check (peso > 0),
    velocidade_maxima NUMERIC(6,2) CONSTRAINT nnVelocidadeTransporte NOT NULL
        constraint ckVelocidadeTransporte check (velocidade_maxima > 0),
    id_slot INTEGER,
    id_tipo INTEGER CONSTRAINT nnIdTipoTransporte NOT NULL
);

CREATE TABLE Scooter (
    id_scooter INTEGER CONSTRAINT pkIdScooter PRIMARY KEY
);

CREATE TABLE Drone (
    id_drone INTEGER CONSTRAINT pkIdDrone PRIMARY KEY,
    comprimento NUMERIC(6,4) CONSTRAINT nnComprimentoDrone NOT NULL
        constraint ckComprimentoDrone check (comprimento > 0),
    velocidade_levantamento NUMERIC(6,2) CONSTRAINT nnVelocidadeLevantamento NOT NULL
        constraint ckVelocidadeLevantamento check (velocidade_levantamento > 0)
);

CREATE TABLE Tipo_Transporte (
            id_tipo INTEGER GENERATED AS IDENTITY CONSTRAINT pkIdTipoTransporte PRIMARY KEY,
            descricao varchar(255) CONSTRAINT nnDescricaoTipoTransporte NOT NULL,
            eficiencia FLOAT CONSTRAINT nnEficienciaTipoTransporte NOT NULL
                CONSTRAINT ckEficienciaTipoTransporte check(eficiencia > 0 AND eficiencia <= 100)
);

CREATE TABLE estado_meio_transporte (
    id_Estado INTEGER generated as identity CONSTRAINT pkEstadoTransporteId  PRIMARY KEY,
    descricao varchar(255) CONSTRAINT nnDescricaoEstadoTransporte not null
);

CREATE TABLE Estacionamento (
            id_Estacionamento INTEGER GENERATED AS IDENTITY CONSTRAINT pkIdEstacionamento PRIMARY KEY,
            maximo_scooters INTEGER CONSTRAINT nnMaximoScootersEstacionamento NOT NULL,
            maximo_drones INTEGER CONSTRAINT nnMaximoDronesEstacionamento NOT NULL,
            potencia_maxima NUMERIC(5,2) CONSTRAINT nnPotenciaMaximaEstacionamento NOT NULL
);
            
CREATE TABLE Slot_Estacionamento (
            id_slot INTEGER GENERATED AS IDENTITY CONSTRAINT pkIdSlotEstacionamento PRIMARY KEY,
            slot_Carga smallint CONSTRAINT nnSlotCargaSlot_Estacionamento not null
                constraint ckSlotCargaSlotEstacionamento CHECK ( slot_Carga =0 OR slot_Carga = 1) ,
            id_Estacionamento INTEGER CONSTRAINT nnIdEstacionamentoSlotEstacionamento not null,
            id_tipo INTEGER CONSTRAINT nnIdTipoEstacionamentoSlot NOT NULL
);
            
CREATE TABLE Farmacia (
             id_Farmacia INTEGER GENERATED AS IDENTITY CONSTRAINT pkIdFarmacia PRIMARY KEY,
             nome    VARCHAR(255) CONSTRAINT nnNomeFarmacia NOT NULL,
             id_Endereco INTEGER CONSTRAINT nnIdEnderecoFarmacia NOT NULL,
             id_Estacionamento Integer CONSTRAINT nnIdEstacionamentoFarmacia NOT NULL
);

create table Estafeta (
            id_estafeta INTEGER CONSTRAINT pkIdEstafeta PRIMARY KEY,
            nome VARCHAR(255) CONSTRAINT nnNomeEstafeta NOT NULL,
            nif INTEGER CONSTRAINT ckNifEstafeta CHECK(REGEXP_LIKE(nif, '^\d{9}$'))
                        CONSTRAINT nnNifEstafeta NOT NULL ,
            carga_Maxima NUMERIC(6,4) CONSTRAINT nnCargaMaximaEstafeta NOT NULL
                    Constraint CKCargaMaximaEstafeta CHECK ( carga_Maxima>0 ),
            id_farmacia INTEGER CONSTRAINT nnidFarmaciaEstafeta NOT NULL,
            peso NUMERIC(6,4) CONSTRAINT  nnIdPesoEstafeta NOT NULL
                constraint ckPesoEstafeta check (peso > 0)
);

    
CREATE TABLE Produto (
            id_produto INTEGER GENERATED AS IDENTITY CONSTRAINT pkIdProduto PRIMARY KEY,
            nome VARCHAR(255) CONSTRAINT nnNomeProduto NOT NULL,
            valor_unitario numeric(5,2) CONSTRAINT nnPrecoUnitarioProduto NOT NULL
                        CONSTRAINT ckPrecoUnitarioProduto check ( valor_unitario>0 ),
            preco numeric(5,2) CONSTRAINT nnPrecoProduto NOT NULL
                        CONSTRAINT ckPrecoProduto check ( preco>0 ),
            peso numeric(5,4) CONSTRAINT nnPesoProduto NOT NULL
                        CONSTRAINT ckPesoProduto check ( peso>0 ),
            descricao VARCHAR(255) CONSTRAINT nnDescricaoProduto NOT NULL
);  

CREATE TABLE Produto_Farmacia (
            id_produto INTEGER ,
            id_Farmacia INTEGER ,
            stock INTEGER CONSTRAINT nnStockProdutoFarmacia NOT NULL CONSTRAINT ckStockProduto check ( stock>=0 ),
            CONSTRAINT pkProdutoFarmacia primary key (id_Produto,id_Farmacia)
);

CREATE TABLE Entrega (
            id_Entrega INTEGER GENERATED AS IDENTITY CONSTRAINT pkidEntrega PRIMARY KEY,
            data_Entrega date CONSTRAINT nnDataEntregaEntrega NOT NULL,
            id_estafeta INTEGER,
            id_transporte INTEGER
);
        
CREATE TABLE Encomenda (
            id_Encomenda INTEGER GENERATED AS IDENTITY CONSTRAINT pkidEncomenda PRIMARY KEY,
            nif_Cliente INTEGER CONSTRAINT nnNifClienteEncomenda NOT NULL,
            id_Farmacia INTEGER CONSTRAINT nnIdFarmaciaEncomenda NOT NULL,
            id_Cliente INTEGER CONSTRAINT nnIdClienteEncomenda NOT NULL,
            id_Estado INTEGER CONSTRAINT  nnIdEstadoEncomenda NOT NULL,
            peso NUMERIC(5,4) CONSTRAINT  nnIdPesoEncomenda NOT NULL
);

CREATE TABLE Fatura (
            id_Fatura integer generated as identity constraint pkFaturaId Primary Key,
            nif INTEGER CONSTRAINT ckNifFatura CHECK(REGEXP_LIKE(nif, '^\d{9}$'))
                        CONSTRAINT nnNifFatura NOT NULL,
            valor_Total NUMERIC(5,2) constraint nnValorTotalFatura NOT NULL,
            id_Encomenda INTEGER CONSTRAINT nnIdEncomendaFatura NOT NULL);

CREATE TABLE  Produto_Encomenda(
             id_Produto integer,
             id_Encomenda integer,
             quantidade integer constraint nnQuantidadeProdutoEncomenda NOT NULL
                        Constraint ckQuantidadeProdutoEncomenda check ( quantidade>0 ),
             CONSTRAINT pkProdutoEncomenda primary key (id_Produto,id_Encomenda)
);

CREATE TABLE estado_Encomenda (
            id_Estado INTEGER generated as identity CONSTRAINT pkEstadoEncomendaId  PRIMARY KEY,
            descricao varchar(255) CONSTRAINT nnDescriecaoEstadoEncomenda not null
);

CREATE TABLE Gestor_Farmacia (
	id_gestor INTEGER CONSTRAINT pkIdGestor PRIMARY KEY,
	id_farmacia INTEGER CONSTRAINT nnIdFarmaciaGestor NOT NULL
);

CREATE TABLE enderecos_adjacentes(
    id_endereco1 INTEGER,
    id_endereco2 INTEGER,
    id_tipo INTEGER,
    id_direcao INTEGER CONSTRAINT nnEnderecosAdjacentes NOT NULL,
    id_condicoes INTEGER,
    velocidade_vento NUMERIC(6,2) CONSTRAINT nnVelocidadeVento NOT NULL
        constraint ckVelocidadeVento check (velocidade_vento > 0),
    angulo_vento NUMERIC(5,2) CONSTRAINT nnAnguloVento NOT NULL
        CONSTRAINT ckAnguloVento check (angulo_vento > -180 AND angulo_vento <= 180),
    CONSTRAINT pkEnderecoAdjacentes primary key (id_endereco1, id_endereco2, id_tipo)
);

CREATE TABLE conexao_enderecos(
    id_direcao INTEGER generated by default as identity CONSTRAINT pkIdDirecao PRIMARY KEY,
    descricao VARCHAR (50) CONSTRAINT nnConexaoEnderecos NOT NULL
);

CREATE TABLE condicoes_rua (
    id_condicoes INTEGER generated as identity CONSTRAINT pkIdCondicoesRua PRIMARY KEY,
    descricao varchar(255) CONSTRAINT nnDescricaoCondicoesRua NOT NULL,
    coeficiente_resistencia NUMERIC(3,3) CONSTRAINT nnCoeficienteResistenciaRua NOT NULL
);

CREATE TABLE tipo_conexao (
    id_tipo INTEGER generated as identity CONSTRAINT pkIdTipoConexao PRIMARY KEY,
    descricao VARCHAR (50) CONSTRAINT nnTipoConexao NOT NULL
);

CREATE TABLE entrega_encomendas(
    id_entrega INTEGER,
    id_encomenda INTEGER,
    CONSTRAINT pkEncomendasEntrega primary key (id_entrega, id_encomenda)
);

CREATE TABLE Transferencia_Produto (
    id_transferencia INTEGER generated as identity CONSTRAINT pkIdTransferencia PRIMARY KEY,
    id_fornecedor INTEGER CONSTRAINT nnFornecedorTransferencia NOT NULL,
    id_recetor INTEGER CONSTRAINT nnRecetorTransferencia NOT NULL,
    id_produto INTEGER CONSTRAINT nnProdutoTransferencia NOT NULL,
    quantidade INTEGER CONSTRAINT nnQuantidadeTransferencia NOT NULL,
        CONSTRAINT ckQuantidadeTransferencia check (quantidade > 0),
    id_estado INTEGER CONSTRAINT nnEstadoTransferencia NOT NULL,
    id_transporte INTEGER CONSTRAINT nnTransporteTransferencia NOT NULL
);

-- ** chaves estrangeiras **
ALTER TABLE Meio_Transporte add Constraint fkTransporteIdEstado FOREIGN KEY (id_Estado) REFERENCES estado_meio_transporte(id_Estado);
ALTER TABLE Meio_Transporte MODIFY id_estado DEFAULT 1;
ALTER TABLE Meio_Transporte MODIFY carga_atual DEFAULT 100;
ALTER TABLE Meio_Transporte add Constraint fkTransporteIdSlot FOREIGN KEY (id_slot) REFERENCES Slot_Estacionamento(id_slot);
ALTER TABLE Meio_Transporte add Constraint fkIdSlotTransporte FOREIGN KEY (id_tipo) REFERENCES Tipo_transporte(id_tipo);

ALTER TABLE Scooter add Constraint fkIdScooter FOREIGN KEY (id_scooter) REFERENCES Meio_Transporte(id_transporte);

ALTER TABLE Drone add Constraint fkIdDrone FOREIGN KEY (id_drone) REFERENCES Meio_Transporte(id_transporte);

ALTER TABLE Slot_Estacionamento add constraint fkSlotEstacionamentoidEstacionamento FOREIGN KEY (id_Estacionamento) REFERENCES Estacionamento(id_Estacionamento);
ALTER TABLE Slot_Estacionamento add constraint fkSlotEstacionamentoidTipo FOREIGN KEY (id_tipo) REFERENCES Tipo_Transporte(id_tipo);

ALTER TABLE Farmacia add constraint fkFarmaciaIdEndereco FOREIGN KEY (id_Endereco) REFERENCES Endereco(id_Endereco);
ALTER TABLE Farmacia  ADD CONSTRAINT fkIdEstacionamentoFarmacia FOREIGN KEY (id_Estacionamento) references Estacionamento(id_Estacionamento);

ALTER TABLE Transferencia_Produto ADD CONSTRAINT fkFornecedorTransferencia FOREIGN KEY (id_fornecedor) REFERENCES Farmacia(id_farmacia);
ALTER TABLE Transferencia_Produto ADD CONSTRAINT fkRecetorTransferencia FOREIGN KEY (id_recetor) REFERENCES Farmacia(id_farmacia);
ALTER TABLE Transferencia_Produto ADD CONSTRAINT fkProdutoTransferencia FOREIGN KEY (id_produto) REFERENCES Produto(id_produto);
ALTER TABLE Transferencia_Produto ADD CONSTRAINT fkEstadoTransferencia FOREIGN KEY (id_estado) REFERENCES estado_Encomenda(id_Estado);
ALTER TABLE Transferencia_Produto ADD CONSTRAINT fkTransporteTransferencia FOREIGN KEY (id_transporte) REFERENCES Meio_Transporte(id_transporte);

ALTER TABLE Estafeta add Constraint fkEstafetaIdEstafeta FOREIGN KEY (id_estafeta) REFERENCES Utilizador(id_user);
ALTER TABLE Estafeta add Constraint fkEstafetaiDFarmacia FOREIGN KEY (id_farmacia) REFERENCES Farmacia(id_Farmacia);

ALTER TABLE Entrega add Constraint fkEntregaIdEstafeta FOREIGN KEY (id_estafeta) REFERENCES Estafeta(id_estafeta);
ALTER TABLE Entrega add Constraint fkEntregaIdTransporte FOREIGN KEY (id_transporte) REFERENCES Meio_Transporte(id_transporte);

ALTER TABLE Produto_Encomenda add Constraint fkProdutoEncomendaIDProduto FOREIGN KEY (id_Produto) REFERENCES Produto(id_Produto);
ALTER TABLE Produto_Encomenda add Constraint fkProdutoEncomendaIDEncomenda FOREIGN KEY (id_Encomenda) REFERENCES Encomenda(id_Encomenda);

ALTER TABLE Produto_Farmacia add Constraint fkProdutoFarmaciaIDProduto FOREIGN KEY (id_Produto) REFERENCES Produto(id_Produto);
ALTER TABLE Produto_Farmacia add Constraint fkProdutoFarmaciaIDFarmacia FOREIGN KEY (id_Farmacia) REFERENCES Farmacia(id_Farmacia);

ALTER TABLE Utilizador add Constraint fkUserTipoUser FOREIGN KEY (tipo_user) REFERENCES Tipo_User(Id_tipo);

ALTER TABLE Cliente add Constraint fkClienteIdCartao FOREIGN KEY (id_cartao) REFERENCES Cartao_Credito(id_cartao);
ALTER TABLE Cliente add Constraint fkClienteIdEndereco FOREIGN KEY (id_endereco) REFERENCES Endereco(id_endereco);
ALTER TABLE Cliente add Constraint fkClienteIdCliente FOREIGN KEY (id_cliente) REFERENCES Utilizador(id_user);
ALTER TABLE Cliente ADD id_estado INTEGER CONSTRAINT nnClienteIdEstado NOT NULL;
ALTER TABLE Cliente ADD CONSTRAINT fkClienteIdEstado FOREIGN KEY (id_estado) REFERENCES Estado_Cliente(id_estado);
ALTER TABLE Cliente MODIFY id_estado DEFAULT 1;

ALTER TABLE Encomenda add Constraint fkEncomendaIdFarmacia FOREIGN KEY (id_Farmacia) rEFERENCES Farmacia(id_Farmacia);
ALTER TABLE Encomenda add Constraint fkEncomendaIdCliente FOREIGN KEY (id_Cliente) rEFERENCES Cliente(id_Cliente);
ALTER TABLE Encomenda add Constraint fkEncomendaIdEstado FOREIGN KEY (id_Estado) rEFERENCES estado_Encomenda(id_Estado);

ALTER TABLE Fatura ADD CONSTRAINT fkFaturaIdEncomenda FOREIGN KEY (id_Encomenda) references Encomenda(id_Encomenda);

ALTER TABLE Gestor_Farmacia add Constraint fkGestorIdGestor FOREIGN KEY (id_gestor) REFERENCES Utilizador(id_user);
ALTER TABLE Gestor_Farmacia add Constraint fkGestorIdFarmacia FOREIGN KEY (id_farmacia) REFERENCES Farmacia(id_farmacia);

ALTER TABLE enderecos_adjacentes add constraint fkEnderecosAdjacentes1 FOREIGN KEY (id_endereco1) REFERENCES ENDERECO;
ALTER TABLE enderecos_adjacentes add constraint fkEnderecosAdjacentes2 FOREIGN KEY (id_endereco2) REFERENCES ENDERECO;
ALTER TABLE enderecos_adjacentes add constraint fkEnderecosAdjacentesTipo FOREIGN KEY (id_tipo) REFERENCES tipo_conexao(id_tipo);
ALTER TABLE enderecos_adjacentes add constraint fkEnderecosAdjacentesSentido FOREIGN KEY (id_direcao) REFERENCES conexao_enderecos(id_direcao);
ALTER TABLE enderecos_adjacentes add Constraint fkEnderecosAdjacentesCondicoes FOREIGN KEY (id_condicoes) REFERENCES condicoes_rua(id_condicoes);

ALTER TABLE entrega_encomendas add constraint fkIdEntrega FOREIGN KEY (id_entrega) REFERENCES ENTREGA;
ALTER TABLE entrega_encomendas add constraint fkIdEncomenda FOREIGN KEY (id_encomenda) REFERENCES ENCOMENDA;

-- INSERTS B�SICOS
INSERT INTO tipo_user (descricao) values ('administrador');
INSERT INTO tipo_user (descricao) values ('gestor');
INSERT INTO tipo_user (descricao) values ('estafeta');
INSERT INTO tipo_user (descricao) values ('cliente');

INSERT INTO estado_encomenda (descricao) values ('Pagamento pendente');
INSERT INTO estado_encomenda (descricao) values ('Pronto a enviar');
INSERT INTO estado_encomenda (descricao) values ('Enviado');
INSERT INTO estado_encomenda (descricao) values ('Entregue');
INSERT INTO estado_encomenda (descricao) values ('Entregue e creditos gerados');

INSERT INTO estado_meio_transporte (descricao) values ('Funcional');
INSERT INTO estado_meio_transporte (descricao) values ('Avariada');

INSERT INTO tipo_conexao (descricao) values ('Terrestre');
INSERT INTO tipo_conexao (descricao) values ('Aereo');

INSERT INTO conexao_enderecos values (1,'sentido unico 1 para 2');
INSERT INTO conexao_enderecos values (2,'sentido unico 2 para 1');
INSERT INTO conexao_enderecos values (3,'duplo sentido');

INSERT INTO condicoes_rua (descricao, coeficiente_resistencia) values ('Asfalto novo', 0.01);
INSERT INTO condicoes_rua (descricao, coeficiente_resistencia) values ('Asfalto', 0.02);
INSERT INTO condicoes_rua (descricao, coeficiente_resistencia) values ('Gravilha nova', 0.02);
INSERT INTO condicoes_rua (descricao, coeficiente_resistencia) values ('Calcada', 0.03);
INSERT INTO condicoes_rua (descricao, coeficiente_resistencia) values ('Gravilha', 0.04);

INSERT INTO Tipo_Transporte (descricao, eficiencia) values ('Scooter', 70);
INSERT INTO Tipo_Transporte (descricao, eficiencia) values ('Drone', 29);

INSERT INTO estado_cliente (descricao) values ('Ativo');
INSERT INTO estado_cliente (descricao) values ('Removido');

-- INSERT do ADMIN
insert into UTILIZADOR (username,password,TIPO_USER,EMAIL)values ('admin','adminPass',1,'admin@farmacia.com');

COMMIT;
