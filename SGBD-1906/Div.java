package syntaxe;

import java.io.IOException;
import java.util.Arrays;
import donnees.Fichier;
import base.Relation;
public class Div extends Grammaire{
    String val1;
    String val2;

    Relation r1;
    Relation r2;

    String col1;
    String col2;

    String[] getAllcol( String value ) throws Exception {
        try {
            String[] split = value.split( "," );
            String[] allcol = new String[ split.length ];
            for ( int i = 0 ; i != allcol.length ; i++ ){
                allcol[i] = getCol(split[i]);
            }

            return allcol;
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }

    String GetTableName( String value ) throws Exception {
        String[] split = value.split( "," );
        if ( split.length == 1 ){                           //si il n'y a qu'une seule colonne
            split = value.split("\\.");
            if ( split.length != 2 )   throw  new Exception( " une erreur sur "+value ) ;   //si la syntaxe n'est pas lolo.huhu 
            return split[0];
        }
        split = split[0].split("\\.");
        if ( split.length != 2 )   throw  new Exception( " une erreur sur "+value ) ;   //si la syntaxe n'est pas lolo.huhu 
        return split[0];
    }

    String getCol( String value )throws Exception{
        String[] split = value.split("\\.");

        if ( split.length != 2 ){
            throw new Exception( " une erreur sur "+value );
        }
        
        return split[1];
    }

    public Div( int i , String[] req , String bdd) throws Exception {

        this.bdd = bdd;

        try{

            if ( req[i+2].compareToIgnoreCase("on") != 0 ){
                if(  req[i+4].compareToIgnoreCase("on") != 0 )
                    throw new Exception(" la requete attend un 'on' ");
            }

            while( req[i].compareToIgnoreCase("/") != 0 ){
                i++;
                if ( i >= req.length )  throw new Exception(" la requete ne s'arrete pas correctement ");
            }

            val1 = req[i-1];

            val2 = req[i+1];


            System.out.println(" val1 "+val1+" val2 "+val2);

            String getRel1 =  GetTableName(val1) ;

            System.out.println(" getRel1 "+getRel1);

            String getRel2 = GetTableName(val2);

            System.out.println(" getRel2 "+getRel2);

            String rel1 = verifyAS(getRel1);
            String rel2 = verifyAS(getRel2);

            System.out.println(" rel1 :  "+rel1);
            System.out.println(" rel2 :  "+rel2);


            Fichier f = new Fichier( rel1 , bdd );

            Fichier f2 = new Fichier( rel2  , bdd);

            r1 = f.getRelation( rel1 );

            r2 = f2.getRelation( rel2 );

        }catch( IOException e ){
            throw e;
        }
        catch( Exception e ){
            throw e;
        }
        
    }


    public Relation action( String[] req , Relation r , String bdd ) throws Exception {       
        String[] col1 =  getAllcol( val1 ) ;
        String[] col2 = getAllcol(val2);

        System.out.println("les colonnes conc col1 : "+Arrays.toString(col1));
        System.out.println("les colonnes conc col2 : "+Arrays.toString(col2));


        r = r1.division(r2 , col1 , col2 );

        return r;
    }
}
