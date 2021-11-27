create or replace procedure atualizarCartaoCredito (p_numero cartao_credito.numero%type, p_ccv cartao_credito.ccv%type, p_data cartao_credito.data_validade%type,
p_id cartao_credito.id_cartao%type)
is
begin
    update cartao_credito
    set numero = p_numero,
    ccv = p_ccv,
    data_validade = p_data
    where id_cartao = p_id; 
end;
/