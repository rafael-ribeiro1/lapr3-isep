CREATE OR REPLACE FUNCTION get_estafeta(p_estafeta_email UTILIZADOR.EMAIL%TYPE)
    return sys_refcursor
    is
    v_id_estafeta UTILIZADOR.ID_USER%TYPE;
    c_user sys_refcursor;
begin
    select ID_USER into v_id_estafeta
    from utilizador where UTILIZADOR.EMAIL=p_estafeta_email;

    open c_user for
        select estafeta.ID_ESTAFETA,estafeta.PESO
        from ESTAFETA estafeta
        where estafeta.ID_ESTAFETA=v_id_estafeta;

    return c_user;
exception
    when no_data_found then
        return null;
end;