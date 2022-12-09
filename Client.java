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

public class Client extends Socket {
    BufferedWriter out; 

    String msg;

    Scanner sc;

    boolean bye = false;

    public Client( String host , int port )throws Exception{
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
                                if ( obj instanceof Relation == true ){
                                    Relation r = (Relation) obj;
        
                                    System.out.println(" tete : "+Arrays.toString(r.getEn_tete()));
        
                                    r.printObj(r);
                                }
                                else if( obj.getClass().getSimpleName().compareToIgnoreCase("Object[]") == 0 ){
                                    Object[] show = ( Object[] ) obj;
                                    for( int i = 0 ; i != show.length ; i++ ){
                                        System.out.println(show[i]);
                                    }
                                }
                                else if( obj.getClass().getSimpleName().compareToIgnoreCase("Object[][][]") == 0 ){

                                    Object[][][] val = ( Object[][][] ) obj;

                                    Relation r =Relation.ObjectToRelation(val);
                                    if( r.getNom() != null && r.getEn_tete().length != 0  ){
                                        System.out.println(" title : "+r.getNom());
                                        System.out.println(Arrays.toString(r.getEn_tete()));
                                        System.out.println(Arrays.toString(r.getType()));
                                        for( int i = 0 ; i != val[3].length ; i++ ){
                                            System.out.println( Arrays.toString( r.getValue()[i] ) );
                                        }
                                    }else{
                                        System.out.println(" aucun résultat ");
                                    }
                                }
                                else if( obj instanceof Exception ){
                                    System.out.println( ((Exception)obj).getMessage() );
                                }
                                else if( obj instanceof String == true ){
                                    if( String.valueOf( obj ).compareToIgnoreCase("bye!!!") == 0 ){
                                        System.out.println(" cliquez sur entrée ");
                                        thread.interrupt();
                                        bye = true;
                                        return;
                                    }
                                }
                                else{
                                    System.out.println(obj);
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
            recevoir.run();

            if( this.bye == true ){
                recevoir.interrupt();
                thread.interrupt();
                this.close();
                return ;
            }

        }catch( SocketException e ){
            throw e;
        } 
         catch (UnknownHostException e) {
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
        String host = "localhost";
        try {
            Client client = new Client(host, 8082);

            client.connectToServer();

        }catch( SocketException e ){
            System.out.println(" n'a pas pu se connecter au serveur : Connection refused\n");
            System.out.println("Le serveur est-il actlif sur l'hote ' "+host+" ' ");
        } 
        catch (UnknownHostException e) {
            e.printStackTrace();
        }  
        catch (IOException e) {
            e.printStackTrace();
        }
         catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

}
