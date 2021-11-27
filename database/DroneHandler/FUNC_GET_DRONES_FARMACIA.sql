create or replace function get_drones_farmacia(p_id_farmacia_inical FARMACIA.ID_FARMACIA%TYPE)
    return sys_refcursor
    is
    c_drones sys_refcursor;
    v_id_tipo TIPO_TRANSPORTE.ID_TIPO%type;
    v_id_estacionamento_farmacia Farmacia.id_estacionamento%type;
begin
    -- buscar apenas o id de endereco da farmacia que se pretende que o estafeta saia
    select ID_ESTACIONAMENTO into v_id_estacionamento_farmacia
    from FARMACIA where ID_FARMACIA = p_id_farmacia_inical;

    select ID_TIPO into v_id_tipo
    from TIPO_TRANSPORTE
    where DESCRICAO like 'Drone';

    open c_drones for
        select m.ID_TRANSPORTE,m.CAPACIDADE_BATERIA,m.CARGA_ATUAL,m.ALTURA,m.LARGURA,m.PESO,m.VELOCIDADE_MAXIMA,d.COMPRIMENTO, d.VELOCIDADE_LEVANTAMENTO
        from MEIO_TRANSPORTE m
                 inner join SLOT_ESTACIONAMENTO se on se.ID_SLOT = m.ID_SLOT
                 inner join TIPO_TRANSPORTE tp on tp.ID_TIPO = m.ID_TIPO
                 inner join DRONE d on d.ID_DRONE = m.id_transporte
        where m.ID_SLOT is not null AND m.ID_TIPO=v_id_tipo AND se.ID_ESTACIONAMENTO=v_id_estacionamento_farmacia;
    return c_drones;
exception
    when no_data_found then
        return null;
end;