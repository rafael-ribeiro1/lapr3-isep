CREATE OR REPLACE FUNCTION get_user_com_email(p_email UTILIZADOR.EMAIL%TYPE)
return sys_refcursor
is
    v_email UTILIZADOR.EMAIL%TYPE;
    c_user sys_refcursor;
begin
select email into v_email
from utilizador where email=p_email;

open c_user for
    select ID_USER, USERNAME, PASSWORD, TIPO_USER.DESCRICAO, EMAIL from UTILIZADOR
                                                                     INNER JOIN TIPO_USER ON TIPO_USER.ID_TIPO=UTILIZADOR.TIPO_USER
where EMAIL=v_email;

return c_user;
exception
    when no_data_found then
        return null;
end;
