import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SistemaProcessamento {
    private final BlockingQueue<Pedido> filaPedidos = new ArrayBlockingQueue<>(10);
    private final Estoque estoque;
    private int pedidosProcessados = 0;
    private int pedidosRejeitados = 0;
    private double totalVendas = 0.0;

    private final ReentrantReadWriteLock relatorioLock = new ReentrantReadWriteLock();

    public SistemaProcessamento(Estoque estoque) {
        this.estoque = estoque;
    }

    public void fazerPedido(Pedido pedido) throws InterruptedException {
        filaPedidos.put(pedido);
    }

    public void processarPedidos() {
        while (true) {
            try {
                Pedido pedido = filaPedidos.take();
                boolean sucesso = estoque.processarPedido(pedido);
                relatorioLock.writeLock().lock();
                try {
                    if (sucesso) {
                        pedidosProcessados++;
                        totalVendas += pedido.getTotal();
                    } else {
                        pedidosRejeitados++;
                    }
                } finally {
                    relatorioLock.writeLock().unlock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void gerarRelatorio() {
        relatorioLock.readLock().lock();
        try {
            System.out.println("Relatório:");
            System.out.println("Pedidos processados: " + pedidosProcessados);
            System.out.println("Total de vendas: " + totalVendas);
            System.out.println("Pedidos rejeitados: " + pedidosRejeitados);
        } finally {
            relatorioLock.readLock().unlock();
        }
    }
}
