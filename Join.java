<<<<<<< HEAD
package syntaxe;

import java.util.*;
import base.Relation;
import donnees.Fichier;

public class Join extends Grammaire{
    String rel1;
    String rel2;

    String col1;
    String col2;

    public Join(){}

    public Join( int i ,  String[] req  , Relation new_r) throws Exception {
        try{
            setValue(i, req);                
            while ( i != req.length ){      //tant que mbola tsy tapitra le req
                i++;

                if ( i >= req.length ) break;

                System.out.println("verification de join : "+i+" valeur : "+req[i]);
                if ( req[i].compareToIgnoreCase("join") == 0 ){

                    System.out.println("prochain join");
                        this.next_gram = new Join( i , req , new_r );

                    this.next_gram.previous_gram = this;

                    this.next_gram.bdd = bdd;

                    System.out.println("break");

                    break;
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
            System.out.println(" l'action de join commence  ");

            Fichier f = new Fichier( rel2 , bdd );

            Fichier f2 = new Fichier( rel1 , bdd);

            r = f2.getRelation( rel1 ).jointure( f.getRelation( rel2 ) , col1, col2);

            Relation[] new_r = { r };
 
            this.value = new_r;

            // System.out.println( "next gram apres join"+(next_gram).getClass().getSimpleName() );

        if ( this.next_gram instanceof Join ){
            r = this.next_gram.action(req , r , bdd);
        }

        Relation fusion[] = new Relation[1];

        boolean lotOfJoin = false;                                                      //s'il y a plusieurs join

        Grammaire g = this;

        while ( g.previous_gram instanceof Join == true ){
            lotOfJoin = true;
            g = g.previous_gram;

        }



        while ( g.next_gram instanceof Join == true ){
            lotOfJoin= true;
            Relation r1 = ( Relation ) g.value[0];

            g = g.next_gram;

            Join j = (Join) g;

            Fichier fileForJoin = new Fichier( j.rel2 , bdd );

            Relation r2 = fileForJoin.getRelation( j.rel2 );
            
            System.out.println( Arrays.toString( r1.getEn_tete() ) );
            r1.printObj(r1);

            System.out.println(" et ");

            System.out.println( Arrays.toString( r2.getEn_tete() ) );
            r2.printObj(r2);


            fusion[0] = r1.jointure(r2 , j.col1  , j.col2 );

            System.out.println(" fusion : "+fusion[0]);

            fusion[0].printObj( fusion[0] );

        }

        if( lotOfJoin == true )
            r = fusion[0];

        System.out.println(" lotofjoin : "+lotOfJoin);

        if ( g.next_gram instanceof Where ){
        if( lotOfJoin == true )            
                defaut = fusion[0];                                             //changer la relation par defaut pour where
            System.out.println(" join puis where "+lotOfJoin);
            Relation r_hafa = g.next_gram.action(req, r , bdd);
            for ( int i =0 ; i != r_hafa.getValue().length ; i++ ){
                System.out.println(" valeur raha hafa "+Arrays.toString(r_hafa.getValue()[i]));
            }
            return r_hafa;
        }
        
        System.out.println(" after join ");

        r.printObj(r);

        return r;

        }catch( Exception e ){
            throw e;
        }
    }
}
=======
package syntaxe;

import java.util.*;
import base.Relation;
import donnees.Fichier;

public class Join extends Grammaire{
    String rel1;
    String rel2;

    String col1;
    String col2;

    public Join(){}

    public Join( int i ,  String[] req  , Relation new_r) throws Exception {
        try{
            setValue(i, req);                
            while ( i != req.length ){      //tant que mbola tsy tapitra le req
                i++;

                if ( i >= req.length ) break;

                System.out.println("verification de join : "+i+" valeur : "+req[i]);
                if ( req[i].compareToIgnoreCase("join") == 0 ){

                    System.out.println("prochain join");
                        this.next_gram = new Join( i , req , new_r );

                    this.next_gram.previous_gram = this;

                    this.next_gram.bdd = bdd;

                    System.out.println("break");

                    break;
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
            System.out.println(" l'action de join commence  ");

            Fichier f = new Fichier( rel2 , bdd );

            Fichier f2 = new Fichier( rel1 , bdd);

            r = f2.getRelation( rel1 ).jointure( f.getRelation( rel2 ) , col1, col2);

            Relation[] new_r = { r };
 
            this.value = new_r;

            // System.out.println( "next gram apres join"+(next_gram).getClass().getSimpleName() );

        if ( this.next_gram instanceof Join ){
            r = this.next_gram.action(req , r , bdd);
        }

        Relation fusion[] = new Relation[1];

        boolean lotOfJoin = false;                                                      //s'il y a plusieurs join

        Grammaire g = this;

        while ( g.previous_gram instanceof Join == true ){
            lotOfJoin = true;
            g = g.previous_gram;

        }



        while ( g.next_gram instanceof Join == true ){
            lotOfJoin= true;
            Relation r1 = ( Relation ) g.value[0];

            g = g.next_gram;

            Join j = (Join) g;

            Fichier fileForJoin = new Fichier( j.rel2 , bdd );

            Relation r2 = fileForJoin.getRelation( j.rel2 );
            
            System.out.println( Arrays.toString( r1.getEn_tete() ) );
            r1.printObj(r1);

            System.out.println(" et ");

            System.out.println( Arrays.toString( r2.getEn_tete() ) );
            r2.printObj(r2);


            fusion[0] = r1.jointure(r2 , j.col1  , j.col2 );

            System.out.println(" fusion : "+fusion[0]);

            fusion[0].printObj( fusion[0] );

        }

        if( lotOfJoin == true )
            r = fusion[0];

        System.out.println(" lotofjoin : "+lotOfJoin);

        if ( g.next_gram instanceof Where ){
        if( lotOfJoin == true )            
                defaut = fusion[0];                                             //changer la relation par defaut pour where
            System.out.println(" join puis where "+lotOfJoin);
            Relation r_hafa = g.next_gram.action(req, r , bdd);
            for ( int i =0 ; i != r_hafa.getValue().length ; i++ ){
                System.out.println(" valeur raha hafa "+Arrays.toString(r_hafa.getValue()[i]));
            }
            return r_hafa;
        }
        
        System.out.println(" after join ");

        r.printObj(r);

        return r;

        }catch( Exception e ){
            throw e;
        }
    }
}
>>>>>>> f4961b01204c5bfe8b018dcc22955771bd40fb15
