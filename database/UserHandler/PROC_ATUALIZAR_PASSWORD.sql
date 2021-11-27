create or replace procedure proc_atualizarPassword (p_id utilizador.id_user%type, p_pass utilizador.password%type)
is
begin
   UPDATE utilizador SET password=p_pass WHERE id_user=p_id; 
end;
/
