package syntaxe;

import java.util.*;
import base.Relation;
import donnees.Fichier;
public class Create extends Grammaire {

    Vector<String> colonne = new Vector<String>();
    Vector<String> type = new Vector<String>();
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

            if( req[ indice - 2 ].compareToIgnoreCase("table") != 0 ){
                throw new Exception(" erreur de syntaxe pres de *table ");
            }
            nom = req[indice - 1];                                                          //nom de table avant (

            while( req[indice].compareToIgnoreCase("end") != 0 ){
                System.out.println(" indice : "+req[indice]);
                if( req[indice].compareToIgnoreCase(",") == 0 ){
                    if( req[indice - 3].compareToIgnoreCase("as") != 0 && req[indice - 3].compareToIgnoreCase(",") != 0 ){
                        throw new Exception(" erreur de syntaxe pres de *"+req[indice - 3] );            //autre  chose que 
                    }
                    if(  Relation.includeType( req[indice - 1] ) == -1 ){                   //vérification type
                        throw new Exception("  type incorrecte ");
                    }
                    type.add( req[indice - 1] );
                    colonne.add( req[indice - 2] );                                             //récupérer colonne i-e : indice num2 avant ,
                }
                indice++;
            }
            if( req[indice - 3].compareToIgnoreCase("as") != 0 && req[indice - 3].compareToIgnoreCase(",") != 0 ){
                throw new Exception(" erreur de syntaxe pres de *"+req[indice - 3]);
            }
            if(  Relation.includeType( req[indice - 1] ) == -1 ){                   //vérification type
                throw new Exception("  type incorrecte ");
            }

            type.add( req[indice - 1] );
            colonne.add( req[indice - 2] );

            Relation.distinct( colonne );                                               //vérifier si toute les colonnes sont distincts

            Fichier.createTable(colonne, type  ,bdd , nom );                                   //creation de la table

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
