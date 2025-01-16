package br.edu.iftm.tspi.sd.trabalho_niveis_isolamentos.util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class SimuladorRequisicoes {

    private static final String ENDPOINT_SEM_LOCK = "http://localhost:8080/pedido/novo";
    private static final String ENDPOINT_LOCK_OTIMISTA = "http://localhost:8080/pedido/otimista/novo";
    private static final String ENDPOINT_LOCK_PESSIMISTA = "http://localhost:8080/pedido/pessimista/novo";
    private static final String OUTPUT_PATH = "docs/resultados_simulacao.txt";

    public static void main(String[] args) throws Exception {
        int[] requisicoes = {100, 1000, 10000};

        for (int qtdRequisicoes : requisicoes) {
            System.out.println("Simulando " + qtdRequisicoes + " requisições simultâneas...");

            // Realizar simulação para cada endpoint
            realizarSimulacao(ENDPOINT_SEM_LOCK, "Sem Lock", qtdRequisicoes);
            realizarSimulacao(ENDPOINT_LOCK_OTIMISTA, "Lock Otimista", qtdRequisicoes);
            realizarSimulacao(ENDPOINT_LOCK_PESSIMISTA, "Lock Pessimista", qtdRequisicoes);
        }
    }

    private static void realizarSimulacao(String endpoint, String tipoLock, int qtdRequisicoes) throws Exception {
        AtomicInteger sucesso = new AtomicInteger(0);
        AtomicInteger conflito = new AtomicInteger(0);
        AtomicInteger outrosErros = new AtomicInteger(0);

        Instant inicioSimulacao = Instant.now();

        Thread[] threads = new Thread[qtdRequisicoes];
        for (int i = 0; i < qtdRequisicoes; i++) {
            threads[i] = new Thread(() -> {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Corpo da requisição para o produto "PlayStation 5"
                    String jsonInput = "{ \"produtoId\": 3, \"quantidade\": 1, \"clienteId\": \"C001\" }";

                    try (var os = connection.getOutputStream()) {
                        os.write(jsonInput.getBytes());
                        os.flush();
                    }

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        sucesso.incrementAndGet();
                    } else if (responseCode == 409) {
                        conflito.incrementAndGet();
                    } else {
                        outrosErros.incrementAndGet();
                    }
                } catch (Exception e) {
                    outrosErros.incrementAndGet();
                    e.printStackTrace();
                }
            });
        }

        // Iniciar todas as threads
        Instant inicio = Instant.now();
        for (Thread thread : threads) {
            thread.start();
        }

        // Esperar todas as threads finalizarem
        for (Thread thread : threads) {
            thread.join();
        }
        Instant fim = Instant.now();

        // Calcular estatísticas
        Duration duracaoTotal = Duration.between(inicioSimulacao, Instant.now());
        Duration duracaoRequisicoes = Duration.between(inicio, fim);
        double tempoMedio = (double) duracaoRequisicoes.toMillis() / qtdRequisicoes;

        // Salvar resultados no arquivo
        salvarResultados(
                tipoLock, qtdRequisicoes, sucesso.get(), conflito.get(), outrosErros.get(),
                duracaoRequisicoes.toMillis(), duracaoTotal.toMillis(), tempoMedio
        );
    }

    private static void salvarResultados(
            String tipoLock, int qtdRequisicoes, int sucesso, int conflito, int outrosErros,
            long duracaoRequisicoes, long duracaoTotal, double tempoMedio
    ) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_PATH, true))) {
            writer.println("====================================================");
            writer.println("Simulação: " + tipoLock);
            writer.println("Requisições Enviadas: " + qtdRequisicoes);
            writer.println("Requisições Bem-Sucedidas (200): " + sucesso);
            writer.println("Conflitos (409): " + conflito);
            writer.println("Outros Erros: " + outrosErros);
            writer.println("Tempo Total de Requisições (ms): " + duracaoRequisicoes);
            writer.println("Tempo Total de Simulação (ms): " + duracaoTotal);
            writer.println("Tempo Médio por Requisição (ms): " + tempoMedio);
            writer.println("====================================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
