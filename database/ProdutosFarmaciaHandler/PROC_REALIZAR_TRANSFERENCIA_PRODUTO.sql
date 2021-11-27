create or replace PROCEDURE realizar_transferencia_produto(p_id_farmacia_recetora Farmacia.id_farmacia%type , p_id_farmacia_fornecedora Farmacia.id_farmacia%type , p_id_produto PRODUTO.id_produto%type , p_quantidade produto_farmacia.stock%type ,p_id_Transporte meio_Transporte.id_transporte%type ,p_descricao Estado_encomenda.descricao%type)
as

    contador integer;
    v_stock_Adicionar produto_farmacia.stock%type;
    v_stock_final    produto_farmacia.stock%type;
    v_id_estado_encomenda estado_encomenda.id_Estado%type ;
begin


    SELECT count(ID_PRODUTO) into contador
    from PRODUTO_FARMACIA  where ID_PRODUTO = p_id_produto AND ID_FARMACIA = p_id_farmacia_recetora;

    if(contador = 0) then
        insert into PRODUTO_FARMACIA(id_produto, id_farmacia, stock) values (p_id_produto,p_id_farmacia_recetora,0);
    end if;

    select stock into v_stock_Adicionar
    from PRODUTO_FARMACIA where ID_PRODUTO = p_id_produto AND ID_FARMACIA = p_id_farmacia_recetora;

    v_stock_Adicionar:= p_quantidade-v_stock_Adicionar;

    UPDATE PRODUTO_FARMACIA set stock = p_quantidade where ID_PRODUTO = p_id_produto AND ID_FARMACIA = p_id_farmacia_recetora;

    select stock into v_stock_final
    from PRODUTO_FARMACIA where ID_PRODUTO = p_id_produto AND ID_FARMACIA = p_id_farmacia_fornecedora;

    v_stock_final:= v_stock_final - v_stock_Adicionar;

    UPDATE PRODUTO_FARMACIA set stock = v_stock_final where ID_PRODUTO = p_id_produto AND ID_FARMACIA = p_id_farmacia_fornecedora;

    select ID_ESTADO into v_id_estado_encomenda
    from ESTADO_ENCOMENDA where DESCRICAO=p_descricao;



    insert into TRANSFERENCIA_PRODUTO (ID_FORNECEDOR,ID_RECETOR,ID_PRODUTO,QUANTIDADE,ID_ESTADO,ID_TRANSPORTE) values (p_id_farmacia_fornecedora,p_id_farmacia_recetora,p_id_produto,p_quantidade,v_id_estado_encomenda,p_id_Transporte);



end;