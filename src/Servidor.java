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
                Socket jugador1 = serverSocket.accept();
                System.out.println("Jugador 1 conectado.");
                Socket jugador2 = serverSocket.accept();
                System.out.println("Jugador 2 conectado.");

                //Creamos un nuevo hilo para manejar la partida entre los dos jugadores
                pool.execute(new Partida(jugador1, jugador2));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

}
