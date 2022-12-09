package syntaxe;

import java.util.*;

import javax.xml.catalog.Catalog;

import base.Relation;
import donnees.Fichier;

public class Drop extends Grammaire{
    String drop ;                   //table ou base a dropper

    Fichier f ;

    public void verifySyntax( String[] req , String bdd)throws Exception{
        if( req.length < 3 ){
            throw new Exception( " une erreur de syntaxe ( trop court ) " );
        }

        if( req[1].compareToIgnoreCase("database") == 0 ){                  
            f = new Fichier(bdd , 1);
            drop = "base de données";
        }
        else if ( req[1].compareToIgnoreCase("table") == 0 ){
            f = new Fichier( req[2] , bdd);
            drop = "table";
        }else{
            throw new Exception( "syntaxe invalide , on ne peut supprimée qu'une table ou une base de données" );
        }
    }

    public Relation action( String[] req , Relation r , String bdd ) throws Exception {

        try{
            verifySyntax(req, bdd);

            System.out.println(" supression de "+f.getPath());

            f.dropTable();

            throw new Exception(drop+" "+ req[2] +"supprimées ");

        }catch( Exception e ){
            throw e;
        }
    }    
}
