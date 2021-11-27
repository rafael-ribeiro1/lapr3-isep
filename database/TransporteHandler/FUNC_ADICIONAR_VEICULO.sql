create or replace function funcAdicionarVeiculo(p_capacidadeBateria in meio_transporte.capacidade_bateria%type, p_cargaAtual in meio_transporte.carga_atual%type,
                                           p_altura in meio_transporte.altura%type, p_largura in meio_transporte.largura%type, p_peso in meio_transporte.peso%type,
                                           p_velocidade in meio_transporte.velocidade_maxima%type, p_descricao in  tipo_transporte.descricao%type,
                                           p_comprimento in drone.comprimento%type, p_velocidade_levantamento in drone.velocidade_levantamento%type) 
                                           return meio_transporte.id_transporte%type is
        l_tipo tipo_transporte.id_tipo%type;
        l_id meio_transporte.id_transporte%type;
begin

    select id_tipo into l_tipo
    from tipo_transporte 
    where descricao = p_descricao;

    insert into meio_transporte(capacidade_bateria, carga_atual, altura, largura, peso, velocidade_maxima, id_tipo)
    values (p_capacidadeBateria, p_cargaAtual,p_altura, p_largura, p_peso, p_velocidade, l_tipo);

    select max(id_transporte) into l_id
    from meio_transporte;
    
    if p_descricao = 'Scooter' then
        insert into scooter(id_scooter) values (l_id);
    elsif p_descricao = 'Drone' then
        insert into drone(id_drone,comprimento,velocidade_levantamento) values (l_id,p_comprimento,p_velocidade_levantamento);
    end if;

    return l_id;
exception
    when OTHERS then
        delete from meio_transporte where id_transporte = l_id;
        raise_application_error(-20001, 'Erro nos parametros');
end;
/