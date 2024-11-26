package src;

import java.util.Scanner;

public class PartidaIA {

    private Tablero tablero;
    private JugadorIA IA;
    private Jugador jugador;
    private boolean partidaAcabada;
    private boolean turnoJugador; // true = jugador, false = IA
    private Scanner entrada = new Scanner(System.in);

    // Variables para guardar el estado de la partida (en memoria)
    private static boolean partidaGuardada;
    private static Tablero tableroGuardado;
    private static boolean turnoGuardado;
    //Necesito guardar los jugadores también porque la ficha se inicia a principio de la partida
    private static Jugador jugadorGuardado;
    private static JugadorIA jugadorGuardadoIA;

    public PartidaIA() {
        this.tablero = new Tablero();
        this.partidaAcabada = false;
    }

    // Método para comprobar si hay una partida a la mitad
    public void partida(){

        //Si hay una partida guardada comprobamos si quiere continuar
        if(partidaGuardada){

            int opcion = -1;

            do{

                System.out.println("\n ¿Desea continuar la partida que esta a la mitad?");
                System.out.println("1- Si, continuar la partida");
                System.out.println("2- No, empezar una nueva");

                try {
                    opcion = entrada.nextInt();
                } catch (NumberFormatException e) {
                    System.out.println("Opción inválida. Introduce un número entre 1 y 2.");
                }

                if (opcion < 1 || opcion > 2) {
                    System.out.println("El número introducido no es válido. Introduce un número entre 1 y 2.");
                }

            } while (opcion<1 || opcion>2);

            if(opcion == 1){
                //Si dice que si, seguimos con la partida
                continuarPartida();
            }
            if(opcion == 2){
                //Si dice que no, empezamos una nueva
                this.partidaAcabada = false;
                iniciarPartida();
            }

        } else {
            iniciarPartida();
        }
    }

    // Método para iniciar la partida
    public void iniciarPartida() {
        System.out.println("\n Bienvenido a la partida contra la IA");

        System.out.print("\n Introduzca el nombre con el que quiera jugar: ");
        String nombre = entrada.nextLine();

        int opcion = -1;

        do {
            System.out.println("\n Elige la opción que desees: ");
            System.out.println("1- Empezar IA");
            System.out.println("2- Empezar Tu");
            System.out.println("3- Salir");

            try {
                opcion = entrada.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Introduce un número entre 1 y 3.");
            }

            if (opcion < 1 || opcion > 3) {
                System.out.println("El número introducido no es válido. Introduce un número entre 1 y 3.");
            }

        } while (opcion < 1 || opcion > 3);



        switch (opcion) {
            case 1:
                // La IA comienza
                System.out.println("\n La IA comienza.");
                this.turnoJugador = false;
                this.IA = new JugadorIA(1);
                this.jugador = new Jugador(nombre,2);
                break;

            case 2:
                // El jugador comienza
                System.out.println("\n Tú comienzas.");
                this.turnoJugador = true;
                this.jugador = new Jugador(nombre,1);
                this.IA = new JugadorIA(2);
                break;

            case 3:
                // Salir de la partida
                System.out.println("\n Saliendo del juego...");
                return;
        }

        desarrolloPartida();
    }

    private void desarrolloPartida() {
        int salir = -1;
        // El juego comienza
        while (!this.partidaAcabada && salir != 1) {

            if (this.turnoJugador) {

                //Antes de comenzar el turno del jugador le preguntamos si quiere salir
                salir = deseaSalir();

                //Si hay un 1 es que quiere salir
                if (salir == 1) {
                    guardarPartida();
                } else {
                    //Si no hay un 1, hace el movimiento

                    // Mostrar el tablero
                    this.tablero.imprimirTablero();

                    // Es el turno del jugador
                    jugarTurnoJugador();

                    // Verificar si el jugador ha ganado después de su movimiento
                    if (this.tablero.verificarVictoria(this.jugador.getFicha())) {
                        System.out.println("\n ¡Felicidades, " + this.jugador.getNombre() + ", has ganado!");
                        this.partidaAcabada = true;
                        partidaGuardada = false;
                        break;
                    }
                }

            } else {
                // Es el turno de la IA
                jugarTurnoIA();

                // Verificar si la IA ha ganado después de su movimiento
                if (this.tablero.verificarVictoria(this.IA.getFicha())) {
                    System.out.println("\n La IA ha ganado. ¡Mejor suerte la próxima vez!");
                    this.partidaAcabada = true;
                    partidaGuardada = false;
                    break;
                }
            }

            // Verificar si hay empate
            if (this.tablero.tableroCompleto()) {
                System.out.println("\n ¡Empate! No quedan movimientos disponibles.");
                this.partidaAcabada = true;
                partidaGuardada = false;
                break;
            }
        }
    }




    // Método para el turno del jugador
    private void jugarTurnoJugador() {
        int columna;
        do {
            System.out.print("\n Jugador: " + jugador.getNombre() + ", elige una columna (0-6): ");
            columna = entrada.nextInt();

            if (!this.jugador.puedeMover(columna,this.tablero.getTablero())) {
                System.out.println("La columna está llena, elige otra.");
            }
        } while (!this.jugador.puedeMover(columna,this.tablero.getTablero()));

        // Realizar el movimiento del jugador
        this.jugador.mover(this.tablero,columna);

        // Cambiar turno
        turnoJugador = false;  // Después del turno del jugador, le toca a la IA
    }

    // Método para el turno de la IA
    private void jugarTurnoIA() {
        System.out.println("Es el turno de la IA...");
        int columna = this.IA.elegirColumna(this.tablero);
        this.IA.mover(this.tablero,columna);

        System.out.println("La IA ha jugado en la columna " + columna);

        // Cambiar turno
        turnoJugador = true;  // Después del turno de la IA, le toca al jugador
    }

    // Método para guardar el estado de la partida
    private void guardarPartida() {
        // Guardamos el estado actual de la partida en variables estáticas
        tableroGuardado = this.tablero;
        turnoGuardado = this.turnoJugador;
        jugadorGuardado = this.jugador;
        jugadorGuardadoIA = this.IA;
        partidaGuardada = true;
        System.out.println("\n Estado de la partida guardado.");
    }

    // Método para continuar una partida guardada
    private void continuarPartida() {
        if (tableroGuardado != null) {
            this.tablero = tableroGuardado;
            this.turnoJugador = turnoGuardado;
            this.jugador = jugadorGuardado;
            this.IA = jugadorGuardadoIA;
            System.out.println("\n Partida cargada exitosamente.");
            System.out.println(); // Linea de separacion
            //Ahora llamamos al desarrollo
            desarrolloPartida();
        } else {
            System.out.println("No hay una partida guardada.");
        }
    }

    // Método para preguntar si quiere salir
    private int deseaSalir() {
        int opcion = -1;
        do {
            System.out.println("\n ¿Desea salir de la partida?: ");
            System.out.println("1- Si");
            System.out.println("2- No");

            try {
                opcion = entrada.nextInt();
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Introduce un número entre 1 y 2.");
            }

            if (opcion < 1 || opcion > 2) {
                System.out.println("El número introducido no es válido. Introduce un número entre 1 y 2.");
            }

        } while (opcion < 1 || opcion > 2);

        return opcion;
    }
}
