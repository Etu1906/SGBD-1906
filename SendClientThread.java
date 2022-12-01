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
        while (true) {
            // System.out.print(">");
            msg = sc.nextLine();
            if(  msg.compareToIgnoreCase("exit") == 0 ){
                break;
            }
            out.write( msg );                                   //ecriture du msg 
            out.newLine();
            out.flush();                                        //envoie msg
        }
    }

    @Override
    public void run() {
        super.run();
        try{
            send();
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
