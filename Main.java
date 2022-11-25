import java.util.Arrays;
import base.Relation;
import syntaxe.Grammaire;
import syntaxe.As;
import java.io.*;
import java.util.Arrays;
import donnees.Fichier;
import java.util.Scanner;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        try{    
            Object[][] val = new Object[3][3];

            Object[] tete = new Object[3];

            Object[] tete2 = { "nom", "prenom" };

            tete[0] = "nom";
            tete[1] = "prenom";
            tete[2] = "chien";

            val[0][0] = "huhu";
            val[0][1] = "hallo";
            val[0][2] = "just";

            val[1][0] = "Marco Polo";
            val[1][1] = "Bella Note";
            val[1][2] = "Italie";

            val[2][0] = "haha";
            val[2][1] = "yoyo";
            val[2][2] = "huhio";


            // Object[][] val2 = new Object[3][2];
            // val2[0][0] = "haha";
            // val2[0][1] = "yoyo";
            // // val2[0][2] = "huhio" ;

            // val2[1][0] = "Marco Polo";
            // val2[1][1] = "Bella Note";
            // // val2[1][2] = "Italie" ;

            // val2[2][0] = "Huhu";
            // val2[2][1] = "Bella Note";

            // Object[][] val3 = new Object[2][3];

            // val3[0][0] = "huhu";
            // val3[0][1] = "hallo";
            // val3[0][2] = "just";

            // val3[1][0] = "Marco Polo";
            // val3[1][1] = "Bella Note";
            // val3[1][2] = "Italie";

            // System.out.println(" equals main  "+Arrays.equals( val[0] , val3[0]) );

            // System.out.println(" table compare " + Arrays.equals(val[1], val2[1]));

            // String[] name = { "chien" };

            Relation r1 = new Relation(" rel1 ", val, tete);

            // Relation r2 = new Relation(" rel2 ", val2, tete2);

            // Relation r = new Relation(" rel3 ", val3, tete);

            // Relation r3 = r1.intersection(r);

            // Relation r8 = r1.union1(r);

            // Relation r4 = r1.project( name );

            // Relation r5 = r1.selection("prenom", "like", "yo");

            // Relation r6 = r1.produit_cartesien(r2);

            // Relation r7 = r1.jointure(r2, "nom", "nom");

            // Relation rel = Relation.create_rel("table.Employe");

            // String[] value = { "milou" , "Tintin" };

            // rel.insertInto(value);


            // String req = "select chien        from        rel1 where nom = huhu";

            // Grammaire g = Grammaire.syntaxAnalysis(req);

            // Relation new_r = g.getRelation(req );

            // System.out.println(" main : "+new_r);

            // for ( int i = 0 ; i != new_r.getValue().length ; i++ ){
            //     System.out.println( " new_r "+Arrays.toString(new_r.getValue()[i]) );
            // }

            // String s1 = "select *        from rel1 where nom = huhu";

            // String[] s2 = s1.split(" ");

            // System.out.println("s2 :  "+Arrays.toString(s2));

            // for ( int i = 0 ; i != s2.length ; i++ ){
            //     System.out.println( " s2["+i+"] = "+s2[i]+" c =  "+s2[i].compareToIgnoreCase("from") );
            // }

            // String s2 = "lolo";

            // s1 = s1.trim();

            // System.out.println(" equals : "+s1.equals(s2));

            // File file = new File("data/test.txt");

            // if ( file.exists() == false ){
            //     file.createNewFile();
            // }else{
            //     FileWriter wr = new FileWriter(file);

            //     BufferedWriter bw = new BufferedWriter( wr );

            //     bw.write( " Bjr " );

            //     bw.newLine();

            //     bw.write( " le monde " );

            //     bw.close();

            //     wr.close();
            // }
        
                // Fichier f = Fichier.findTable( "rel1" );

                // Relation r = f.getRelation("rel1");

                // for ( int i = 0 ; i != r.getValue().length ; i++ ){
                //     System.out.println(" r.value "+Arrays.toString(r.getValue()[i]));
                // }

                // Object[] value = { "Ralp" , "Steave" , "James" };

                // System.out.println(" include : "+Arrays.asList(value).contains("Jame"));

                // Fichier.insertValue(value, r.getNom());

                // Fichier f = new Fichier(r1.getNom());

                // f.insertTable(r1);

                // File f = new File(" E:/alg_rel/s3/creation_bdd/operation/lolo");
                // f.mkdir();

                System.out.println("\n connected ");

                String req = "";

                String bdd = "";

                Vector<Relation> l_r = new Vector<Relation>();

                Relation new_r = new Relation();

                try ( Scanner sc = new Scanner( System.in ) ) {
                    while ( true ){ 

                        System.out.print("> ");                    
                        
                        req = sc.nextLine();

                        if ( req.compareToIgnoreCase("exit") == 0 ){
                            break ;
                        }
                        else if ( req.contains("use") == true ){
                            String[] reqSplit = req.split(" ");
                            reqSplit = Grammaire.trim(reqSplit);

                            System.out.println("split : "+reqSplit[1]);

                            bdd = Fichier.findDbb( reqSplit[1] , bdd );

                            Fichier f = new Fichier(bdd);
                            f.setBdd(bdd);
                            
                            Grammaire g = new Grammaire();
                            g.setBdd(bdd);                  //donner bdd pour chq relation
                            System.out.println("bdd : "+bdd);
                        }
                        else if ( req.contains("save") == true){
                            String[] reqSplit = req.split(" ");
                            reqSplit = Grammaire.trim(reqSplit);

                            System.out.println("split : "+reqSplit[1]);

                            Relation r_save = Relation.findRelation(l_r, reqSplit[1]);

                            System.out.println(" nom "+r_save.getNom());

                            Fichier new_f = new Fichier(r_save.getNom()) ;
                            
                            new_f.insertTable(r_save);
                            
                            System.out.println("relation sauvegardé");
                        }else{
                            
                        if (  bdd.compareToIgnoreCase("") == 0 ){
                            System.out.println( " aucune base de données " );
                        }
                            try{
                                Grammaire g = new Grammaire( bdd );

                                g = Grammaire.syntaxAnalysis(req , bdd);

                                System.out.println(" bdd :  "+g.getBdd());

                                new_r = g.getRelation(req );

                                new_r.printObj(new_r);

                                g.setL_as( new Vector<As>() );

                                l_r.add( new_r );
                            }catch( Exception e ){
                                e.printStackTrace();
                                System.err.println(e);
                            }
                        }

                    }
                }

                for ( int i = 0 ; i != l_r.size() ; i++ ){
                    System.out.println(" vector : "+l_r.get(i).getNom());
                }

                System.out.println("good bye");


                //  String req = "select * from rel1 where nom like ha";

                // Grammaire g = Grammaire.syntaxAnalysis(req);

                // Relation new_r = g.getRelation(req );

                // System.out.println(" new_r tete : "+Arrays.toString(new_r.getEn_tete()));
            }catch(Exception e ){
                e.printStackTrace();
                System.err.println(e);
            }
    }


}
