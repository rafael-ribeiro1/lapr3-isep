CREATE OR REPLACE PROCEDURE estacionar(p_id_farmacia farmacia.id_farmacia%type,
    p_id_transporte meio_transporte.id_transporte%type, p_carga integer)
AS
    v_id_tipo INTEGER;
    v_id_estacionamento estacionamento.id_estacionamento%type;
    v_id_slot slot_estacionamento.id_slot%type;
    invalid_parameter exception;
BEGIN
    -- Validar parametro boolean de carga
    if p_carga < 0 OR p_carga > 1 then
        raise invalid_parameter;
    end if;
    -- Descobrir o tipo de transporte
    select id_tipo into v_id_tipo
    from meio_transporte where id_transporte = p_id_transporte;
    -- Obter id do estacionamento
    select id_estacionamento into v_id_estacionamento
    from farmacia where id_farmacia = p_id_farmacia;
    -- Obter slot disponível
    select slot.id_slot into v_id_slot
    from slot_estacionamento slot
    inner join tipo_transporte tt
    on slot.id_tipo = tt.id_tipo
    where slot.id_estacionamento = v_id_estacionamento
    and slot.slot_carga = p_carga
    and tt.id_tipo = v_id_tipo
    and slot.id_slot not in (
        select mt.id_slot from meio_transporte mt
        inner join slot_estacionamento se
        on mt.id_slot = se.id_slot
        where se.id_estacionamento = v_id_estacionamento
        and mt.id_slot is not null)
    fetch first 1 row only;
    -- Atualizar slot no meio de transorte
    update meio_transporte set id_slot = v_id_slot
    where id_transporte = p_id_transporte;
EXCEPTION
    when invalid_parameter then
        raise_application_error(-20001, 'Parâmetro inválido');
END;