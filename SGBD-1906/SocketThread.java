package thread;

import java.io.*;
import java.net.*;

import thread.TraitementThread;
import java.util.Arrays;
import base.Relation;
import syntaxe.Grammaire;
import syntaxe.As;
import java.io.*;
import java.util.Arrays;
import donnees.Fichier;
import java.util.Scanner;
import java.util.*;


public class SocketThread extends Thread{
    Socket client;
    ServerSocket serveur;
    TraitementThread traitement;

    public SocketThread(Socket client, ServerSocket serveur,TraitementThread traitement ) {
        this.setServeur(serveur);   
        this.setClient(client);
        this.setTraitement(traitement);
    }

    public static void setMessage( Object obj , Socket client )throws Exception{
        OutputStream os = client.getOutputStream();                         //send message au client

        ObjectOutputStream message = new ObjectOutputStream(os);            //pour l'envoie de l'objet

        message.writeObject(obj);                                           

    }
    
    public void getMessage()throws Exception{
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String msg = "";

        String bdd = "";
        while( true ){
            msg = in.readLine();                                                        //récupérer la ligne écrite par le client
            traitement.setReq(msg);                                                     //donner la requete au traitement                                    
            System.out.println(" bdd use : "+bdd);  
            try{
                Object obj = traitement.traitement();                                                //traiter la requete
                setMessage(obj, client);                                                               //envoie du message au client
            }catch( Exception e ){
                setMessage(e, client);
            }
            System.out.println(" client : "+msg);
        }
    }

    public void action()throws Exception{                                           //send et receive
        try {
            System.out.println("accepted : " + client.getInetAddress().getHostName() + " =/ " + client);
            try{
                getMessage();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("finished resp for " + this.client.getInetAddress().getHostName());
            client.close();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void run() {
        try {
            action();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }
        
    }

///getters et setters 
    public Socket getClient() {
        return client;
    }
    public void setTraitement(TraitementThread traitement) {
        this.traitement = traitement;
    }

    public void setClient(Socket client) {
        this.client = client;
    }
    public void setServeur(ServerSocket serveur) {
        this.serveur = serveur;
    }
}
