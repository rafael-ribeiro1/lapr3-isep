create or replace function get_farmacias_com_stock(p_produto_id produto.id_produto%type, p_quantidade  produto_farmacia.stock%type)
return sys_refcursor
is
    v_produto_id  produto_farmacia.stock%type;
    c_lista_farmacias sys_refcursor;
    quantidade_invalida exception;
begin
    if(p_quantidade<=0) then
        raise quantidade_invalida;
    end if;

    select ID_PRODUTO into v_produto_id
    from PRODUTO where ID_PRODUTO = p_produto_id;

    open c_lista_farmacias for
        select f.id_farmacia , f.nome , e.*
            from FARMACIA f
            inner join ENDERECO e on e.ID_ENDERECO = f.ID_ENDERECO
            inner join PRODUTO_FARMACIA pf on pf.ID_FARMACIA = f.ID_FARMACIA AND pf.ID_PRODUTO = v_produto_id
                where pf.STOCK >= p_quantidade;

    return c_lista_farmacias;
exception
    when no_data_found then
        return null;
    when quantidade_invalida then
        return null;
end;