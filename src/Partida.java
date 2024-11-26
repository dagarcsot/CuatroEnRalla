package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Partida implements Runnable{

    private Socket jugador1, jugador2;

    public Partida(Socket j1, Socket j2){
        this.jugador1 = j1;
        this.jugador2 = j2;
    }

    @Override
    public void run() {

        try(ObjectOutputStream salidaJugador1 = new ObjectOutputStream(jugador1.getOutputStream());
            ObjectOutputStream salidaJugador2 = new ObjectOutputStream(jugador2.getOutputStream());
            ObjectInputStream entradaJugador1 = new ObjectInputStream(jugador1.getInputStream());
            ObjectInputStream entradaJugador2 = new ObjectInputStream(jugador2.getInputStream())){

            // Leemos los nombres de los jugadores
            String nombreJugador1 = (String) entradaJugador1.readObject();
            String nombreJugador2 = (String) entradaJugador2.readObject();

            // Creamos los jugadores de la partida
            Jugador j1 = new Jugador(nombreJugador1, 1);
            Jugador j2 = new Jugador(nombreJugador2, 2);

            // Enviamos las fichas de cada jugador
            salidaJugador1.writeObject(j1.getFicha());
            salidaJugador2.writeObject(j2.getFicha());

            // Creamos el tablero para la partida
            Tablero tablero = new Tablero();
            boolean partidaAcabada = false;

            // Creamos los turnos
            ObjectOutputStream turnoActual = salidaJugador1;
            ObjectInputStream entradaActual = entradaJugador1;
            ObjectOutputStream siguienteTurno = salidaJugador2;
            ObjectInputStream siguienteEntrada = entradaJugador2;

            while (!partidaAcabada) {
                // Informar al jugador que es su turno
                turnoActual.writeObject("¡Es tu turno!");
                siguienteTurno.writeObject("Espera tu turno.");

                // Recibimos el movimiento
                int columna = (Integer) entradaActual.readObject();
                int fila = (Integer) entradaActual.readObject();
                int ficha = (turnoActual == salidaJugador1) ? j1.getFicha() : j2.getFicha();

                // Asignamos la decisión a nuestro tablero
                tablero.getTablero()[fila][columna] = ficha;

                // Actualizamos el tablero en el otro lado
                siguienteTurno.writeObject(fila);
                siguienteTurno.writeObject(columna);
                siguienteTurno.writeObject(ficha);
                siguienteTurno.flush();

                // Verificamos victoria
                if (tablero.verificarVictoria(ficha)) {
                    partidaAcabada = true;
                    turnoActual.writeObject("¡Has ganado!");
                    siguienteTurno.writeObject("Has perdido.");
                } else {
                    turnoActual.writeObject("Movimiento realizado. Espera tu turno.");
                    siguienteTurno.writeObject("¡Te toca mover!");
                }

                // Confirmamos que hemos mandado el mensaje
                turnoActual.flush();
                siguienteTurno.flush();

                // Cambiamos los turnos
                ObjectOutputStream temp = turnoActual;
                turnoActual = siguienteTurno;
                siguienteTurno = temp;

                ObjectInputStream tempIn = entradaActual;
                entradaActual = siguienteEntrada;
                siguienteEntrada = tempIn;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                jugador1.close();
                jugador2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
