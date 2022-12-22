package socket;
import java.io.*;
import java.net.*;
import thread.SocketThread;
import thread.TraitementThread;
import donnees.Fichier;

public class Serveur {
    public static void main(String[] args) {
        String message = "";
        int port = 0;
        try {
            Fichier f = new Fichier(1);
            ServerSocket serverSocket = new ServerSocket(f.getPort());
            while(true){
                Socket client = serverSocket.accept();
                TraitementThread traitement = new TraitementThread( client , serverSocket );
                (new SocketThread(client , serverSocket,traitement)).start();
            }
            // serverSocket.close();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }
        
    }
}
