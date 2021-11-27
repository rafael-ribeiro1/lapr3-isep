CREATE OR REPLACE PROCEDURE insert_fatura(p_nif fatura.id_fatura%type,p_valor_total fatura.valor_total%type, p_id_encomenda fatura.id_encomenda%type)
AS
BEGIN

    insert into fatura(nif,valor_total,ID_ENCOMENDA) values (p_nif,p_valor_total,p_id_encomenda);

end;


