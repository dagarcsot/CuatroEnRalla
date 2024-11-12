package src;

public class Prueba {
    public static void main(String[] args) {
        Tablero t = new Tablero();
        Jugador j = new Jugador("Ivan",1);
        j.mover(t.getTablero());
        t.imprimirTablero();
    }
}
