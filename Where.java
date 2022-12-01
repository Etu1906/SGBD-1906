package syntaxe;

import java.util.Arrays;

import base.Relation;

public class Where extends Grammaire{
    int id ;
    String colonne;
    String input;
    String condition;

    public Where( int i , String[] req) throws Exception {  
        try{
            id  = i;

            setData(req);

            if ( req.length > id + 4 ){                         //trop statique
                setPrevious_gram(req , id);                     
            }
        }catch( Exception e ){
            throw e;
        }

    }

    public void setPrevious_gram( String[] req , int id ) throws Exception {            //récupérer tous les where de la req
        try{
                if ( req[ id + 4 ] == null  ){
                    throw new Exception(" syntaxe invalide where ( "+( id + 4 )+" ) ");
                }
                if( req[ id + 4 ].compareToIgnoreCase("end") == 0 ){
                    return ;
                }
                this.next_gram = new Where( ( id + 4 ) , req);              //fonction rec

                this.next_gram.previous_gram = this;

                this.next_gram.bdd = bdd;
            }catch( Exception e ){
                throw e;
            }

    }

    public void setData( String[] req ){
        this.colonne = req[id + 1];

        this.input = req[ id + 3 ];

        this.condition = req[ id + 2 ];
    }

    public Relation action( String[] req , Relation r , String bdd) throws Exception{

        try{
            Relation seletion = defaut.selection(colonne, condition, input);    // la req where

            Relation[] new_r = { seletion };

            this.value = new_r;                                         //donner la séléction au where ( chq where )

            if ( this.next_gram instanceof Where ){                 //commencer vers la fin 
                r = this.next_gram.action(req, r , bdd );
            }

            if ( this.previous_gram instanceof From){               //pour le premier where 
                this.previous_gram.value[0] = r;                    

                return r;
            }
            if (  this.previous_gram instanceof Where ){

                Grammaire g = this;

                Relation fusion[] = new Relation[1];

                while ( g.previous_gram instanceof Where == true ){             //tant que mbola where le izy 

                    String connecteur = req[ id ];

                    //récupére les relations a fusionner
                    Relation r1 = ( Relation ) g.value[0];

                    Relation r2 = ( Relation ) g.previous_gram.value[0];            

                    if ( connecteur.compareToIgnoreCase("and") == 0 ){

                        fusion[0] = r1.intersection(r2);
                    }
                    if ( connecteur.compareToIgnoreCase("or") == 0 ){
                        
                        fusion[0] = r1.union1(r2);

                    }
                    if ( connecteur.compareToIgnoreCase("-") == 0 ){
                        fusion[0] = r2.difference(r1);
                    }

                    g.previous_gram.value[0] = fusion[0];                       //donner au prochain where la valeur de la fusion

                    g = g.previous_gram;                                        //iteration

                }

                g.previous_gram.value = fusion;                                 // donner a from

                System.out.println(" apres tout les where  ");

                fusion[0].printObj(fusion[0]);

                System.out.println(" apres return  ");

                System.out.println(" size :  "+fusion[0].getValue().length);
                for ( int i =0 ; i != fusion[0].getValue().length ; i++ ){
                    System.out.println(Arrays.toString(fusion[0].getValue()[i]));
                }

                return fusion[0];
            }

            return seletion;

        }catch( Exception e ){
            throw e;
        }
    }

}
