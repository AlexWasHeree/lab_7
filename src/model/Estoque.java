import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Estoque {
    private final Map<String, Produto> produtos;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Estoque(Map<String, Produto> produtos) {
        this.produtos = produtos;
    }

    public boolean verificarDisponibilidade(Pedido pedido) {
        lock.readLock().lock();
        try {
            return pedido.getProdutos().stream()
                .allMatch(produto -> produtos.get(produto.getNome()).getQuantidade() >= produto.getQuantidade());
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean processarPedido(Pedido pedido) {
        lock.writeLock().lock();
        try {
            if (!verificarDisponibilidade(pedido)) return false;

            for (Produto pedidoProduto : pedido.getProdutos()) {
                Produto estoqueProduto = produtos.get(pedidoProduto.getNome());
                estoqueProduto.retirar(pedidoProduto.getQuantidade());
            }
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void reabastecer(String nomeProduto, int quantidade) {
        lock.writeLock().lock();
        try {
            Produto produto = produtos.get(nomeProduto);
            if (produto != null) {
                produto.adicionar(quantidade);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
