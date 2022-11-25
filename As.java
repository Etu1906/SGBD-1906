package syntaxe;

import java.util.*;
import base.Relation;
import donnees.Fichier;
public class As extends Grammaire{
    String rel;
    String as;

    public As( String[] req , int id , String bdd ) throws Exception {
        try{
            action(req , id , bdd);
        }catch( Exception e ){
            throw e;
        }
    }

    public static void getAllAs( String[] req , String bdd )throws Exception{
        try {
            int i = 0 ;
            while( i != req.length ){
                if ( req[ i ].compareTo("AS") == 0 ){
                    As as = new As( req , i , bdd );

                }
                i++;
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }

    public void exist( Vector<As> v )throws Exception{                            //vérifie si l'alias n'existe déja pas
        for ( int i = 0 ; i != v.size() ; i++ ){
            System.out.println(" aluas : "+v.get(i)+"  "+this.as);
            if ( v.get(i).as.compareToIgnoreCase( this.as ) == 0 ){
                throw new Exception( " l'alias "+this.as+" existe deja " );
            }
        }
    }

    public void action( String[] req , int id , String bdd ) throws Exception {
        try {   
            String table = req[id - 1];
            String alias = req[id + 1];

            Fichier f = new Fichier(table , bdd);             //prendre le fichier correspondant

            f.getRelation(table).setAlias(alias);           //donner l'alias

            this.rel = table;
            this.as = alias;

            exist(l_as);
            l_as.add( this );
            System.out.println(" list vect  ");
            for ( int i = 0 ; i != l_as.size() ; i++ ){
                System.out.println(" l["+i+"] : "+l_as.get(i).getAs()+" et "+l_as.get(i).getRel());
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }

    public String getAs() {
        return as;
    }
    public String getRel() {
        return rel;
    }

    // select * from rel1 AS r join hoho AS h on r.chien = h.chien

}
