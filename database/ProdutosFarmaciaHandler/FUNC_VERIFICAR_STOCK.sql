create or replace function verificar_stock(p_id_produto PRODUTO_FARMACIA.ID_PRODUTO%TYPE , p_quantidade_desejada PRODUTO_FARMACIA.STOCK%TYPE, p_id_farmacia PRODUTO_FARMACIA.ID_FARMACIA%TYPE)
    return INTEGER
    is
    v_stockAtual  PRODUTO_FARMACIA.STOCK%TYPE;
begin
    select stock into v_stockAtual
    from PRODUTO_FARMACIA
    where ID_FARMACIA=p_id_farmacia and ID_PRODUTO=p_id_produto;
    if v_stockAtual>= p_quantidade_desejada then
        return 1;
    end if;

    return 0; -- se chegar aqui signfica que nao tem stock suficiente

exception
    when no_data_found then
        return 0;
end;