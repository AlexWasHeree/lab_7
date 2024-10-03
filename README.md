
# Sistema de Processamento de Pedidos - Loja de E-commerce

Este projeto implementa um sistema de processamento de pedidos para uma loja de e-commerce, com foco em concorrência e controle de acesso seguro aos dados compartilhados. Ele simula múltiplos clientes fazendo pedidos simultâneos e workers (threads) processando esses pedidos.

## Passos para Rodar o Projeto

### 1. Usando o Terminal (sem IDE)

#### Compilar o Projeto

1. Navegue até a pasta raiz do projeto `ecommerce-system`.
2. Execute o seguinte comando para compilar todos os arquivos Java:

   ```bash
   javac -d build src/model/*.java src/service/*.java src/Main.java
   ```

   Este comando compila todos os arquivos na pasta `build`.

#### Executar o Projeto

Após compilar, execute o projeto com o comando abaixo:

```bash
java -cp build Main
```

