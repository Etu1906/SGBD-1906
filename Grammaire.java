package syntaxe;
import java.util.*;
import base.Relation;
import syntaxe.Update;
public class Grammaire {
    Grammaire next_gram;
    Grammaire previous_gram;
    Object[] value;                 
    static Relation defaut;                             //la table de départ apres from
    Relation response;                                  //la table résultat ( tsy tadidiko raha nampiasaina )
    String bdd;
    static Vector<As> l_as = new Vector<As>();                 //tous les alias
    static String[] syntaxe = { "select" , "as" ,"from" , "where" , "create" , "table" , "or" , "replace" , "and" }; //les vocabulaires possibles

    public Grammaire(){}

    public Grammaire( String bdd ){
        this.bdd = bdd;
    }

    public String getBdd() {
        return bdd;
    }
    public void setL_as(Vector<As> l_as) {
        Grammaire.l_as = l_as;
    }
    public void setBdd( String bdd ){
        this.bdd = bdd;
    }

    public String verifyAS( String value )throws Exception{
        try{
            for ( int i = 0 ; i != l_as.size() ; i++ ){
                System.out.println(" alias :  "+l_as.get(i).getAs()+" et "+l_as.get(i).getRel());
                if ( value.compareToIgnoreCase( l_as.get(i).getAs() ) == 0 || value.compareToIgnoreCase( l_as.get(i).getRel() ) == 0 ){
                    return l_as.get(i).getRel();
                }
            }
            return value;
        }catch( Exception e ){
            throw e;
        }
    }
    public static String[] trim( String[] req ){            //enlever les espaces de la requete
       String[] s1 = new String[ req.length ];

        Vector<String> v = new Vector<String>();

       for ( int i = 0 ; i != req.length ; i++ ){
            s1[i] = req[i].trim();
            if ( s1[i].compareToIgnoreCase(" ") != -1 ){
                v.add( s1[i] );
                System.out.println(" trim :  "+s1[i]);
            }
       } 

       String[] result = new String[ v.size() ];

       for ( int i = 0 ; i != result.length ; i++ ){
           result[i] = v.get(i);
       }

       return result;
    }

    public static boolean include ( String[] liste , String nom ){          
        for ( int i = 0 ; i != liste.length ; i++ ){
            if ( nom.compareToIgnoreCase(liste[i]) == 0 )
                return true;
        }
        return false;
    }

    public void setNext_gram(Grammaire next_gram) {
        this.next_gram = next_gram;
    }
    public void setPrevious_gram( String[] req ) throws Exception {
    }

    public void setValue(Object[] value ) throws Exception {

    }

    public void verifyNext_grammar( String next_val ) throws Exception {

    }

    public Relation requete( String[] req ) throws Exception {
        return new Relation();
    }

    public static String[] verifyReqEnd( String req ){            //vérifier si c'est la fin dub requete (END)
        String[] valiny = new String[2];

        String[] gram = req.split(" ");
        gram = trim(gram);

        valiny[0] = "end";
        valiny[1] = req;

        if( gram[gram.length - 1].compareToIgnoreCase("end")  != 0 ){           //la req n'est pas terminée
            valiny[0] = "tempo";                                                    //voir si la req est une req temporaire 
        }   
        return valiny;
    }

    public static Grammaire syntaxAnalysis( String req , String bdd ) throws Exception {             //initialisation : select ou create ou ...
        String[] gram = req.split(" ");
        gram = trim(gram);

        if ( gram[0].compareToIgnoreCase("select") == 0 ){
            Grammaire g = new Select();

            g.bdd = bdd;

            As.getAllAs( gram , bdd );

            return g;
        }
        if ( gram[0].compareToIgnoreCase("delete") == 0 ){
            Grammaire g = new Delete();

            g.bdd = bdd;

            return g;
        }
        if ( gram[0].compareToIgnoreCase("create") == 0 ){
            Grammaire g = new Create();

            g.bdd = bdd;

            return g;
        }
        if ( gram[0].compareToIgnoreCase("insert") == 0 ){
            Grammaire g = new Insert();

            g.bdd = bdd;

            return g;
        }
        if ( gram[0].compareToIgnoreCase("update") == 0 ){
            Grammaire g = new Update();

            g.bdd = bdd;

            return g;
        }
        if ( gram[0].compareToIgnoreCase( "drop") == 0 ){
            Grammaire g = new Drop();

            g.bdd = bdd;

            return g;
        }
        throw new Exception(" syntaxe invalide , 1ere ligne ");
    }

    public Relation getRelation( String req ) throws Exception {            //début de l'analyse 
        try{
            String[] gram = req.split(" ");
            gram = trim(gram);

            Grammaire g = this;

            Relation new_r = new Relation();

            if ( g instanceof Create ){
                g.action( gram , new_r , bdd);                   //si l'expression est create    
            }
            else{
                new_r = g.action(gram , new_r , bdd);             //une action provoque une autre action  , voir les classes filles
            }

            return new_r;
        }catch ( Exception e ){
            throw e;
        }

    }

    public Object[] Show( String[] req , String bdd )throws Exception{
        return new Object[1];
    }

    public void action( String[] req ,  String bdd ) throws Exception {                           //voir classes filles

    }

    public Relation action( String[] req , Relation r , String bdd ) throws Exception {          //voir classes filles

        return r;
    }

    public String[] transformString( Object[] value , Relation rel ){
        if ( String.valueOf(value[0]).compareToIgnoreCase("*") == 0 )   value = rel.getEn_tete();
        String[] str_value = new String[ value.length ];

        for ( int i= 0 ; i != value.length ; i++ ){
            str_value[i] = String.valueOf(value[i]);
        }
        return str_value;
    }

    public String[] transformString( Object[] value  ){
        String[] str_value = new String[ value.length ];

        for ( int i= 0 ; i != value.length ; i++ ){
            str_value[i] = String.valueOf(value[i]);
        }
        return str_value;
    }

    public Grammaire getNext_gram() {
        return next_gram;
    }
    public Object[] getValue() {
        return value;
    }
    public Grammaire getPrevious_gram() {
        return previous_gram;
    }
}

