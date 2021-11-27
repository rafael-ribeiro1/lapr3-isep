CREATE OR REPLACE PROCEDURE insert_produto_encomenda(p_id_produto produto_encomenda.id_produto%type, p_id_encomenda produto_encomenda.id_encomenda%type , p_quantidade produto_encomenda.QUANTIDADE%type)
AS
    v_stockAtual  PRODUTO_FARMACIA.STOCK%TYPE;
    v_stock_new produto_farmacia.stock%type;
    v_id_farmacia encomenda.id_farmacia%type ;
BEGIN

    Select ID_FARMACIA into v_id_farmacia
    from Encomenda where ID_ENCOMENDA=p_id_encomenda;

    SELECT STOCK Into v_stockAtual
    from PRODUTO_FARMACIA
    where ID_FARMACIA=v_id_farmacia and ID_PRODUTO=p_id_produto;

    v_stock_new:= v_stockAtual - p_quantidade;

    UPDATE PRODUTO_FARMACIA
    SET STOCK = v_stock_new
    where ID_PRODUTO=p_id_produto AND ID_FARMACIA=v_id_farmacia;

    insert into PRODUTO_ENCOMENDA(id_produto, id_encomenda, quantidade) values (p_id_produto,p_id_encomenda,p_quantidade);

end;