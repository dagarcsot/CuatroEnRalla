package src;

public class Tablero {

    private int[][] tablero; //tiene 7 filas y 6 columnas
    private static final int FILAS = 7;
    private static final int COLUMNAS = 6;

    //Post: crea el tablero vacio
    public Tablero(){
        this.tablero = new int[FILAS][COLUMNAS];
        for(int i=0;i<FILAS;i++){
            for(int j=0;j<COLUMNAS;j++){
                this.tablero[i][j] = 0;
            }
        }
    }

    public void imprimirTablero() {
        for(int i=0;i<FILAS;i++){
            for(int j=0;j<COLUMNAS;j++){
                System.out.println(tablero[i][j]+" ");
            }
            System.out.println();
        }
    }

}
