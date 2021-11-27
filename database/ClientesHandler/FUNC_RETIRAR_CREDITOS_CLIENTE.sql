CREATE OR REPLACE FUNCTION retirar_creditos_cliente(p_id_cliente Cliente.id_cliente%type, p_creditos_retirar Cliente.creditos%type)
return integer
is
    v_creditos_atuais_cliente Cliente.creditos%type;
    nao_possui_creditos exception;
begin
    select creditos into v_creditos_atuais_cliente
    from cliente where ID_CLIENTE=p_id_cliente;

    if p_creditos_retirar<=0 OR p_creditos_retirar>v_creditos_atuais_cliente then
        raise nao_possui_creditos;
    end if;

    v_creditos_atuais_cliente:=v_creditos_atuais_cliente-p_creditos_retirar;

    update cliente set CREDITOS = v_creditos_atuais_cliente where ID_CLIENTE = p_id_cliente;

    return 1; -- returnar 1 vai ser true
exception
    when no_data_found then
        return 0;
    when  nao_possui_creditos then
        return 0;
end;