CREATE OR REPLACE PROCEDURE insert_gestor(p_username utilizador.username%type, p_password utilizador.password%type,
    p_tipo_user tipo_user.descricao%type, p_email utilizador.email%type, p_id_farmacia gestor_farmacia.id_farmacia%type)
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
    -- Inserir gestor da farmácia
    insert into gestor_farmacia(id_gestor,id_farmacia) values (v_id_user,p_id_farmacia);
EXCEPTION
    when no_data_found then
        raise_application_error(-20001, 'Tipo de user inválido');
END;