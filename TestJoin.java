
import java.util.Arrays;
import base.Relation;
import syntaxe.Grammaire;
import syntaxe.As;
import java.io.*;
import java.util.Arrays;
import donnees.Fichier;
import java.util.Scanner;
import java.util.*;


public class TestJoin {
    public static void main(String[] args)throws Exception{
        Fichier f1 = new Fichier( "etudiant" , "bisous" );
        Fichier f2 = new Fichier( "etu" , "bisous" );

        Relation rel1 = f1.getRelation("etudiant");
        Relation rel2 = f2.getRelation("etu");

        Relation.printObj( (rel1.jointure(rel2, "etu", "nombre")).getValue() );
    }
}
