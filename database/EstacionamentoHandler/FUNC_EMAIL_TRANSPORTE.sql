CREATE OR REPLACE FUNCTION email_from_transporte(p_id_transporte meio_transporte.id_transporte%type)
    RETURN utilizador.email%type
IS
    v_tipo tipo_transporte.descricao%type;
    v_email utilizador.email%type;
BEGIN
    -- Descobrir o tipo de transporte
    select tt.descricao into v_tipo
    from meio_transporte mt inner join tipo_transporte tt
    on mt.id_tipo = tt.id_tipo
    where mt.id_transporte = p_id_transporte;
    -- Obter email
    if v_tipo = 'Scooter' then
        select u.email into v_email
        from entrega e inner join utilizador u
        on e.id_estafeta = u.id_user
        where e.id_transporte = p_id_transporte
        order by e.id_entrega desc
        fetch first 1 row only;
        return v_email;
    elsif v_tipo = 'Drone' then
        select u.email into v_email
        from utilizador u inner join tipo_user tu
        on u.tipo_user = tu.id_tipo
        where tu.descricao = 'administrador'
        fetch first 1 row only;
        return v_email;
    else
        return null;
    end if;
EXCEPTION
    when no_data_found then
        return null;
END;