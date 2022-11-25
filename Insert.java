package syntaxe;

import java.security.cert.CertPath;
import java.util.*;

import javax.xml.catalog.Catalog;

import base.Relation;
import donnees.Fichier;

public class Insert extends Grammaire{
    
    String nom;     //nom table 
    Vector<String> colonne = new Vector<String>();
    Vector<String> values = new Vector<String>();

    public String findValue( String name){
        int i = colonne.indexOf(name);
        if( i == -1 )   return "null";

        return values.get(i);
    }

    public Object[] getValue( Relation r ){
        Object[] res = new Object[ r.getEn_tete().length];
        if( colonne.size() == 0 ){                  //si l'on a pas spécifié les colonnes
            return values.toArray();
        }

        for( int i = 0 ; i != res.length ; i++ ){
            res[i] = findValue( String.valueOf(r.getEn_tete()[i]) ); 
            System.out.println(" res["+i+"] :  "+res[i]);            
        }

        return res;
    }

    public void verifySyntaxe( String[] req )throws Exception{
        if( req[1].compareToIgnoreCase("in") != 0 ){
            throw new Exception( "syntaxe invalide sur in" );
        }
        int indice = 3;
        if( req[3].compareToIgnoreCase("col") == 0 ){
            while( req[indice].compareToIgnoreCase("values") != 0 ){
                indice++;
                if( indice>= req.length )   throw new Exception(" syntaxe invalide apres col ");
            }
        }
        while( req[indice].compareToIgnoreCase(";") != 0 ){
            if( req[indice].compareToIgnoreCase(",") == 0 ){
                if( req[indice - 2].compareToIgnoreCase("values") != 0 && req[indice - 2].compareToIgnoreCase(",") != 0 ){
                    throw new Exception( " une erreur pres de *"+req[indice - 2]);
                }
            }
            indice++;
            if( indice>= req.length )   throw new Exception(" syntaxe invalide apres values ");
        }   

        if( indice != req.length - 1 ) throw new Exception(" ; pas a la bonne place ");

    }

    public Relation action( String[] req , Relation r , String bdd ) throws Exception {          // insertio data

        try{
            verifySyntaxe(req);

            nom = req[2];
            int indice = 3;
            if( req[3].compareToIgnoreCase("col") == 0 ){                           //si l'on précise les colonnes
                while( req[indice].compareToIgnoreCase("values") != 0 ){
                    if( req[indice].compareToIgnoreCase(",") == 0 ){
                        colonne.add( req[indice - 1] );
                    }
                    indice++;
                }
                colonne.add( req[indice - 1] );
            }

            while( req[indice].compareToIgnoreCase(";") != 0 ){
                if( req[indice].compareToIgnoreCase(",") == 0 ){
                    values.add( req[indice - 1] );
                }
                indice++;
            }  
            values.add( req[indice - 1] );

            Fichier f = new Fichier( nom , bdd );

            Relation rel = f.getRelation( nom );                        //la relation concerné

            Object[] all_val = getValue( rel );                              //la valeur de toutes les colonnes 'null' si pas de valeur

            f.insertValue(all_val);

            throw new Exception(" insertion réussie ");

        }catch( Exception e ){
            throw e;
        }
    }


}
