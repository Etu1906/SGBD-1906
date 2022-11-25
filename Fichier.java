package donnees;

import base.Relation;
import java.io.*;
import java.util.Vector;

public class Fichier extends File {

    String path;
    String bdd;

    public Fichier(String table_name) {                     //pour création fichier
        super("data/"+table_name.trim()+".txt");

            path = "data/"+table_name+".txt";

        
    }

    public Fichier(String table_name , String bdd){                     //pour création fichier
        super("data/"+bdd+"/"+table_name.trim()+".txt");

        this.bdd = bdd;
            path = "data/"+table_name+".txt";
    }
    public Fichier(String table_name , int i){              //pour création dossier
        super("data/"+table_name.trim());

        path = "data/"+table_name;
    }

    public static String findDbb( String name , String bdd ) throws Exception {     //vérifier si la dbb existe 
        Fichier f = new Fichier( name , 0 );

        if ( f.exists() == true ){
            return name;
        }else{
            throw new Exception( " cette base n'existe pas " );
        }
    }
///setters
    public void setBdd( String bd){
        bdd = bd;        
    }

    public void CreerFichier() {                                                    //création fichier
        try {
            this.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] toArray( Vector<String> v ){
        String[] new_val = new String[ v.size() ];

        for( int i = 0 ; i != v.size() ; i++ ){
            new_val[i] = v.get(i);
        }

        return new_val;
    }

    public static void createTable( Vector<String> colonne , String bdd , String nom )throws Exception{
        try {
            Fichier f = new Fichier( nom  ,bdd );

            if( f.exists() == true ){
                throw new Exception(" table existe déja ");
            }
            f.createNewFile();                          //creation du fichier

            f.insertName(nom);                          //insertion nom

            f.insertHead( toArray(colonne) );               //insertion colonne

        } catch (Exception e) {
            throw e;
        }
    }

    public static void createDb( String name ){                                     //création d'une base de données
        try {
            
            Fichier f = new Fichier(name , 0);

            if ( f.exists() == false ){
                boolean v = f.mkdir();
                System.out.println(" v :  "+v);
                return ;
            }
            System.out.println(" la base existe deja ");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static Fichier findTable(String nom , String bdd) throws Exception {                                  //vérifier si une table existe
        Fichier f = new Fichier(nom , bdd);
        System.out.println(" f : " + f);

        if (f.exists() == false) {
            throw new Exception("la relation n'existe pas ");
        }

        return f;
    }

    public Relation getRelation(String table_nom) throws Exception {                            //retourner la relation grace a son nom
        Relation new_r = new Relation();

        setTable(new_r, table_nom);       
        
        System.out.println("fichier ");

        new_r.printObj(new_r);

        return new_r;
    }

    public void setTable(Relation r, String nom) throws Exception {                             //donner les valeurs de la table grace a son nom
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this), ("UTF-8")));

        String line = br.readLine();

        r.setNom(line);

        line = br.readLine();

        String[] en_tete = line.split("%%");

        r.setEn_tete(en_tete);                              //donner le nom des colonnes

        Vector value = new Vector();

        int nb_col = 0;

        while (line != null) {
            line = br.readLine();

            if ( line == null ){ break  ; }

            nb_col = en_tete.length;

            value.add(line);
        }

        br.close();

        Object[][] valiny = new Object[value.size()][nb_col];

        for (int i = 0; i != valiny.length; i++) {
            valiny[i] = String.valueOf(value.get(i)).split("%%");
        }

        r.setValue(valiny);

    }

    public void insertTable(Relation r) throws Exception {                          //inserer une table 
        CreerFichier();
        insertName(r.getNom());
        insertHead(r.getEn_tete());
        for (int i = 0; i != r.getValue().length; i++) {
            insertValue(r.getValue()[i]);
        }
    }

    public void insertValue( Object[] value ) throws Exception {                    //inserer les valeurs d'une table
        FileWriter wr = new FileWriter(this, true);

        BufferedWriter bw = new BufferedWriter(wr);

        String col = "";

        for (int i = 0; i != value.length; i++) {
            col = col + value[i] + "%%";
        }

        bw.write(col);

        bw.newLine();

        bw.close();
        wr.close();
    }

    public static void insertValue( Object[] value , String nom , String bdd ) throws Exception{             //bilan de l'insertion et de la création
        Fichier f = new Fichier(nom , bdd);

        f.CreerFichier();

        f.insertValue(value);
    }

    public void insertName(String nom) throws IOException {                             //inserer nom de la table 
        FileWriter wr = new FileWriter(this, true);

        BufferedWriter bw = new BufferedWriter(wr);

        nom = nom.trim();

        bw.write(nom);
        bw.newLine();

        bw.close();
        wr.close();
    }

    public void insertHead(Object[] en_tete) throws IOException {               //inserer nom des colonnes
        FileWriter wr = new FileWriter(this, true);

        BufferedWriter bw = new BufferedWriter(wr);

        String head = "";

        for (int i = 0; i != en_tete.length; i++) {
            head = head + en_tete[i] + "%%";
        }

        bw.write(head);
        bw.newLine();

        bw.close();
        wr.close();
    }
}
