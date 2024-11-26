package src;

import java.io.Serializable;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

public class Jugador implements Serializable {

    private String nombre;
    private int ficha; //Identifica al jugador con un numero (1 ó 2)

    public Jugador(String nombre, int ficha) {
        this.nombre = nombre;
        this.ficha = ficha;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getFicha() {
        return this.ficha;
    }


    public void registrarVictorias(){
        System.out.println("¡Felicidades, "+ this.nombre + " has ganado la partida!");
    }

    public boolean puedeMover(int columna, int[][] tablero) {
        // Un jugador puede mover si la columna elegida no está llena
        return tablero[0][columna] == 0; // Si la celda superior está vacía
    }
    public void mover(Tablero tablero, int columna) {

        int fila = tablero.getTablero().length - 1; // fila más baja
        boolean colocado = false;

        // Buscar la fila más baja disponible en la columna elegida
        while (fila >= 0 && !colocado) {
            if (tablero.getTablero()[fila][columna] == 0) { // Si la celda está vacía
                tablero.getTablero()[fila][columna] = this.ficha; // Coloca la ficha
                colocado = true; //hemos colocado la ficha
            }
            fila--; // Subir una fila
        }
    }


}
