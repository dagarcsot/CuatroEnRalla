package src;

import java.util.Random;

public class JugadorIA extends Jugador{

    public JugadorIA(int ficha) {
        super("IA", ficha);
    }

    public int elegirColumna(Tablero tablero) {
        // Lógica para elegir una columna aleatoria (para la IA)
        Random rand = new Random();
        int columna;
        do {
            columna = rand.nextInt(7); // Elige un número entre 0 y 6 (columnas)
        } while (!puedeMover(columna, tablero.getTablero())); // Si la columna está llena, elige otra
        return columna;
    }

}
