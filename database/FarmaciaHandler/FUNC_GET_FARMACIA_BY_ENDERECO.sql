create or replace function return_Farmacia_mais_perto(p_rua endereco.rua%type,p_num_porta endereco.num_porta%type,p_codigo_postal endereco.cod_postal%type,p_localidade endereco.localidade%type,p_pais endereco.pais%TYPE,p_latitude endereco.latitude%TYPE,p_longitude endereco.longitude%type,p_altitude endereco.altitude%type)
    RETURN SYS_REFCURSOR
AS
    c_Farmacia sys_refcursor;
    v_id_endereco farmacia.id_farmacia%type;

BEGIN
    select ID_ENDERECO into v_id_endereco
    from ENDERECO where rua = p_rua and NUM_PORTA = p_num_porta and COD_POSTAL = p_codigo_postal and LOCALIDADE = p_localidade and PAIS = p_pais and LATITUDE = p_latitude and LONGITUDE = p_longitude and ALTITUDE = p_altitude;


    open c_Farmacia for
        select f.ID_FARMACIA,f.NOME, e.id_endereco, rua, num_porta, cod_postal, localidade, pais, latitude, longitude, altitude  from FARMACIA f
                                                                                                                                          inner join ENDERECO e on f.ID_ENDERECO = e.ID_ENDERECO
        where e.ID_ENDERECO = v_id_endereco;

    return  c_Farmacia;
exception
    when no_Data_found then
        Raise_Application_Error(-20001,'Farmacia nao registada');
end;
/

