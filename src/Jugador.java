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

    public int elegirColumna(int[][] tablero){
        Scanner sc = new Scanner(System.in);
        int columna = -1;

        do{
            do {
                System.out.println("Jugador: "+this.nombre+" (Ficha "+this.ficha+"), elige una columna: ");

                try{
                    String entrada = sc.nextLine();
                    columna = Integer.parseInt(entrada);

                    if(!(columna >= 0 && columna <= 6)){
                        System.out.println("El numero "+columna+" no es correcto. Esta fuera de rango.");
                        System.out.println("Introduce un numero entre 0 y 6.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("El numero de columna que has introducido no existe.");
                    System.out.println("Introduce un numero entre 0 y 6.");
                }

            }while (!(columna >= 0 && columna <= 6));

            if(!puedeMover(columna, tablero)){
                System.out.println("La columna introducida esta completa, elige otra.");
            }

        }while (!puedeMover(columna, tablero));

        return columna;
    }

    public void mover(int[][] tablero) {

        int columna = elegirColumna(tablero);
        int fila = tablero.length - 1; //fila más baja
        boolean encontrado = false;

        // Buscar la fila más baja disponible en la columna elegida
        while (fila >= 0) {
            if (tablero[fila][columna] == 0) { // Si la celda está vacía
                tablero[fila][columna] = this.ficha; // Coloca la ficha
                return; // Salir del método
            }
            fila--; // Subir una fila
        }

        // Si el bucle se completa sin encontrar una celda disponible
        System.out.println("La columna " + columna + " está llena. Intenta otra.");
        mover(tablero); // Solicitar otro movimiento
    }
}
