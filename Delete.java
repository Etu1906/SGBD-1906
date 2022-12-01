package syntaxe;

import java.util.Arrays;
import base.Relation;
import donnees.Fichier;

public class Delete extends Select{
    public void getNewRelation( String table  , Relation new_r)throws Exception{
        try{
            Fichier f = new Fichier(table , bdd);

            f.DeleteAction(new_r);
        }catch( Exception e ){
            throw e;
        }
    }


    public Relation action( String[] req , Relation new_r , String bdd) throws Exception {

        try{
            verifyNext_grammar( req[1] );

            String[] colonne = req[1].split(",");

            int i = 2;

            String tab = req[i];

            while ( include( syntaxe , tab ) == true ){
                tab = req[i++];
            }

            String[] table = { tab };

            this.next_gram.setValue(table);

            if ( req.length > 3 ){
                int reference = 3 ;
                while ( i < req.length ){
                    System.out.println(" pour i : "+reference+" compare :  "+req[ reference ]+" :  "+req[ reference ].compareToIgnoreCase("where"));

                    if ( req[ reference ].compareToIgnoreCase("where") == 0 ){

                        System.out.println(" ref :  where"+reference);
                        this.next_gram.next_gram = new Where( reference , req);
        
                        this.next_gram.next_gram.previous_gram = this.next_gram;
        
                        next_gram.bdd = bdd;

                        Where where = (Where) this.next_gram.next_gram;
        
                        new_r = (Relation) this.next_gram.value[0];
        
                        new_r = where.action(req, new_r , bdd );

                        break;
        
                    }

                    reference++;

                    if ( reference >= req.length )  break;
                }

            }

            setValue(colonne);

            new_r = this.next_gram.requete( req );

            new_r = defaut.difference(new_r);

            getNewRelation(table[0], new_r);

            return new_r;
        }catch( Exception e ){
            throw e;
        }

    }

}
