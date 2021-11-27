create or replace procedure removerPerfilCliente (p_id_estado cliente.id_estado%type,p_id cliente.id_cliente%type)
is
begin
    update cliente 
    set id_estado = p_id_estado
    where id_cliente = p_id; 
end;
/