package syntaxe;

import java.util.Arrays;
import donnees.Fichier;
import base.Relation;

public class Diff extends Grammaire{
    Relation r1;
    Relation r2;
    int index ;
    public Diff( int i , String[] req ) throws Exception {
        index = i;
    }


    public Relation action( String[] req , Relation r , String bdd ) throws Exception {       

        Fichier f2 = new Fichier( req[index+1] , bdd );
        r1 = defaut;

        r2 = f2.getRelation( req[index+1] );


        r = r1.difference(r2);

        return r;
    }
}
