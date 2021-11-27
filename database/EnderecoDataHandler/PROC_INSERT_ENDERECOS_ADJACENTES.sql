CREATE OR REPLACE PROCEDURE insert_enderecos_adjacentes(p_lat1 endereco.latitude%type, p_long1 endereco.longitude%type, p_lat2 endereco.latitude%type, p_long2 endereco.longitude%type,
    p_tipo_conexao tipo_conexao.descricao%type, p_sentido conexao_enderecos.descricao%type, p_condicoes condicoes_rua.descricao%type,
    p_velocidade_vento enderecos_adjacentes.velocidade_vento%type, p_angulo_vento enderecos_adjacentes.angulo_vento%type)
AS
    v_id_endereco1 endereco.id_endereco%type;
    v_id_endereco2 endereco.id_endereco%type;
    v_id_tipo_conexao tipo_conexao.id_tipo%type;
    v_id_direcao conexao_enderecos.id_direcao%type;
    v_id_condicoes condicoes_rua.id_condicoes%type;
BEGIN
    -- Obter ids dos endere�os
    select id_endereco into v_id_endereco1
    from endereco where latitude = p_lat1 and longitude = p_long1;
    select id_endereco into v_id_endereco2
    from endereco where latitude = p_lat2 and longitude = p_long2;
    -- Obter tipo de conexao
    select id_tipo into v_id_tipo_conexao
    from tipo_conexao where descricao = p_tipo_conexao;
    -- Obter id do sentido
    select id_direcao into v_id_direcao
    from conexao_enderecos where descricao = p_sentido;
    -- Obter id das condi��es
    if p_condicoes IS NOT NULL then
        select id_condicoes into v_id_condicoes
        from condicoes_rua where descricao = p_condicoes;
    else
        v_id_condicoes := null;
    end if;
    -- Inserir endere�os adjacentes
    insert into enderecos_adjacentes (id_endereco1,id_endereco2,id_tipo,id_direcao,id_condicoes,velocidade_vento,angulo_vento)
    values (v_id_endereco1,v_id_endereco2,v_id_tipo_conexao,v_id_direcao,v_id_condicoes,p_velocidade_vento,p_angulo_vento);
END;