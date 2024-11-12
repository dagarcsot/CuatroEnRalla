package src;

public class Tablero {

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

    public int[][] getTablero() {
        return this.tablero;
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

}
