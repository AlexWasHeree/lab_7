import java.util.List;

public class Pedido {
    private final List<Produto> produtos;

    public Pedido(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public double getTotal() {
        return produtos.stream().mapToDouble(p -> p.getPreco() * p.getQuantidade()).sum();
    }
}
