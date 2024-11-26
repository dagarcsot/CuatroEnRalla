package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GestionCliente implements Runnable{

    private Socket cliente;

    public GestionCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {

        try (ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream())){

            //Leer la opcion que el cliente ha seleccionado
            int opcion = (Integer) ois.readObject();

            switch(opcion){
                case 1:{ //Jugar contra la IA
                    oos.writeObject("Modo contra la máquina no está gestionado por el servidor. Cerrando conexión.");
                    oos.flush();
                    break;
                }
                case 2:{ //Modo multijugador
                    ServidorPartidas.getInstancia().gestionarMultijugador(cliente, oos, ois);
                    break;
                }
                default:{
                    oos.writeObject("Opción no válida. Cerrando conexión.");
                    oos.flush();
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                cliente.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
