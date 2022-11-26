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

    String[] syntaxe = { "into" , "col" , ";" , "," , "values" };
    public String findValueByIndex( String name){             //retrouver la valeur grace a l'index de la valeur            
        int i = colonne.indexOf(name);
        if( i == -1 )   return "null";                      //si elle n'est pas précisée

        return values.get(i);
    }

    public Object[] getFilterValue( Relation r ){           //relié les valeurs et les colonnes demandées  ex : insert into huhu ( *lala ) values ( *hoho );       
        Object[] res = new Object[ r.getEn_tete().length];
        if( colonne.size() == 0 ){                  //si l'on a pas spécifié les colonnes
            return values.toArray();
        }

        for( int i = 0 ; i != res.length ; i++ ){
            res[i] = findValueByIndex( String.valueOf(r.getEn_tete()[i]) ); 
            System.out.println(" res["+i+"] :  "+res[i]);            
        }

        return res;
    }


///vérification syntaxe
    void verifyInto( String[] req)throws Exception{
        if( req[1].compareToIgnoreCase("into") != 0 ){
            throw new Exception( "syntaxe invalide sur in" );
        }
    }

    int verifyColAndValues( String[] req , int indice )throws Exception{
        if( req[3].compareToIgnoreCase("col") == 0 ){
            while( req[indice].compareToIgnoreCase("values") != 0 ){
                indice++;
                if( indice>= req.length )   throw new Exception(" syntaxe invalide apres col ");
            }
        }
        return indice;
    }

    int verifyAfterValues( String[] req , int indice )throws Exception{
        while( req[indice].compareToIgnoreCase(syntaxe[2]) != 0  ){

            if( req[indice].charAt( req[indice].length() - 1 ) == ';' ){
                break;
            }
            if( req[indice].compareToIgnoreCase(syntaxe[3]) == 0 ){
                if( req[indice - 2].compareToIgnoreCase(syntaxe[4]) != 0 && req[indice - 2].compareToIgnoreCase(syntaxe[3]) != 0 ){
                    throw new Exception( " une erreur pres de *"+req[indice - 2]);
                }
            }
            indice++;
            if( indice>= req.length )   throw new Exception(" syntaxe invalide apres values ");
        }  
        return indice; 
    }

    public void verifySyntaxe( String[] req )throws Exception{

        try{
            verifyInto(req);

            int indice = 3;

            indice = verifyColAndValues(req, indice);
            
            indice = verifyAfterValues(req, indice);  

            if( indice != req.length - 1 ) throw new Exception(" ; pas a la bonne place ");
        }catch( Exception e ){
            throw e;
        }

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

            String req_tempo = req[indice];
            while( req[indice].compareToIgnoreCase(";") != 0 ){
                if( req[indice].charAt( req[indice].length() - 1 ) == ';' ) break;
                
                if( req[indice].compareToIgnoreCase(",") == 0 ){
                    if( req[indice - 1].charAt(0) != '\'' && req[indice - 1].charAt(req[indice - 1].length() - 1) != '\'' ){ 
                        throw new Exception(" erreur sur les cotes ");
                    }   
                        String val = req[indice - 1].substring(1);
                        val = val.substring(0, val.length()-1);                     //effacer les cotes
                        values.add( val );
                }
                indice++;
            }  
            String value= req[indice - 1];
            if( req[indice].charAt( req[indice].length() - 1 ) == ';' ){
                value = req[indice].substring(1);                       //enlever '
                value = value.substring(0, value.length()-2);           //enlever ';
            }
            values.add( value );

            Fichier f = new Fichier( nom , bdd );

            Relation rel = f.getRelation( nom );                        //la relation concerné

            Object[] all_val = getFilterValue( rel );                              //la valeur de toutes les colonnes 'null' si pas de valeur

            f.insertValue(all_val);

            throw new Exception(" insertion réussie ");

        }catch( Exception e ){
            throw e;
        }
    }


}
