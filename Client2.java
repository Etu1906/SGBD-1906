package socket;

import java.io.*;
import java.net.*;
import thread.SendClientThread;

import base.Relation;
import syntaxe.Grammaire;
import syntaxe.As;
import java.io.*;
import donnees.Fichier;
import java.util.*;

public class Client2 extends Socket{
         
    BufferedWriter out; 

    String msg;

    Scanner sc;



    public Client2( String host , int port )throws Exception{
        super( host , port );
    }

    public void connectToServer()throws Exception{                                              //connection avec le seveur
        try {
            // flux pour envoyer
            out = new BufferedWriter( new OutputStreamWriter( this.getOutputStream() ) ) ;

            InputStream is = this.getInputStream(); 

            sc = new Scanner(System.in);// pour lire à partir du clavier

            SendClientThread thread = new SendClientThread(this , sc , out , msg );

            thread.start();

            Thread recevoir = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        while( true ){
                        
                            ObjectInputStream message = new ObjectInputStream(is);

                            try {
                                Object obj  = message.readObject(); 
                                System.out.println("instance : "+obj.getClass().getSimpleName());
                                if ( obj instanceof Relation == true ){
                                    Relation r = (Relation) obj;
        
                                    System.out.println(" tete : "+Arrays.toString(r.getEn_tete()));
        
                                    r.printObj(r);
                                }
                                else if( obj instanceof Object[][][] == true ){

                                    Object[][][] val = ( Object[][][] ) obj;


                                        System.out.println(" title : "+val[0][0][0]);
                                        System.out.println(Arrays.toString(val[1][0]));
                                        for( int i = 0 ; i != val[2].length ; i++ ){
                                            System.out.println( Arrays.toString( val[2][i] ) );
                                        }
                                }else{
                                    System.out.println(" retour : "+obj);
                                }

                            }catch( EOFException e ){
                                e.printStackTrace();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recevoir.start();

        } catch (UnknownHostException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

///getters et setters
    public BufferedWriter getOut() {
        return out;
    }
    public Scanner getSc() {
        return sc;
    }

    public static void main(String[] args) {
        try {
            Client2 client = new Client2("localhost", 8082);

            client.connectToServer();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }  
        catch (IOException e) {
            e.printStackTrace();
        }
         catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
