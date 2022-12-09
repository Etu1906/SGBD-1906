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
    Relation rel ;                                      //la relation a insérer

    String[] syntaxe = { "into" , "col" , "end" , "," , "values" };

    public void verifyColonne()throws Exception{                                        //vérifier si les colonnes existe 
        for( int i = 0 ; i != colonne.size() ; i++ ){
            if( rel.include( colonne.get(i) ) == -1  ){
                throw new Exception(" la colonne "+colonne.get(i)+"  n'existe pas ");
            }
        }
    } 

    public String findValueByIndex( String name)throws Exception{             //retrouver la valeur grace a l'index de la valeur
        int i = colonne.indexOf(name);
        if( i == -1 && rel.include(name) != -1   )   return "null";                      //si elle n'est pas précisée

        return values.get(i);
    }

    public Object[] getFilterValue(  )throws Exception{           //relié les valeurs et les colonnes demandées  ex : insert into huhu ( *lala ) values ( *hoho );
        try{
            verifyColonne();
            Object[] res = new Object[ rel.getEn_tete().length];
            if( colonne.size() == 0 ){                  //si l'on a pas spécifié les colonnes
                return values.toArray();
            }
            for( int i = 0 ; i != res.length ; i++ ){
                res[i] = findValueByIndex( String.valueOf(rel.getEn_tete()[i]) );
                System.out.println(" res["+i+"] :  "+res[i]);
            }

            return res;
        }catch( Exception e ){
            throw e;
        }
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
        while( req[indice].compareToIgnoreCase("end") != 0  ){

            if( req[indice].compareToIgnoreCase(syntaxe[3]) == 0 ){
                System.out.println(" indice :  "+indice);
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

            System.out.println(" valeur apres col val "+indice);

            indice = verifyAfterValues(req, indice);

            // if( indice != req.length - 1 ) throw new Exception(" end pas a la bonne place ");
        }catch( Exception e ){
            throw e;
        }

    }

    public Relation action( String[] req , Relation r , String bdd ) throws Exception {          // insertio data

        try{
            verifySyntaxe(req);

            nom = req[2];
            int indice = 3;

            Fichier f = new Fichier( nom , bdd );

            rel = f.getRelation( nom );                        //la relation concerné

            if( req[3].compareToIgnoreCase("col") == 0 ){                           //si l'on précise les colonnes
                while( req[indice].compareToIgnoreCase("values") != 0 ){

                    if( req[indice].compareToIgnoreCase(",") == 0 ){
                        colonne.add( req[indice - 1] );
                    }

                    indice++;
                }

                colonne.add( req[indice - 1] );
            }
        
            boolean vect_tempo = false;      //voir si le vecotr colonne n'est que temporaire pour la vérification du type

            if( colonne.size() ==  0){
                colonne = Relation.ToVectorString( rel.getEn_tete() );
                vect_tempo = true;
            }

            System.out.println(" valeur des colonnes :  "+Arrays.toString(colonne.toArray()));

            String req_tempo = req[indice];
            int indexOfColumn = 0;
            while( req[indice].compareToIgnoreCase("end") != 0 ){
                if( req[indice].compareToIgnoreCase(",") == 0 ){
                    String type = String.valueOf(rel.getType()[rel.include(colonne.get(indexOfColumn))]) ;
                    if( type.compareToIgnoreCase("String") == 0 ){
                        if( req[indice - 1].charAt(0) != '\'' || req[indice - 1].charAt(req[indice - 1].length() - 1) != '\'' ){
                            throw new Exception(" erreur sur les cotes ");
                        }
                            String val = req[indice - 1].substring(1);
                            val = val.substring(0, val.length()-1);                     //effacer les cotes
                            values.add( val );
                    }else{
                        if( req[indice - 1].matches("[+-]?\\d*(\\.\\d+)?") == false )
                        throw new Exception(" ce n'est pas un nombre ");
                        values.add( req[indice - 1] );                                              //pour type number
                    }
                    indexOfColumn++;
                }
                indice++;
            }
            String type = String.valueOf(rel.getType()[rel.include(colonne.get(indexOfColumn))]) ;
            if( type.compareToIgnoreCase("String") == 0 ){
            // dernier valeur a ajouté
            if( req[indice - 1].charAt(0) != '\'' || req[indice - 1].charAt(req[indice - 1].length() - 1) != '\'' ){
                throw new Exception(" erreur sur les cotes ");
            }
                String val = req[indice - 1].substring(1);
                val = val.substring(0, val.length()-1);                     //effacer les cotes
                values.add( val );
        }else{
            if( req[indice - 1].matches("[+-]?\\d*(\\.\\d+)?") == false )
                throw new Exception(" ce n'est pas un nombre ");
            values.add( req[indice - 1] );                                              //pour type number
        }

        if( vect_tempo == true )   colonne.clear();                                 //effacer les données de la colonnes si temporaire

            Object[] all_val = getFilterValue();                              //la valeur de toutes les colonnes 'null' si pas de valeur 

            Relation new_r =  f.getRelation( nom );

            if( new_r.getEn_tete().length != all_val.length  ){
                throw new Exception((new_r.getEn_tete().length - all_val.length )+" colonne en manque");
            }

            f.insertValue(all_val);                                     //insertion

            throw new Exception(" insertion réussie ");

        }catch( Exception e ){
            throw e;
        }
    }


}
