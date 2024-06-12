package org.example.socket;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedSocketServer {

    private ServerSocket serverSocket;
    private ExecutorService pool;  // Usando um pool de threads para gerenciar as threads

    public MultiThreadedSocketServer(int port, int maxConnections) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(maxConnections);  // Criando um pool de threads com um número fixo de threads
    }

    public void startServer() {
        System.out.println("Servidor iniciado. Aguardando conexões...");
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();  // Aceitando uma nova conexão de cliente
                System.out.println("Conexão aceita de " + clientSocket.getRemoteSocketAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                pool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 22222;
        int maxConnections = 10;
        try {
            MultiThreadedSocketServer server = new MultiThreadedSocketServer(port, maxConnections);
            server.startServer();
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

