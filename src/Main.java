import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Estoque estoque = new Estoque(Map.of(
            "Produto1", new Produto("Produto1", 10.0, 100),
            "Produto2", new Produto("Produto2", 20.0, 50)
        ));

        SistemaProcessamento sistema = new SistemaProcessamento(estoque);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        scheduler.execute(sistema::processarPedidos);

        scheduler.scheduleAtFixedRate(() -> {
            estoque.reabastecer("Produto1", 10);
            estoque.reabastecer("Produto2", 5);
            System.out.println("Estoque reabastecido.");
        }, 0, 10, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(sistema::gerarRelatorio, 0, 30, TimeUnit.SECONDS);

        sistema.fazerPedido(new Pedido(List.of(new Produto("Produto1", 10.0, 10))));
        sistema.fazerPedido(new Pedido(List.of(new Produto("Produto2", 20.0, 5))));
    }
}
