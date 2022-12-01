package syntaxe;

import java.util.*;
import base.Relation;
import donnees.Fichier;

public class Update extends Delete{
    Vector<Vector> update = new Vector<Vector>();
    String nom_table = "";

    String[] syntaxe = { "update" , "set" , "where" };

    public boolean In( String val , String[] array ){
        for( int i = 0 ; i != array.length ; i++ ){
            if( val.compareToIgnoreCase(array[i]) == 0){
                return true;
            }
        }
        return false;
    }


    public void verifySyntax( String[] req )throws Exception{
        if( req[2].compareToIgnoreCase("set") != 0 ){
            throw new Exception(" erreur pres de *set ");
        }

        int indice  = 2;

        while( req[indice].compareToIgnoreCase("where") != 0 ){
            if( req[indice].compareToIgnoreCase("=") == 0 ){
                if(  In(req[indice - 1], syntaxe ) == true || In(req[indice + 1], syntaxe ) == true ){
                    throw new Exception(" une erreur pres de  *= ");
                }
            }
            indice++;
        }
    }

    public Relation action( String[] req , Relation new_r , String bdd) throws Exception {
        try {
            verifySyntax(req);
            int indice = 0;

            while ( req[indice].compareToIgnoreCase("set") != 0 ){
                indice++;
            }

            nom_table = req[indice - 1];

///set ...
            while( req[indice].compareToIgnoreCase("where") != 0 ){
                if( req[indice].compareToIgnoreCase("=") == 0 ){
                    Vector v = new Vector();

                    v.add( req[indice -1] );                            //nom de la colonne

                    String value= req[indice + 1];
                    System.out.println(" value for update "+value);
                    String val = value.substring(1);
                    val = val.substring(0, val.length()-1);         //enlever les cotes

                    v.add( val );                                           //valeur colonne

                    update.add(v);
                }
                indice++;
                if( indice >= req.length ) break;
            }

            Fichier f = new Fichier( nom_table , bdd);                  //récuperer la relation a modifier

            new_r = f.getRelation(nom_table);           //par défaut , si pas de where la relation a modifier est la valeur par défaut

            this.value = new Object[1];                                           

            this.value[0] = f.getRelation(nom_table);                           //donner la relation de départ

///apres where
            while ( indice < req.length ){
                System.out.println(" pour i : "+indice+" compare :  "+req[ indice ]+" :  "+req[ indice ].compareToIgnoreCase("where"));

                if ( req[ indice ].compareToIgnoreCase("where") == 0 ){

                    System.out.println(" ref :  where"+indice);
                    this.next_gram = new Where( indice , req);
    
                    this.next_gram.previous_gram = this;
    
                    next_gram.bdd = bdd;

                    defaut = (Relation) this.value[0];

                    Where where = (Where) this.next_gram;
    
                    new_r = (Relation) this.value[0];
    
                    new_r = where.action(req, new_r , bdd );

                    break;
    
                }

                indice++;

                if ( indice >= req.length )  break;
            }

            Object[][] upd = Relation.ObjectToVector(update);               

            Relation defaut = f.getRelation(nom_table);                                                 //la relation de départ

            int nbr_colonne_modif = new_r.getValue().length;                       

            new_r = defaut.update(new_r, upd);

            getNewRelation( nom_table , new_r);                 //création de la nouvelle relation ( écraser l'ancien )

            throw new Exception( "colonne "+nbr_colonne_modif+" modifiées " );

        } catch (Exception e) {
            throw e;
        }
    }
}
