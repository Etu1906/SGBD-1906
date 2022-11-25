package syntaxe;

import java.util.*;
import base.Relation;
import donnees.Fichier;
public class Create extends Grammaire {

    Vector<String> colonne = new Vector<String>();
    String nom;

    public Create(){
        Relation[] r = new Relation[1];

        r[0] = new Relation();

        this.value = r ;
    }

    public Relation action( String[] req , Relation r , String bdd ) throws Exception {          // cration table  tsy aiko raha efa vita
        System.out.println(" creation bdd ");

        if( req.length <= 1 ){                                              //create doit contenir au moins 3 élément
            throw new Exception( " rien apres create " );
        }
        if( req[1].compareToIgnoreCase("database") == 0 ){              
                Fichier.createDb( req[2] );
                throw new Exception( "base de données "+req[2]+" créée " );
        }

        if( req[1].compareToIgnoreCase("table") == 0 ){
            int indice = 1;
            while ( req[indice].compareToIgnoreCase("as") != 0 ){                    //tant que mbola tsy (
                indice++;
            }

            nom = req[indice - 1];                                                          //nom de table avant (

            while( req[indice].compareToIgnoreCase(";") != 0 ){
                if( req[indice].compareToIgnoreCase(",") == 0 ){
                    if( req[indice - 1].compareToIgnoreCase("String") != 0 ){                   //vérification type
                        throw new Exception("  type doit etre String ");
                    }
                    colonne.add( req[indice - 2] );                                             //récupérer colonne i-e : indice num2 avant ,
                }
                indice++;
            }
            if( req[indice - 1].compareToIgnoreCase("String") != 0 ){
                throw new Exception("  type doit etre String ");
            }
            colonne.add( req[indice - 2] );

            Fichier.createTable(colonne, bdd , nom );                                   //creation de la table

            throw new Exception( "table "+nom+" créée " );

        }

        return r;
    }

    public void action( String[] req ) throws Exception {                   //creation de la bdd
        try{
            System.out.println( " miditra " );
            if ( req[1].compareToIgnoreCase("database") == 0   ){
                Fichier.createDb( req[2] );
            }
        }catch( Exception e ){
            throw e;
        }

    }

}
