package socket;
import java.io.*;
import java.net.*;
import thread.SocketThread;
import thread.TraitementThread;


public class Serveur {
    public static void main(String[] args) {
        String message = "";
        try {
            ServerSocket serverSocket = new ServerSocket(8082);
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
