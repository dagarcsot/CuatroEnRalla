package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Partida implements Runnable{

    private Socket jugador1;
    private Socket jugador2;

    public Partida(Socket j1, Socket j2){
        this.jugador1 = j1;
        this.jugador2 = j2;
    }

    @Override
    public void run() {

        try(ObjectOutputStream oos1 = new ObjectOutputStream(jugador1.getOutputStream());
            ObjectOutputStream oos2 = new ObjectOutputStream(jugador2.getOutputStream());
            ObjectInputStream ois1 = new ObjectInputStream(jugador1.getInputStream());
            ObjectInputStream ois2 = new ObjectInputStream(jugador2.getInputStream())){

            Tablero tablero = new Tablero();
            boolean partidaAcabada = false;

            while(!partidaAcabada){
                //Empieza la partida

            }

        } catch (IOException e) {
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
