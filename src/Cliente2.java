package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {

    private static final String HOST = "localhost";
    private static final int PUERTO = 6666;
    private static Scanner entrada = new Scanner(System.in);
    private static boolean partidaAcabada = false;
    private static Tablero tablero = new Tablero();

    public static void main(String[] args) {
        try (Socket cliente = new Socket(HOST, PUERTO)) {
            System.out.println("Conectado al servidor en el puerto " + cliente.getLocalPort() + "\n");
            System.out.println("Bienvenido al juego del 4 en raya :)");

            try (ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream())) {

                int opcion;

                do {
                    mostrarMenu();

                    // Leer la opción del usuario
                    opcion = entrada.nextInt();
                    entrada.nextLine(); // Consumir el salto de línea

                    oos.writeObject(opcion);
                    oos.flush();

                    switch (opcion) {
                        case 1: {
                            String respuesta = (String) ois.readObject();
                            System.out.println(respuesta);
                            // Aquí se podría iniciar la partida contra la IA
                            break;
                        }
                        case 2: {
                            System.out.println((String) ois.readObject()); // Mensaje "Introduce tu nombre: "
                            String nombre = entrada.nextLine();
                            oos.writeObject(nombre);
                            oos.flush();

                            System.out.println((String) ois.readObject()); // Mensaje de estado

                            while (!partidaAcabada) {
                                String mensaje = (String) ois.readObject();
                                System.out.println(mensaje);

                                if (mensaje.startsWith("Espera")) {
                                    int fila = (Integer) ois.readObject();
                                    int columna = (Integer) ois.readObject();
                                    int ficha = (Integer) ois.readObject();

                                    tablero.getTablero()[fila][columna] = ficha;
                                    tablero.imprimirTablero();
                                } else if (mensaje.startsWith("¡Es tu turno!")) {
                                    tablero.imprimirTablero();
                                    int columna = elegirColumna(tablero);
                                    oos.writeObject(columna);
                                    oos.flush();
                                }

                                mensaje = (String) ois.readObject();
                                System.out.println(mensaje);

                                if (mensaje.startsWith("¡Has ganado!") || mensaje.startsWith("Has perdido.")) {
                                    partidaAcabada = true;
                                    break;
                                }
                            }
                            System.out.println("Partida finalizada.");
                            break;
                        }
                        case 3: {
                            System.out.println("\nGracias por jugar al 4 en raya.");
                            break;
                        }
                        default: {
                            System.out.println("Opción no válida. Intenta de nuevo.");
                        }
                    }
                } while (opcion != 3);

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n Opciones de juego: ");
        System.out.println("1.- Jugar contra la IA");
        System.out.println("2.- Jugar contra otro jugador online");
        System.out.println("3.- Salir del juego");
        System.out.print("Elige la opción que deseas: ");
    }

    private static int elegirColumna(Tablero tablero) {
        int columna;
        do {
            System.out.print("Elige una columna (0-6): ");
            columna = entrada.nextInt();
            entrada.nextLine(); // Consumir salto de línea
            if (columna < 0 || columna > 6 || tablero.getTablero()[0][columna] != 0) {
                System.out.println("Columna inválida o llena. Intenta de nuevo.");
                columna = -1;
            }
        } while (columna == -1);
        return columna;
    }
}
