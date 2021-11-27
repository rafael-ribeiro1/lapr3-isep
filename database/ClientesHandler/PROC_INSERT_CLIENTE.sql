CREATE OR REPLACE PROCEDURE insert_cliente(p_username utilizador.username%type, p_password utilizador.PASSWORD%type,p_email utilizador.EMAIL%type,p_tipo_user tipo_user.DESCRICAO%type,p_nome Cliente.Nome%type ,p_numero_cartao Cartao_credito.numero%type , p_ccv Cartao_credito.ccv%type, p_data_validade Cartao_credito.Data_validade%type , p_rua endereco.rua%type, p_num_porta endereco.num_porta%type,
                                           p_cod_postal endereco.cod_postal%type, p_localidade endereco.localidade%type, p_pais endereco.pais%type, p_latitude endereco.latitude%type,
                                           p_longitude endereco.longitude%type, p_altitude endereco.altitude%type )
AS
    v_id_tipo_user TIPO_USER.ID_TIPO%TYPE;
    v_id_endereco endereco.id_endereco%type;
    v_id_cartao_credito cartao_credito.id_cartao%type;
    v_id_utilizador Utilizador.id_user%type;
Begin
    -- Obter o id de tipo para
    select ID_TIPO into v_id_tipo_user
    from TIPO_USER
    where DESCRICAO = p_tipo_user;


    -- Inserir endereco
    insert into endereco(rua,num_porta,cod_postal,localidade,pais,latitude,longitude,ALTITUDE)
    values (p_rua,p_num_porta,p_cod_postal,p_localidade,p_pais,p_latitude,p_longitude,p_altitude);
    -- Obter id do endereco inserido
    select max(id_endereco) into v_id_endereco
    from endereco;

    -- Inserir cartaoCredito
    insert into CARTAO_CREDITO (NUMERO,CCV,DATA_VALIDADE) values (p_numero_cartao,p_ccv,p_data_validade);
    -- Obter o id do cartao de credito inserido
    select max(ID_CARTAO) into v_id_cartao_credito
    from CARTAO_CREDITO;

    -- Inserir Utilizador
    insert into UTILIZADOR (USERNAME,PASSWORD,TIPO_USER,EMAIL) values (p_username,p_password,v_id_tipo_user,p_email);
    --Obter o id do utilizador criado
    select max(ID_USER) into v_id_utilizador
    from UTILIZADOR;

    -- inserir cliente
    insert into CLIENTE (ID_CLIENTE, NOME, ID_CARTAO, ID_ENDERECO) values (v_id_utilizador,p_nome,v_id_cartao_credito,v_id_endereco);
end;