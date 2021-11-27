create or replace procedure update_Farmacia( p_id_farmacia farmacia.id_farmacia%type, p_nome farmacia.nome%type,
                                             p_rua endereco.rua%type, p_num_porta endereco.num_porta%type, 
                                             p_cod_postal endereco.cod_postal%type, p_localidade endereco.localidade%type, p_pais endereco.pais%type,
                                             p_latitude endereco.latitude%type, p_longitude endereco.longitude%type, p_altitude endereco.altitude%type)
AS
v_id_endereco_farmacia farmacia.id_endereco%type;
BEGIN

SELECT id_endereco INTO v_id_endereco_farmacia FROM farmacia WHERE id_farmacia=p_id_farmacia;

        
        UPDATE FARMACIA SET nome= p_nome WHERE id_farmacia = p_id_farmacia;
        UPDATE ENDERECO SET rua= p_rua WHERE id_endereco=v_id_endereco_farmacia ;
        UPDATE ENDERECO SET num_porta= p_num_porta WHERE id_endereco=v_id_endereco_farmacia ;
        UPDATE ENDERECO SET cod_postal= p_cod_postal WHERE id_endereco=v_id_endereco_farmacia ;
        UPDATE ENDERECO SET localidade=p_localidade WHERE id_endereco=v_id_endereco_farmacia ;
        UPDATE ENDERECO SET pais=p_pais WHERE id_endereco=v_id_endereco_farmacia ;
        UPDATE ENDERECO SET latitude=p_latitude WHERE id_endereco=v_id_endereco_farmacia ;
        UPDATE ENDERECO SET longitude=p_longitude WHERE id_endereco=v_id_endereco_farmacia ;
        UPDATE ENDERECO SET altitude=p_altitude WHERE id_endereco=v_id_endereco_farmacia ;
        
        END;
        
