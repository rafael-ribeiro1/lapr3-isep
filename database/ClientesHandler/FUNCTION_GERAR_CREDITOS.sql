CREATE OR REPLACE FUNCTION gerar_Creditos(p_id_encomenda encomenda.id_encomenda%type)
return cliente.creditos%type
is
    v_creditos_antigos cliente.creditos%type;
    v_id_cliente cliente.id_cliente%type;
    v_custo_compra fatura.valor_total%type ;
    v_creditos_adicionar cliente.creditos%type;
    v_creditos_novos cliente.creditos%type;
    v_id_novo_Estado_encomenda Estado_Encomenda.id_estado%type;
begin

    select ID_CLIENTE into v_id_cliente
        from ENCOMENDA where ID_ENCOMENDA=p_id_encomenda;

    select CREDITOS into v_creditos_antigos
        from CLIENTE where ID_CLIENTE = v_id_cliente;

    select VALOR_TOTAL into v_custo_compra
        from FATURA where ID_ENCOMENDA = p_id_encomenda;

    select id_Estado into v_id_novo_Estado_encomenda
    from ESTADO_ENCOMENDA where DESCRICAO like 'Entregue e creditos gerados';

    v_creditos_adicionar:=v_custo_compra/5;

    v_creditos_novos:= v_creditos_antigos+v_creditos_adicionar;

    update CLIENTE set CREDITOS = v_creditos_novos where ID_CLIENTE = v_id_cliente;

    update Encomenda set ID_ESTADO = v_id_novo_Estado_encomenda where ENCOMENDA.ID_ENCOMENDA = p_id_encomenda;

    return v_creditos_novos;

exception
    when no_data_found then
         return 0;
end;