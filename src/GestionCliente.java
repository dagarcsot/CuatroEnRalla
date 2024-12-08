package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GestionCliente implements Runnable {

    private static Socket jugadorEnEspera; // Variable estática para almacenar al jugador en espera
    private Socket cliente;

    public GestionCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream())) {

            int opcion;

            // Continuar hasta que el cliente decida salir
            while (true) {
                // Leer la opción seleccionada por el cliente
                opcion = (Integer) ois.readObject();

                if (opcion == 3) {
                    // Cliente elige salir
                    oos.writeObject("Gracias por jugar, cerrando conexión.");
                    oos.flush();
                    break;
                }

                switch (opcion) {
                    case 1: { // Jugar contra la IA
                        oos.writeObject("\nIniciando partida contra la IA...");
                        oos.flush();
                        // Aquí se podría implementar la lógica contra la IA
                        break;
                    }
                    case 2: { // Jugar contra otro jugador
                        oos.writeObject("Introduce tu nombre: ");
                        oos.flush();

                        String nombre = (String) ois.readObject();
                        System.out.println("Jugador conectado: " + nombre);

                        synchronized (GestionCliente.class) {
                            if (jugadorEnEspera == null) {
                                // Si no hay jugador en espera, este cliente queda en espera
                                jugadorEnEspera = cliente;
                                oos.writeObject("Esperando a otro jugador...");
                                oos.flush();
                            } else {
                                // Si hay jugador en espera, se inicia una partida
                                Socket jugador1 = jugadorEnEspera;
                                jugadorEnEspera = null;

                                new Thread(new Partida(jugador1, cliente)).start();
                                oos.writeObject("Jugador encontrado. ¡Comienza la partida!");
                                oos.flush();
                            }
                        }
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
            // Cerrar conexión si no está cerrada
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
