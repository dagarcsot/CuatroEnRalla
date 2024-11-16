package src;

public class Prueba {
    public static void main(String[] args) {
        /*
        Tablero t = new Tablero();
        Jugador j1 = new Jugador("Ivan",1);
        Jugador j2 = new Jugador("Dani",2);
        int turno = 0;

        while(!t.tableroCompleto()) {
            t.imprimirTablero();

            if(turno == 0) {
                j1.mover(t.getTablero());
                turno = 1;
            } else if(turno == 1) {
                j2.mover(t.getTablero());
                turno = 0;
            }
        }

        if(j1.isGanado()){
            j1.registrarVictorias();
        }
        if(j2.isGanado()){
            j2.registrarVictorias();
        }

        System.out.println("Registro total de partidas ganadas: ");
        System.out.println(j1.getNombre()+" ha ganado "+j1.getVictorias()+" partidas");
        System.out.println(j2.getNombre()+" ha ganado "+j2.getVictorias()+" partidas");

         */

        int[][] tablero = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 0},
                {0, 2, 1, 1, 0, 0, 0},
                {0, 1, 2, 1, 0, 0, 0},
                {0, 2, 2, 2, 1, 0, 0}
        };

        Tablero t = new Tablero(tablero);

        System.out.println(t.verificarVictoria(1));
        System.out.println(t.verificarVictoria(2));

    }
}
