package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PUERTO = 6666;
    private static Scanner entrada = new Scanner(System.in);
    private static Jugador jugador;
    private static boolean partidaAcabada = false;

    public static void main(String[] args) {
        try(Socket cliente = new Socket(HOST, PUERTO)){
            System.out.println("Conectado al servidor en el puerto " + cliente.getLocalPort() + "\n");

            try (ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream())){

                    //Obtener nombre del jugador
                    System.out.println("¿Con que nombre quieres identificarte?");
                    String nombre = entrada.nextLine();
                    jugador = new Jugador(nombre,1);

                    while(!partidaAcabada){

                    }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
