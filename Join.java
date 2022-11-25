package syntaxe;

import java.util.*;
import base.Relation;
import donnees.Fichier;

public class Join extends Grammaire{
    String rel1;
    String rel2;

    String col1;
    String col2;

    public Join( int i ,  String[] req  , Relation new_r) throws Exception {
        try{
            setValue(i, req);                
            while ( i != req.length ){      //tant que mbola tsy tapitra le req
                i++;

                if ( i >= req.length ) break;

                if ( req[i].compareToIgnoreCase("join") == 0 ){
                    this.next_gram = new Join(i, req , new_r);

                    this.next_gram.previous_gram = this;

                    this.next_gram.bdd = bdd;
                }

                if ( req[i].compareToIgnoreCase("where") == 0 ){
                    this.next_gram = new Where(i, req);

                    next_gram.bdd = bdd;

                    this.next_gram.previous_gram = this;
                }
            }
        }catch( Exception e ){
            throw e;
        }
    } 
    
    public void setValue( int i ,String[] req ) throws Exception {          //récupérer les 2 tables
        try{
            while ( req[i].compareToIgnoreCase("=") != 0 ){
                i++;
            }
            String val1 = req[i-1];

            String val2 = req[i+1];

            System.out.println(" val1 "+val1+" val2 "+val2);

            String[] getRel1 = val1.split("\\.");

            System.out.println(" getRel1 "+Arrays.toString(getRel1));

            String[] getRel2 = val2.split("\\.");

            System.out.println(" getRel2 "+Arrays.toString(getRel2));

            rel1 = verifyAS(getRel1[0]);
            rel2 = verifyAS(getRel2[0]);

            col1 = getRel1[1];
            col2 = getRel2[1];

            System.out.println(" fichier "+rel1);
            System.out.println(" fichier "+rel2);
        }catch( Exception e ){
            throw e;
        }
    }

    public Relation  action( String[] req , Relation r , String bdd) throws Exception{
        try{
            Fichier f = new Fichier( rel2 , bdd );

            Fichier f2 = new Fichier( rel1 , bdd);

            System.out.println(" path :  "+f.getPath()+" et "+f2.getPath());

            r = f2.getRelation( rel1 ).jointure( f.getRelation( rel2 ) , col1, col2);

        if ( this.next_gram instanceof Join ){
            r = this.next_gram.action(req , r , bdd);
        }

        while ( this.previous_gram instanceof Join ){
            r = f2.getRelation( rel1 ).jointure( f.getRelation( rel2 ) , col1, col2);

        }

        defaut = r;

        while ( this.next_gram != null ){
            System.out.println(" instance :  "+next_gram.getClass().getSimpleName());
            if ( this.next_gram instanceof Where ){
                System.out.println(" let's go ");
                Relation r_hafa = this.next_gram.action(req, r , bdd);
                System.out.println(" relation apres where ");
                System.out.println(" size :  "+r_hafa.getValue().length);
                for ( int i =0 ; i != r_hafa.getValue().length ; i++ ){
                    System.out.println(Arrays.toString(r_hafa.getValue()[i]));
                }
                return r_hafa;
            }
        }
    
        System.out.println(" fin de la boucle ");
        r.printObj(r);

        return r;

        }catch( Exception e ){
            throw e;
        }
    }
}
