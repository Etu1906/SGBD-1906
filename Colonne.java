package syntaxe;

import java.util.*;
import base.Relation;
public class Colonne extends Grammaire{
    
    static Vector<String> type = new Vector<String>() ;
    static Vector<String> col = new Vector<String>() ;

    int id;

    public Colonne( int id ){
        this.id = id;
    }
    //tsy aiko
    public Relation action( String[] req , Relation r ) throws Exception {
        type.add( req[ id + 2 ] );
        col.add( req[ id + 1 ] );

        System.out.println(" col : "+req[ id + 1 ]+" type : "+req[ id + 2 ]);

        id++;

        while ( Arrays.asList( syntaxe ).contains( req[ id + 2 ] ) == true ){
            id++;
        }

        System.out.println( " id : "+(id + 2)+" req[id] : "+req[id+2]+" " );

        if ( req[ id + 2 ].compareToIgnoreCase(",") == 0 ){

            this.next_gram = new Colonne(id + 2); 

            this.next_gram.bdd = this.bdd;

            next_gram.value = this.value;

            r = next_gram.action(req, r , bdd);
        }

        r = (Relation) this.value[0];

        Object[] title = col.toArray();

        String[] valiny = new String[ title.length ];

        for ( int i = 0 ; i != title.length ; i++ ){
            valiny[i] = String.valueOf(title[i]);
        }

        r.setEn_tete( valiny );

        return r;
    }
}
