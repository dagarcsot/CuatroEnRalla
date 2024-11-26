package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GestionCliente implements Runnable{

    private Socket cliente;

    public GestionCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {

        try (ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream())){

            int opcion;

            // Continuar hasta que el cliente decida salir
            while (true) {
                // Leer la opción que el cliente ha seleccionado
                opcion = (Integer) ois.readObject();

                if (opcion == 3) {
                    // El cliente eligió "Salir", cerramos la conexión
                    oos.writeObject("Gracias por jugar, cerrando conexión.");
                    oos.flush();
                    break; // Salir del bucle, lo que cerrará la conexión después
                }

                switch (opcion) {
                    case 1: { // Jugar contra la IA
                        oos.writeObject("\nIniciando partida contra la IA...");
                        oos.flush();
                        // Aquí iría la lógica para gestionar la partida contra la IA
                        break;
                    }
                    case 2: { // Modo multijugador
                        ServidorPartidas.getInstancia().gestionarMultijugador(cliente, oos, ois);
                        break;
                    }
                    default: {
                        oos.writeObject("Opción no válida. Elige una opción válida.");
                        oos.flush();
                        break;
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Solo cerramos la conexión si el cliente ha decidido salir
            try {
                if (!cliente.isClosed()) {
                    cliente.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
