create or replace procedure procRemoverVeiculo (p_id meio_transporte.id_transporte%type)
is
    l_slot meio_transporte.id_slot%type;
    l_estado estado_meio_transporte.descricao%type;
begin
    select id_estado into l_estado
    from estado_meio_transporte
    where descricao = 'Avariada';

    update meio_transporte 
    set id_estado = l_estado
    where id_transporte = p_id; 
    
    select id_slot into l_slot
    from meio_transporte 
    where id_transporte = p_id;
    
    if l_slot is not null
        then update meio_transporte
        set id_slot = null
        where id_transporte = p_id;
    end if;
end;
/