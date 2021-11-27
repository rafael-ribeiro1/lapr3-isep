create or replace procedure proc_AtualizarVeiculo (p_id meio_transporte.id_transporte%type, 
p_cargaAtual meio_transporte.carga_atual%type, p_slot meio_transporte.id_slot%type default null)
is
begin
    update meio_transporte
    set carga_atual = p_cargaAtual,
    id_slot = p_slot
    where id_transporte = p_id;
end;
/