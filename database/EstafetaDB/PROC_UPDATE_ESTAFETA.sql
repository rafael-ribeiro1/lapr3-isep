create or replace procedure update_Estafeta( p_id_estafeta estafeta.id_estafeta%type, p_id_farmacia estafeta.id_farmacia%type,
                                             p_nome estafeta.nome%type, p_nif estafeta.nif%type, p_carga_maxima estafeta.carga_maxima%type, p_peso estafeta.peso%type)
AS
BEGIN

    UPDATE ESTAFETA SET id_farmacia=p_id_farmacia WHERE id_estafeta = p_id_estafeta;
    UPDATE ESTAFETA SET nome=p_nome WHERE id_estafeta = p_id_estafeta;
    UPDATE ESTAFETA SET carga_maxima=p_carga_maxima WHERE id_estafeta = p_id_estafeta;
    UPDATE ESTAFETA SET nif=p_nif WHERE id_estafeta = p_id_estafeta;
    UPDATE ESTAFETA SET peso=p_peso WHERE id_estafeta = p_id_estafeta;
END;
/