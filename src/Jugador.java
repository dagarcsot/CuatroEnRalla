package src;

import java.io.Serializable;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

public class Jugador implements Serializable {

    private String nombre;
    private int ficha; //Identifica al jugador con un numero (1 ó 2)
    private boolean haGanado; //Booleano para saber si ha ganado
    private int victorias;

    public Jugador(String nombre, int ficha) {
        this.nombre = nombre;
        this.ficha = ficha;
        this.haGanado = false;
        this.victorias = 0;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getFicha() {
        return this.ficha;
    }

    public boolean isGanado() {
        return this.haGanado;
    }

    public int getVictorias() {
        return this.victorias;
    }

    public void registrarVictorias(){
        this.haGanado = true;
        this.victorias++;
        System.out.println("¡Felicidades, "+ this.nombre + " has ganado la partida!");
    }

    public boolean puedeMover(int columna, int[][] tablero) {
        // Un jugador puede mover si la columna elegida no está llena
        return tablero[0][columna] == 0; // Si la celda superior está vacía
    }
}
