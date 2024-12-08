package src;

import java.io.Serializable;

public class Tablero implements Serializable {

    private int[][] tablero; //tiene 6 filas y 7 columnas
    private static final int FILAS = 6;
    private static final int COLUMNAS = 7;

    //Post: crea el tablero vacio
    //Tener en cuenta que la posicion [0][Columna] es la más alta del tablero y la [5][Columna] la más baja
    public Tablero(){
        this.tablero = new int[FILAS][COLUMNAS];
        for(int i=0;i<FILAS;i++){
            for(int j=0;j<COLUMNAS;j++){
                this.tablero[i][j] = 0;
            }
        }
    }

    public Tablero(int[][] tablero){
        this.tablero = tablero;
    }

    public int[][] getTablero() {
        return this.tablero;
    }

    public int encontrarFilaDisponible(int columna) {
        for (int fila = tablero.length - 1; fila >= 0; fila--) {
            if (tablero[fila][columna] == 0) { // 0 representa una celda vacía
                return fila;
            }
        }
        return -1; // Devuelve -1 si la columna está llena
    }

    public boolean tableroLleno() {
        for (int columna = 0; columna < tablero[0].length; columna++) {
            if (tablero[0][columna] == 0) { // Si la celda superior de una columna está vacía
                return false;
            }
        }
        return true; // Todas las columnas están llenas
    }

    //Post: dibuja el tablero del 4 en ralla
    public void imprimirTablero() {
        for (int i = 0; i < FILAS; i++) {
            System.out.print("|"); // Borde izquierdo
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(tablero[i][j] + "|"); // Borde derecho
            }
            System.out.println();
        }
        System.out.println("-".repeat(COLUMNAS * 2+1)); // Línea separadora al final
    }

    public boolean tableroCompleto(){
        for(int i=0;i<FILAS;i++){
            for(int j=0;j<COLUMNAS;j++){
                if(this.tablero[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verificarVictoria(int ficha) {
        // Comprobar horizontal
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS - 3; j++) { // Aseguramos que j + 3 no se salga del tablero
                if (tablero[i][j] == ficha &&
                        tablero[i][j + 1] == ficha &&
                        tablero[i][j + 2] == ficha &&
                        tablero[i][j + 3] == ficha) {
                    return true;
                }
            }
        }

        // Comprobar vertical
        for (int i = 0; i < FILAS - 3; i++) { // Aseguramos que i + 3 no se salga del tablero
            for (int j = 0; j < COLUMNAS; j++) {
                if (tablero[i][j] == ficha &&
                        tablero[i + 1][j] == ficha &&
                        tablero[i + 2][j] == ficha &&
                        tablero[i + 3][j] == ficha) {
                    return true;
                }
            }
        }

        // Comprobar diagonal ascendente (\)
        for (int i = 3; i < FILAS; i++) { // Aseguramos que i - 3 no se salga del tablero
            for (int j = 0; j < COLUMNAS - 3; j++) { // Aseguramos que j + 3 no se salga del tablero
                if (tablero[i][j] == ficha &&
                        tablero[i - 1][j + 1] == ficha &&
                        tablero[i - 2][j + 2] == ficha &&
                        tablero[i - 3][j + 3] == ficha) {
                    return true;
                }
            }
        }

        // Comprobar diagonal descendente (/)
        for (int i = 0; i < FILAS - 3; i++) { // Aseguramos que i + 3 no se salga del tablero
            for (int j = 0; j < COLUMNAS - 3; j++) { // Aseguramos que j + 3 no se salga del tablero
                if (tablero[i][j] == ficha &&
                        tablero[i + 1][j + 1] == ficha &&
                        tablero[i + 2][j + 2] == ficha &&
                        tablero[i + 3][j + 3] == ficha) {
                    return true;
                }
            }
        }

        return false; // No se encontró un 4 en raya
    }

}
