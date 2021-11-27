CREATE OR REPLACE FUNCTION criar_encomenda(p_nif_cliente encomenda.nif_cliente%type ,p_id_farmacia farmacia.id_Farmacia%type, p_id_cliente CLIENTE.ID_CLIENTE%type , p_estado_descricao ESTADO_ENCOMENDA.DESCRICAO%type, p_peso ENCOMENDA.Peso%type)
return ENCOMENDA.ID_ENCOMENDA%type
is
    v_id_estado estado_encomenda.id_estado%type ;
    v_id_encomenda_gerado encomenda.id_encomenda%type;
Begin
    select id_estado into v_id_estado
        from ESTADO_ENCOMENDA
            where DESCRICAO=p_estado_descricao;

    Insert into ENCOMENDA(NIF_CLIENTE,ID_FARMACIA,ID_CLIENTE,ID_ESTADO,PESO) VALUES (p_nif_cliente,p_id_farmacia,p_id_cliente,v_id_estado,p_peso);

    -- buscar o ultimo id gerado
    select max(ID_ENCOMENDA) into v_id_encomenda_gerado
        from ENCOMENDA;

    return v_id_encomenda_gerado;
exception
    when no_data_found then
        return -1;

end;