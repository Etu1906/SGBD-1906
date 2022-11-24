import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class Serveur {
    public static void main(String[] args) {
        String message = "";
        try {
            ServerSocket serverSocket = new ServerSocket(8082);
            while(true){
                Socket client = serverSocket.accept();
                (new SocketThread(client, 100000 , serverSocket )).start();
            }
            //serverSocket.close();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }
        
    }
}
