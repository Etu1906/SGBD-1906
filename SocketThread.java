import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

public class SocketThread extends Thread{
    long timeout;
    Socket client;
    ServerSocket serveur;
    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }
    public void setServeur(ServerSocket serveur) {
        this.serveur = serveur;
    }

    public SocketThread(Socket client, long timeout , ServerSocket serveur) {
        this.setServeur(serveur);   
        this.setTimeout(timeout);
        this.setClient(client);
    }
    
    @Override
    public void run() {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            System.out.println("accepted : " + client.getInetAddress().getHostName() + " =/ " + client);
            out.write("HTTP/2 200 ");
            //out.close();
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                for (String string : in.lines().toList()) {
                    System.out.println("dsdsd");
                    System.out.println(string);
                }
                //in.close();
            }catch(Exception e){
                //System.out.println(e.getMessage());
                System.out.println(e.getMessage());
            }
                
            System.out.println("finished resp for " + this.client.getInetAddress().getHostName());
            client.close();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }
        
    }
}
