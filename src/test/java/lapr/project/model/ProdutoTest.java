package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProdutoTest {

    private final Produto produto1;
    private final Produto produto2;
    private final Produto produto3;
    private final Produto produto4;
    private final Produto produto5;

    public ProdutoTest() {
        produto1 = new Produto(1, "a", 10, 11.3, 0.5, "desc");
        produto2 = new Produto("b", 10.2, 11.5, 1.5, "desc");
        produto3 = new Produto("c", 1);
        produto4 = new Produto(5.5, "d");
        produto5 = new Produto(5, "e", 3);
    }

    @Test
    public void getIdTest() {
        Assertions.assertEquals(1, produto1.getId());
        Assertions.assertNotEquals(2, produto1.getId());
        Assertions.assertEquals(0, produto2.getId());
        Assertions.assertEquals(5, produto5.getId());
        Assertions.assertNotEquals(4, produto5.getId());
    }

    @Test
    public void getNomeTest() {
        Assertions.assertEquals("a", produto1.getNome());
        Assertions.assertNotEquals("c", produto1.getNome());
        Assertions.assertEquals("b", produto2.getNome());
        Assertions.assertEquals("c", produto3.getNome());
        Assertions.assertEquals("d", produto4.getNome());
        Assertions.assertEquals("e", produto5.getNome());
    }

    @Test
    public void getPeso() {
        Assertions.assertEquals(0.5, produto1.getPeso());
        Assertions.assertNotEquals(1, produto1.getPeso());
        Assertions.assertEquals(1.5, produto2.getPeso());
        Assertions.assertEquals(1, produto3.getPeso());
    }

    @Test
    public void getValorUnitario() {
        Assertions.assertEquals(10, produto1.getValorUnitario());
        Assertions.assertNotEquals(5, produto1.getValorUnitario());
        Assertions.assertEquals(10.2, produto2.getValorUnitario());
    }

    @Test
    public void getPrecoTest() {
        Assertions.assertEquals(11.3, produto1.getPreco());
        Assertions.assertNotEquals(10, produto1.getPreco());
        Assertions.assertEquals(11.5, produto2.getPreco());
        Assertions.assertEquals(5.5, produto4.getPreco());
        Assertions.assertEquals(3, produto5.getPreco());
    }

    @Test
    public void getDescricaoTest() {
        Assertions.assertEquals("desc", produto1.getDescricao());
        Assertions.assertNotEquals("a", produto1.getDescricao());
        Assertions.assertEquals("desc", produto2.getDescricao());
    }

    @Test
    public void setIdTest() {
        produto2.setId(6);
        Assertions.assertEquals(6, produto2.getId());
        Assertions.assertNotEquals(7, produto2.getId());
    }

    @Test
    public void setNomeTest() {
        produto5.setNome("e");
        Assertions.assertEquals("e", produto5.getNome());
        Assertions.assertNotEquals("a", produto5.getNome());
    }

    @Test
    public void setValorUnitarioTest() {
        produto3.setValorUnitario(5);
        Assertions.assertEquals(5, produto3.getValorUnitario());
        Assertions.assertNotEquals(2, produto3.getValorUnitario());
    }

    @Test
    public void setPrecoTest() {
        produto3.setPreco(8);
        Assertions.assertEquals(8, produto3.getPreco());
        Assertions.assertNotEquals(9, produto3.getPreco());
    }

    @Test
    public void setPesoTest() {
        produto4.setPeso(3);
        Assertions.assertEquals(3, produto4.getPeso());
        Assertions.assertNotEquals(1, produto4.getPeso());
    }

    @Test
    public void setDescricaoTest() {
        produto5.setDescricao("desc2");
        Assertions.assertEquals("desc2", produto5.getDescricao());
        Assertions.assertNotEquals("d", produto5.getDescricao());
    }

    @Test
    public void equalsTest() {
        Produto produto = produto1;
        Assertions.assertEquals(produto, produto1);
        Assertions.assertNotEquals(null, produto1);
        Assertions.assertNotEquals(produto1, new Farmacia(1));
        Assertions.assertEquals(produto, new Produto(1, "a", 10, 11.3, 0.5, "desc"));
        Assertions.assertNotEquals(produto, new Produto(2, "b", 2, 1.3, 1.5, "desc2"));
        Assertions.assertNotEquals(produto, new Produto(2, "c", 10, 11.3, 0.5, "desc"));
        Assertions.assertNotEquals(produto, new Produto(2, "a", 3, 11.3, 0.5, "desc")); 
        Assertions.assertNotEquals(produto, new Produto(2, "a", 10, 10, 0.5, "desc"));
        Assertions.assertNotEquals(produto, new Produto(2, "a", 10, 11.3, 2, "desc"));
        Assertions.assertNotEquals(produto, new Produto(2, "a", 10, 11.3, 0.5, "descA"));
    }

    @Test
    public void hashCodeTest() {
        Assertions.assertEquals(1186318932, produto1.hashCode());
        Assertions.assertNotEquals(1, produto1.hashCode());
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals(String.format("%s custa %.2f ", produto1.getNome(),
                produto1.getPreco()), produto1.toString());
        Assertions.assertNotEquals("a", produto1.toString());
    }

    @Test
    public void compareToTest() {
        Assertions.assertEquals(-1, produto1.compareTo(produto2));
        Assertions.assertNotEquals(1, produto1.compareTo(produto2));
    }

}
