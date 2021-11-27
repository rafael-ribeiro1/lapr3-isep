CREATE OR REPLACE FUNCTION get_produtos_farmacia(p_id_farmacia FARMACIA.ID_FARMACIA%TYPE)
return sys_refcursor
is
    v_id_farmacia FARMACIA.ID_FARMACIA%TYPE;
    c_produtos sys_refcursor;
begin
    select ID_FARMACIA into v_id_farmacia
        from FARMACIA where ID_FARMACIA=p_id_farmacia;

    open c_produtos for
        select pf.id_produto , p.NOME , p.VALOR_UNITARIO , p.PRECO , p.PESO , p.DESCRICAO
        from PRODUTO_FARMACIA pf
            inner join  FARMACIA f on f.ID_FARMACIA = pf.ID_FARMACIA
            inner join  PRODUTO p on p.ID_PRODUTO = pf.ID_PRODUTO
            where f.ID_FARMACIA = v_id_farmacia AND pf.STOCK>0; -- só pequena verificao

    return c_produtos;
exception
    when no_data_found then
        return null;
end;