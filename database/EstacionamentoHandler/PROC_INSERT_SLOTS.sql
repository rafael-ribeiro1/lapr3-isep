CREATE OR REPLACE PROCEDURE insert_slots(p_id_farmacia farmacia.id_farmacia%type, p_tipo_slot tipo_transporte.descricao%type,
    p_quantidade INTEGER, p_num_carga INTEGER)
AS
    v_id_estacionamento estacionamento.id_estacionamento%type;
    v_id_tipo_estacionamento Slot_Estacionamento.id_tipo%type;
    
    invalid_parameters exception;
BEGIN
    -- Validar parametros
    if p_num_carga < 0 OR p_quantidade < 1 OR p_num_carga > p_quantidade then
        raise invalid_parameters;
    end if;
    -- Obter id do estacionamento
    select id_estacionamento into v_id_estacionamento
        from farmacia where id_farmacia = p_id_farmacia;
    -- Obter id do tipo de estacionamento
    select id_tipo into v_id_tipo_estacionamento
        from tipo_transporte where descricao = p_tipo_slot;
    -- Inserir slots de carga
    for counter in 1 .. p_num_carga
    loop
        insert into slot_estacionamento(slot_carga, id_estacionamento, id_tipo) values (1, v_id_estacionamento, v_id_tipo_estacionamento);
    end loop;
    -- Inserir slots de estacionamento sem carga
    for counter in 1 .. p_quantidade - p_num_carga
    loop
        insert into slot_estacionamento(slot_carga, id_estacionamento, id_tipo) values (0, v_id_estacionamento, v_id_tipo_estacionamento);
    end loop;
EXCEPTION
    when invalid_parameters then
        raise_application_error(-20001, 'Parametro inválido');
END;