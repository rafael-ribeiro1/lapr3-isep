create or replace function get_scooters_farmacia(p_id_farmacia_inical FARMACIA.ID_FARMACIA%TYPE)
    return sys_refcursor
    is
    c_scooters sys_refcursor;
    v_id_estacionamento_farmacia Farmacia.id_estacionamento%type;
    v_id_tipo TIPO_TRANSPORTE.ID_TIPO%type;
begin

    select ID_ESTACIONAMENTO into v_id_estacionamento_farmacia
    from FARMACIA where ID_FARMACIA = p_id_farmacia_inical;

    select ID_TIPO into v_id_tipo
    from TIPO_TRANSPORTE
    where DESCRICAO like 'Scooter';

    -- buscar todas as scooters com carga igual ou maior a carga passada como parametro
    open c_scooters  for
        select m.ID_TRANSPORTE,m.CAPACIDADE_BATERIA,m.CARGA_ATUAL,m.ALTURA,m.LARGURA,m.PESO,m.VELOCIDADE_MAXIMA
        from MEIO_TRANSPORTE m
                 inner join SLOT_ESTACIONAMENTO se on se.ID_SLOT = m.ID_SLOT
                 inner join TIPO_TRANSPORTE tp on tp.ID_TIPO = m.ID_TIPO
        where m.ID_SLOT is not null AND m.ID_TIPO=v_id_tipo AND se.ID_ESTACIONAMENTO=v_id_estacionamento_farmacia;
    return c_scooters;
exception
    when no_data_found then
        return null;
end;