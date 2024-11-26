package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private static final String HOST = "localhost";
    private static final int PUERTO = 6666;
    private static Scanner entrada = new Scanner(System.in);
    private static boolean partidaAcabada = false;
    private static Tablero tablero = new Tablero();

    public static void main(String[] args) {
        try (Socket cliente = new Socket(HOST, PUERTO)) {
            System.out.println("Bienvenido al juego del 4 en raya :)");
            System.out.println("Conectado al servidor en el puerto " + cliente.getLocalPort() + "\n");

            try (ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream())) {

                int opcion = -1;

                do{

                    do{
                        //Mandamos menu
                        System.out.println("Opciones de juego: ");
                        System.out.println("1.- Jugar contra la IA");
                        System.out.println("2.- Jugar contra otro jugador online");
                        System.out.println("3.- Salir del juego");
                        System.out.print("Elige la opcion que quiera escoger: ");

                        try{
                            //Recibimos el numero
                            opcion = entrada.nextInt();
                        } catch (NumberFormatException e){
                            System.out.println();//linea de espacio
                            System.out.println("Opcion invalida");
                            System.out.println("Introduce un numero entre 1 y 3.");
                            System.out.println();//linea de espacio
                        }

                        //Mensaje de aviso de que ese numero no vale
                        if(opcion<1 || opcion>3){
                            System.out.println();//linea de espacio
                            System.out.println("El numero introducido no es valido.");
                            System.out.println("Introduce un numero entre 1 y 3.");
                            System.out.println();//linea de espacio
                        }

                    }while(opcion<1 || opcion>3); //Mientras no sea el numero que queremos

                    //Cuando acabamos las comprobaciones le mandamos la opcion al servidor
                    oos.writeObject(opcion);
                    oos.flush();

                    //Entramos en la opcion que sea
                    switch (opcion) {
                        case 1:{
                            //Implementar la logica
                            break;
                        }
                        case 2:{ //Modo multijugador

                            // Obtener nombre del jugador
                            System.out.print("Introduce tu nombre: ");
                            String nombre = entrada.nextLine();
                            oos.writeObject(nombre);
                            oos.flush();

                            // Recibir ficha
                            int ficha = (Integer) ois.readObject();
                            Jugador jugador = new Jugador(nombre, ficha);

                            if(opcion==2){
                                // Mandamos mensaje de que estamos esperando
                                System.out.println("Esperando al otro jugador...");
                            }

                            while (!partidaAcabada) {

                                // Esperamos el mensaje de que es nuestro turno
                                String mensaje = (String) ois.readObject();
                                System.out.println(mensaje);

                                if (mensaje.startsWith("Espera")) {
                                    // Si no es nuestro turno, actualizamos los datos
                                    // Recibimos la actualización del otro lado
                                    int fila = (Integer) ois.readObject();
                                    int columna = (Integer) ois.readObject();
                                    ficha = (Integer) ois.readObject();

                                    tablero.getTablero()[fila][columna] = ficha;

                                } else if (mensaje.startsWith("¡Es tu turno!")) {
                                    // Mostramos el tablero
                                    tablero.imprimirTablero();

                                    // Escogemos fila y columna
                                    int columna = elegirColumna(tablero, jugador);
                                    int fila = mover(tablero, jugador, columna);
                                    oos.writeObject(columna);
                                    oos.writeObject(fila);
                                    oos.flush();
                                }

                                // Recibimos un mensaje
                                mensaje = (String) ois.readObject();

                                // Si se ha acabado la partida
                                if (mensaje.startsWith("¡Has ganado!") || mensaje.startsWith("Has perdido.")) {
                                    partidaAcabada = true;
                                    System.out.println(mensaje);
                                    break;//salimos del bucle para evitar más lecturas
                                }

                                System.out.println(mensaje);
                            }
                            //Mensaje avisar de que se ha acabado la partida
                            System.out.println("Partida acabada");
                            break;
                        }
                        case 3:{
                            System.out.println("Gracias por jugar al 4 en ralla");
                            System.out.println("Vuelva pronto :)");
                            break;
                        }

                    }

                }while(opcion!=3); //Mientras la opción no sea salir (3), seguimos
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Métodos para elegir en qué posición ponemos la ficha

    public static int elegirColumna(Tablero tablero, Jugador jugador) {
        int columna = -1;

        do {
            do {
                System.out.print("Jugador: " + jugador.getNombre() + " (Ficha " + jugador.getFicha() + "), elige una columna (0-6): ");
                try {
                    String s = entrada.nextLine();
                    columna = Integer.parseInt(s);

                    if (!(columna >= 0 && columna <= 6)) {
                        System.out.println("El número " + columna + " no es correcto. Está fuera de rango.");
                        System.out.println("Introduce un número entre 0 y 6.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("El número de columna que has introducido no existe.");
                    System.out.println("Introduce un número entre 0 y 6.");
                }

            } while (!(columna >= 0 && columna <= 6));

            if (!puedoMover(columna, tablero)) {
                System.out.println("La columna introducida está completa, elige otra.");
            }

        } while (!puedoMover(columna, tablero));

        return columna;
    }

    public static int mover(Tablero tablero, Jugador jugador, int columna) {

        int fila = tablero.getTablero().length - 1; // fila más baja

        // Buscar la fila más baja disponible en la columna elegida
        while (fila >= 0) {
            if (tablero.getTablero()[fila][columna] == 0) { // Si la celda está vacía
                tablero.getTablero()[fila][columna] = jugador.getFicha(); // Coloca la ficha
                return fila;
            }
            fila--; // Subir una fila
        }

        // Si está lleno devuelve -1
        return -1;
    }

    public static boolean puedoMover(int columna, Tablero tablero) {
        // Un jugador puede mover si la columna elegida no está llena
        return tablero.getTablero()[0][columna] == 0; // Si la celda superior está vacía
    }
}
