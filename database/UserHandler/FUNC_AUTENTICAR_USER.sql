create or replace function autenticarUser(p_email utilizador.email%type, p_pw utilizador.password%type)
return int
is
    user utilizador.id_user%type;
begin

select ID_USER into user
from utilizador
where email = p_email
  and password = p_pw;

return 1;
exception
	when no_data_found then
		return 0;
end;