import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        try {
            Socket serveur = new Socket("localhost", 8082);
            BufferedReader azo =  new BufferedReader(new InputStreamReader(serveur.getInputStream()));
            for (String string : azo.lines().toList()) {
                System.out.println(string);
            }
            serveur.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
