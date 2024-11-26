package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServidorPartidas{

    private static ServidorPartidas instancia;
    private BlockingQueue<Socket> jugagoresEspera = new LinkedBlockingQueue<Socket>();

    private ServidorPartidas(){}

    //Explicacion de porque tiene que ser synchronized:
    //
    public synchronized static ServidorPartidas getInstancia(){
        if(instancia == null){
            instancia = new ServidorPartidas();
        }
        return instancia;
    }

    public void gestionarMultijugador(Socket cliente, ObjectOutputStream oos, ObjectInputStream ois){
        try {
            //Introducimos al jugador a la cola
            jugagoresEspera.put(cliente);

            if(jugagoresEspera.size() >= 2){
                // Si la cola tiene 2 jugadores o más, cogemos 2
                Socket jugador1 = jugagoresEspera.take();
                Socket jugador2 = jugagoresEspera.take();

                // Creamos una partida para ellos
                Thread partida = new Thread(new Partida(jugador1, jugador2));
                partida.start();
            } else {
                oos.writeObject("Esperando a otro jugador...");
                oos.flush();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
