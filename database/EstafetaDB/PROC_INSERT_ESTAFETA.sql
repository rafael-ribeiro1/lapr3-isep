create or replace PROCEDURE insert_estafeta(p_username utilizador.username%type, p_password utilizador.password%type,
    p_tipo_user tipo_user.descricao%type, p_email utilizador.email%type, p_id_farmacia estafeta.id_farmacia%type,
    p_nome estafeta.nome%type, p_nif estafeta.nif%type, p_carga_maxima estafeta.carga_maxima%type, p_peso estafeta.peso%type)
AS
    v_id_tipo_user utilizador.tipo_user%type;
    v_id_user utilizador.id_user%type;
BEGIN
    -- Obter id do tipo de user
    select id_tipo into v_id_tipo_user
        from tipo_user where descricao = p_tipo_user;
    -- Inserir utilizador
    insert into utilizador(username,password,tipo_user,email) values (p_username,p_password,v_id_tipo_user,p_email);
    -- Obter id do user inserido
    select max(id_user) into v_id_user
        from utilizador;
    -- Inserir estafeta da farmácia
    insert into estafeta(id_estafeta,nome,nif,carga_maxima,id_farmacia,peso) values (v_id_user,p_nome,p_nif,p_carga_maxima, p_id_farmacia,p_peso);
EXCEPTION
    when no_data_found then
        raise_application_error(-20001, 'Tipo de user inválido');
END;
/

 