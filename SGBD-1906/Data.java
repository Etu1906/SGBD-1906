package donnees;
import java.util.Arrays;

import base.Relation;

public class Data {
    public static Relation[] getData(){
        Relation[] l_r = new Relation[3];
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


        Object[][] val2 = new Object[3][2];
        val2[0][0] = "haha";
        val2[0][1] = "yoyo";
        // val2[0][2] = "huhio" ;

        val2[1][0] = "Marco Polo";
        val2[1][1] = "Bella Note";
        // val2[1][2] = "Italie" ;

        val2[2][0] = "Huhu";
        val2[2][1] = "Bella Note";

        Object[][] val3 = new Object[2][3];

        val3[0][0] = "huhu";
        val3[0][1] = "hallo";
        val3[0][2] = "just";

        val3[1][0] = "Marco Polo";
        val3[1][1] = "Bella Note";
        val3[1][2] = "Italie";

        System.out.println(" equals main  "+Arrays.equals( val[0] , val3[0]) );

        System.out.println(" table compare " + Arrays.equals(val[1], val2[1]));

        String[] name = { "chien" };

        Relation r1 = new Relation("rel1", val, tete);

        Relation r2 = new Relation("rel2", val2, tete2);

        Relation r = new Relation("rel3", val3, tete);

        l_r[0] = r1 ; l_r[1] = r2 ; l_r[2] = r ;
        
        return l_r;
    }
}
