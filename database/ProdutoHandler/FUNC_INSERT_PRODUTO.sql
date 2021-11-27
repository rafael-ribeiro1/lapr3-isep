create or replace function insert_produto(p_id out produto.id_produto%type,p_nome produto.nome%type,p_valor_unitario produto.valor_unitario%type,
p_preco produto.preco%type,p_peso produto.peso%type,p_descricao produto.descricao%TYPE) return produto.id_produto%type
AS

BEGIN
    -- Inserir produto
    insert into produto(nome,valor_unitario,preco,peso,descricao) values (p_nome,p_valor_unitario,p_preco,p_peso,p_descricao);
   
    -- Obter id do produto inserido
    select max(id_produto) into p_id
        from produto;
    return p_id;
EXCEPTION
    when no_data_found then
        return -1;
END;

 