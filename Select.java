package syntaxe;

import java.util.Arrays;
import base.Relation;
import syntaxe.Delete;

public class Select extends Grammaire {

    public void setValue( Object[] value ) throws Exception {
        if ( value == null ){
            if( this instanceof Select == true )
                throw new Exception(" select invalide aucune colonne ");

            throw new Exception(" delete invalide ");
        }
        
        String[] str_value = transformString( value );
        
        if ( str_value[0].compareToIgnoreCase( "*" ) == 0 || this instanceof Delete == true){

            Relation rel = (Relation) this.next_gram.value[0] ;

            String[] temp = { "*" };

            this.value =  temp;
        }else{
            this.value = str_value;
        }
        
        this.previous_gram = null;

    }   

    public Relation action( String[] req , Relation new_r , String bdd) throws Exception {

        verifyNext_grammar( req[2] );

        String[] colonne = req[1].split(",");

        int i = 3;

        String tab = req[i];

        while ( include( syntaxe , tab ) == true ){
            tab = req[i++];
        }

        String[] table = { tab };

        this.next_gram.setValue(table);

        if ( req.length > 4 ){
            int reference = 4 ;
            // while ( include( syntaxe , req[reference] ) == true){
            while ( i < req.length ){
                System.out.println(" pour i : "+reference+" compare :  "+req[ reference ]+" :  "+req[ reference ].compareToIgnoreCase("where"));

                if ( req[ reference ].compareToIgnoreCase("diff") == 0 ){
                    this.next_gram.next_gram = new Diff( reference , req );

                    this.next_gram.next_gram.previous_gram = this.next_gram;

                    next_gram.bdd = bdd;

                    break ;
                }

                if ( req[ reference ].compareToIgnoreCase("join") == 0 ){
                    this.next_gram.next_gram = new Join( reference , req , new_r );

                    this.next_gram.next_gram.previous_gram = this.next_gram;

                    next_gram.bdd = bdd;

                    // Relation r = new Relation();

                    // new_r = this.next_gram.next_gram.action(req, r , bdd); 

                    break ;
                }

                if ( req[ reference ].compareToIgnoreCase("div") == 0 ){
                    this.next_gram.next_gram = new Div( reference , req , bdd );

                    this.next_gram.next_gram.previous_gram = this.next_gram;

                    break;
                }

                if ( req[ reference ].compareTo("as") == 0 ){
                    System.out.println(" ref :  as"+reference);

                    int j = reference + 1;

                    new_r.setNom( req[j] );
                    defaut.setNom(  req[j] );

                    reference += 1;
                }

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

        new_r = this.next_gram.requete( req , new_r );

        return new_r;
    }

    public void verifyNext_grammar( String next_val ) throws Exception {

        if ( next_val.compareToIgnoreCase("from") != 0 ){
            throw new Exception(" syntaxe invalide , from : "+next_val.compareToIgnoreCase("from"));
        }

        this.next_gram = new From();

        this.next_gram.previous_gram = this;

        next_gram.bdd = bdd;
    }


}
