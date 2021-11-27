CREATE OR REPLACE FUNCTION insert_farmacia(p_nome farmacia.nome%type, p_rua endereco.rua%type, p_num_porta endereco.num_porta%type,
    p_cod_postal endereco.cod_postal%type, p_localidade endereco.localidade%type, p_pais endereco.pais%type, p_latitude endereco.latitude%type,
    p_longitude endereco.longitude%type, p_altitude endereco.altitude%type, p_maximo_scooters estacionamento.maximo_scooters%type,
    p_num_carga_scooters integer, p_maximo_drones estacionamento.maximo_drones%type, p_num_carga_drones integer, p_potencia estacionamento.potencia_maxima%type) 
    return farmacia.id_farmacia%type
AS
    v_id_farmacia farmacia.id_farmacia%type;
    v_id_endereco endereco.id_endereco%type;
    v_id_estacionamento estacionamento.id_estacionamento%type;
    v_id_estacionamento_scooter Slot_Estacionamento.id_tipo%type;
    v_id_estacionamento_drone Slot_Estacionamento.id_tipo%type;
    
    invalid_parameters exception;
BEGIN
    -- Validar parametros
    if p_num_carga_scooters < 0 OR p_num_carga_drones < 0 OR p_num_carga_scooters > p_maximo_scooters OR 
            p_maximo_scooters < 1 OR p_maximo_drones < 1 OR p_num_carga_drones > p_maximo_drones then
        raise invalid_parameters;
    end if;
    -- Inserir endere�o
    insert into endereco(rua,num_porta,cod_postal,localidade,pais,latitude,longitude,altitude)
        values (p_rua,p_num_porta,p_cod_postal,p_localidade,p_pais,p_latitude,p_longitude,p_altitude);
    -- Obter id do endere�o inserido
    select max(id_endereco) into v_id_endereco
        from endereco;
    -- Inserir estacionamento
    insert into estacionamento(maximo_scooters,maximo_drones,potencia_maxima) values (p_maximo_scooters,p_maximo_drones,p_potencia);
    -- Obter id do estacionamento inserido
    select max(id_estacionamento) into v_id_estacionamento
        from estacionamento;
    -- Obter ids dos tipos de estacionamento
    select id_tipo into v_id_estacionamento_scooter
        from Tipo_Transporte where descricao = 'Scooter';
    select id_tipo into v_id_estacionamento_drone
        from Tipo_Transporte where descricao = 'Drone';
    -- Inserir slots de carga de scooters
    for counter in 1 .. p_num_carga_scooters
    loop
        insert into slot_estacionamento(slot_carga, id_estacionamento, id_tipo) values (1, v_id_estacionamento, v_id_estacionamento_scooter);
    end loop;
    -- Inserir slots de estacionamento sem carga de scooters
    for counter in 1 .. p_maximo_scooters - p_num_carga_scooters
    loop
        insert into slot_estacionamento(slot_carga, id_estacionamento, id_tipo) values (0, v_id_estacionamento, v_id_estacionamento_scooter);
    end loop;
    -- Inserir slots de carga de drones
    for counter in 1 .. p_num_carga_drones
    loop
        insert into slot_estacionamento(slot_carga, id_estacionamento, id_tipo) values (1, v_id_estacionamento, v_id_estacionamento_drone);
    end loop;
    -- Inserir slots de estacionamento sem carga de drones
    for counter in 1 .. p_maximo_drones - p_num_carga_drones
    loop
        insert into slot_estacionamento(slot_carga, id_estacionamento, id_tipo) values (0, v_id_estacionamento, v_id_estacionamento_drone);
    end loop;
    -- Inserir farm�cia
    insert into farmacia (nome, id_endereco, id_estacionamento) values (p_nome, v_id_endereco, v_id_estacionamento);
    
    select max(id_farmacia) into v_id_farmacia
    from farmacia;
    
    return v_id_farmacia;
EXCEPTION
    when invalid_parameters then
        return -1;
END;