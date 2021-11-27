create or replace function funcObterSlotDisponivel(p_idTransporte in meio_transporte.id_slot%type,
                                                   p_id_farmacia in farmacia.id_farmacia%type, p_carga in slot_estacionamento.slot_carga%type)
                                           return slot_estacionamento.id_slot%type is
        l_id tipo_transporte.id_tipo%type;
        l_id_estacionamento estacionamento.id_estacionamento%type;
        l_idSlot slot_estacionamento.id_slot%type;
begin
    --Obter o tipo do transporte
    select id_tipo into l_id
    from meio_transporte 
    where id_transporte = p_idTransporte;

    --Obter id do estacionamento
    select id_estacionamento into l_id_estacionamento
    from farmacia where id_farmacia = p_id_farmacia;

   select slot.id_slot into l_idSlot
    from slot_estacionamento slot
    inner join tipo_transporte tt
    on slot.id_tipo = tt.id_tipo
    where slot.id_estacionamento = l_id_estacionamento
    and tt.id_tipo = l_id
    and slot.slot_carga = p_carga
    and slot.id_slot not in (
        select mt.id_slot from meio_transporte mt
        inner join slot_estacionamento se
        on mt.id_slot = se.id_slot
        where se.id_estacionamento = l_id_estacionamento
        and mt.id_slot is not null)
    fetch first 1 row only;
    
    update meio_transporte
        set id_slot = l_idSlot
        where id_transporte = p_idTransporte;

    return l_idSlot;
    
exception   
    when NO_DATA_FOUND then
        return -1;
    
end;
/