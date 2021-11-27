create or replace function obterTipoUser(p_email utilizador.email%type)
return tipo_user.descricao%type
is
    ret tipo_user.descricao%type;
begin
    select tu.descricao into ret
    from utilizador u
    inner join tipo_user tu on tu.ID_TIPO = u.tipo_user
    where u.email = p_email;
    return ret;
end;