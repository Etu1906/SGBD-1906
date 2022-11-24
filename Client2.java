import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2{
    public static void main(String[] args) {
        try {
            Socket serveur = new Socket("localhost", 8082);
            BufferedReader azo =  new BufferedReader(new InputStreamReader(serveur.getInputStream()));
            for (String string : azo.lines().toList()) {
                System.out.println(string);
            }

            OutputStream os = serveur.getOutputStream();                         //send message au client
            ObjectOutputStream sendMessage = new ObjectOutputStream(os);            //pour l'envoie de l'objet

            Object obj  = "Vola beeeeee!";                                   //objet a envoyé

            sendMessage.writeObject(obj);                                       //envoie de l'obj

            



        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
