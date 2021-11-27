CREATE OR REPLACE FUNCTION get_cliente_logado(p_user UTILIZADOR.ID_USER%TYPE)
    return sys_refcursor
    is
    v_id UTILIZADOR.ID_USER%TYPE;
    c_user sys_refcursor;
begin
    select ID_USER into v_id
    from utilizador where ID_USER=p_user;

    open c_user for
        select c.ID_CLIENTE,c.nome, e.RUA ,e.NUM_PORTA , e.COD_POSTAL,e.LOCALIDADE,e.PAIS,e.LATITUDE,e.LONGITUDE,e.ALTITUDE , cre.NUMERO,cre.CCV,cre.DATA_VALIDADE
        from CLIENTE c
                 INNER JOIN ENDERECO e ON e.ID_ENDERECO=c.ID_ENDERECO
                 INNER JOIN CARTAO_CREDITO cre on cre.ID_CARTAO = c.ID_CARTAO
        where c.ID_CLIENTE=v_id;

    return c_user;
exception
    when no_data_found then
        return null;
end;
