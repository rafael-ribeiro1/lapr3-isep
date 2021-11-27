create or replace procedure atualizarInformacaoCliente (p_id in cliente.id_cliente%type, p_nome in cliente.nome%type, p_rua in endereco.rua%type, p_porta in endereco.num_porta%type, 
p_codigo endereco.cod_postal%type, p_localidade in endereco.localidade%type, p_pais in endereco.pais%type, p_latitude in endereco.latitude%type, p_longitude in endereco.longitude%type,
p_altitude endereco.altitude%type)
is
l_id endereco.id_endereco%type;
begin
    select id_endereco into l_id
    from cliente
    where id_cliente = p_id;
    
    update cliente
    set nome = p_nome
    where id_cliente = p_id;
    
    update endereco
    set rua = p_rua,
        num_porta = p_porta,
        cod_postal = p_codigo,
        localidade = p_localidade,
        pais = p_pais,
        latitude = p_latitude,
        longitude = p_longitude,
        altitude = p_altitude
    where id_endereco = l_id;    
    
end;
/