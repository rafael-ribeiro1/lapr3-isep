create or replace function inserirEntrega
(p_data ENTREGA.data_entrega%type,p_id_estafeta ENTREGA.ID_ESTAFETA%type default null, p_idTransporte ENTREGA.ID_TRANSPORTE%type)
return ENTREGA.ID_ENTREGA%type
is
    v_id ENTREGA.ID_ENTREGA%type;
begin
    insert into ENTREGA(DATA_ENTREGA,ID_ESTAFETA,ID_TRANSPORTE)
    values(p_data,p_id_estafeta,p_idTransporte);

    select ID_ENTREGA into v_id
    from ENTREGA
        order by ID_ENTREGA desc
        fetch first 1 row only;
    return v_id; -- return do id da ultima entrega inserida
end;