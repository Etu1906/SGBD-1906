package thread;

import java.io.*;
import java.net.*;
import java.util.*;

public class SendClientThread extends Thread {
    Socket client;
    String msg;
    Scanner sc;
    BufferedWriter out;
    public SendClientThread( Socket client , Scanner sc , BufferedWriter out , String msg )throws Exception{
        try{
            setClient(client);
            setSc(sc);
            setOut(out);
            setMsg(msg);
        }catch(  Exception e){
            throw e;
        }
        
    }

    public void send()throws Exception{                              //envoie de message
        try{
            while (true) {
                    msg = sc.nextLine();
                    out.write( msg );                                   //ecriture du msg 
                    out.newLine();
                    out.flush();                                        //envoie msg
                
            }
        }catch( SocketException e ){
            throw new SocketException(" au revoir ");
        }
    }

    @Override
    public void run() {
        super.run();
        try{
            send();
        }catch( SocketException e ){
            System.out.println( e.getMessage() );
        }catch( Exception e ){
            e.printStackTrace();
        }
    }
    

///getters et setters
    public Socket getClient() {
        return client;
    }
    public void setOut(BufferedWriter out) {
        this.out = out;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setSc(Scanner sc) {
        this.sc = sc;
    }
    public void setClient(Socket client) {
        this.client = client;
    }
}
