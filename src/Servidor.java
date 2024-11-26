package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    private static final int PUERTO = 6666;
    private static ExecutorService pool = Executors.newCachedThreadPool(); // hilos para manejar partidas

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)){
            System.out.println("Servidor iniciado. Esperando jugadores...\n");

            while (true){

                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + cliente.getInetAddress());
                pool.execute(new GestionCliente(cliente)); //creamos un hilo en cada cliente
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

}
