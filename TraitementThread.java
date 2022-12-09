package thread;
import java.util.Arrays;
import base.Relation;
import syntaxe.Grammaire;
import syntaxe.Show;
import syntaxe.As;
import java.io.*;
import java.util.Arrays;
import donnees.Fichier;
import java.util.*;
import java.io.*;
import java.net.*;
public class TraitementThread{
    String bdd;                                 //Bdd pour chq client
    String req;                                 
    String req_tempo = "";
    Socket client;
    ServerSocket serveur;

    Vector<Relation> l_r = new Vector<Relation>();

    public TraitementThread( Socket client , ServerSocket serveur ){
        setClient(client);
        setServeur(serveur);
    }


    public Object traitement()throws Exception{
        try{

            Relation new_r = new Relation();

            System.out.println(" bdd use : "+bdd);
            System.out.println(" req :  "+req+" equals : "+req.contains("save"));
            if( req.compareToIgnoreCase("bye") == 0 ){
                String bye = "bye!!!";
                System.out.println(bye);
                return bye;
            }
            if ( req.contains("use") == true ){
                String[] reqSplit = req.split(" ");
                reqSplit = Grammaire.trim(reqSplit);

                System.out.println("split : "+reqSplit[1]);

                bdd = Fichier.findDbb( reqSplit[1] , bdd );

                Fichier f = new Fichier(bdd);
                f.setBdd(bdd);
                
                Grammaire g = new Grammaire();

                System.out.println("bdd : "+bdd);

                return "base de données choisit";
            }else if ( req.contains("save") == true ){
                String[] reqSplit = req.split(" ");
                reqSplit = Grammaire.trim(reqSplit);

                System.out.println("split : "+reqSplit[1]);

                Relation r_save = Relation.findRelation(l_r, reqSplit[1]);

                Fichier new_f = new Fichier( r_save.getNom() , bdd) ;
                
                new_f.insertTable(r_save);
                
                return "relation sauvegardé";
            }
            else if( req.contains("show") == true ){
                String[] reqSplit = req.split(" ");
                reqSplit = Grammaire.trim(reqSplit);

                Show show = new Show();

                return show.Show(reqSplit, bdd);
            }
            else{
                System.out.println(" bdd1 : "+bdd);

                if( bdd == null ){
                    System.out.println(" bdd est null ");
                    return " aucune base de données " ;
                }
                if(  bdd.compareToIgnoreCase("") == 0 ){
                    return " aucune base de données " ;
                }
                try{
                    Grammaire g = new Grammaire( bdd );

                    String[] verification = Grammaire.verifyReqEnd(req);        //vérfication de la requete 

                    if( verification[0].compareToIgnoreCase("end") != 0 ){       //ce n'est pas encore une vraie req
                        req_tempo = req_tempo + "  " + verification[1];
                        throw new Exception("requete pas terminée");
                    }

                    req = req_tempo + "  " + req;

                    req_tempo = "";

                    g = Grammaire.syntaxAnalysis(req , bdd);

                    System.out.println(" bdd :  "+g.getBdd());

                    new_r = g.getRelation(req );

                    System.out.println(" title : "+new_r.getNom());
                    new_r.printObj(new_r);

                    g.setL_as( new Vector<As>() );

                    l_r.add( new_r );

                    Object[][][] obj = new Object[ 4 ][ 2 ][ new_r.getValue().length + 1 ]; // pour l'en tete donc + 1

                    System.out.println( "last title : "+new_r.getNom() );
                    System.out.println( " en tete : "+Arrays.toString(new_r.getEn_tete()) );

                    obj[0][0][0] = new_r.getNom();
                    obj[1][0] = new_r.getEn_tete();
                    obj[2][0] = new_r.getType(); 
                    obj[3] = new_r.getValue();

                    return obj;
                }catch( Exception e ){
                    e.printStackTrace();
                    System.err.println(e);
                    throw e;
                }
            }

        }catch( Exception e ){
            throw e;
        }
    }

///getters et setters
    public void setClient(Socket client) {
        this.client = client;
    }
    public void setServeur(ServerSocket serveur) {
        this.serveur = serveur;
    }
    public void setReq(String req) {
        this.req = req;
    }
}
