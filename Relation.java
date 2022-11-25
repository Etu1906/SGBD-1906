package base;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import java.util.Iterator;

import java.util.Arrays;
import java.util.HashSet;
import java.lang.reflect.*;
public class Relation {
    String nom;
    Object[][] value;
    Object[] en_tete;
    int line = 0;
    String alias = "";

    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getAlias() {
        return alias;
    }
    public Object[][] getValue() {
        return value;
    }
    public String getNom() {
        return nom;
    }
    public Object[] getEn_tete() {
        return en_tete;
    }
    public void setLine(int line) {
        this.line = line;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setValue(Object[][] value) {
        this.value = value;
    }
    public Relation()
    {

    }

    public Relation( String nom , Object[] title )
    {
        this.nom = nom;
        this.en_tete = title;
    }

    public Relation( String nom , Object[][] value , Object[] title )
    {
        this.nom = nom;
        this.value = value;
        this.en_tete = title;
    }

    public static Relation findRelation( Vector<Relation> l_r , String nom ){
        for ( int i = l_r.size() - 1 ; i >= 0 ; i-- ){
            System.out.println(" compare :  "+(l_r.get(i).nom).compareToIgnoreCase( nom )+" name "+l_r.get(i).nom+" "+nom);
            if ( (l_r.get(i).nom).compareToIgnoreCase( nom ) == 0){
                return l_r.get(i);
            }
        }
        return new Relation();
    }

    Object[][] cleanTable( Object[][] table ){
        int i = 0 ;
        while ( table[i][0] != null ){
            System.out.println("obj value : "+table[i]);
            i++;
            if ( i >= table.length ) 
                break;
        }

        Object[][] new_table = new Object[ i ][ table[0].length ];
        for ( int j = 0 ; j != new_table.length ; j++ ){
            new_table[j] = table[j];
        }

        return new_table;
    } 

    public static Relation create_rel( String classe ) throws Exception
    {
        Class c = Class.forName(classe);

        Field[] f = c.getDeclaredFields();

        Object[] title = new Object[ f.length ];

        for ( int i = 0 ; i != title.length ; i++ )
        {
            title[i] = f[i].getName();
        }

        Object[][] obj = null;

        Relation new_r = new Relation( c.getSimpleName() , obj , title );

        return new_r;
    }

    public Relation union1( Relation rel )
    {
        triFusion(rel);
        triFusion(this);
        Relation new_r = new Relation( this.nom , this.en_tete );

        new_r.value = Fusion(this.value, rel.value);

        printObj(new_r);

        return new_r;
    }


    public static Object[][] Fusion(Object[][] X, Object[][] Y)
    {
        int k = 0, i = 0, j = 0;
        Object[][] aux = new Object[X.length + Y.length][X[0].length];
         while (i < X.length && j < Y.length)
        {
            if ( !Arrays.equals(X[i], Y[j]) )
            {
                if ((String.valueOf(X[i][0])).compareToIgnoreCase( (String.valueOf(Y[j][0]) )) < 0) {
                    aux[k++] = X[i++];
                }
                else {
                    aux[k++] = Y[j++];
                }
            }else{
                aux[k++] = X[i];
                while(Arrays.equals(X[i], Y[j]) )
                {
                    j++;
                    if ( j >= Y.length )
                        break;
                }
                i++;
            }
        }
 
        while (i < X.length) {
            aux[k++] = X[i++];
        }
 
        while (j < Y.length) {
            aux[k++] = Y[j++];
        }
 
        return aux;
    }
 




    public static void triFusion(Relation tableau)
    {
    int longueur=tableau.value.length;
    if (longueur>0)
        {
        triFusion(tableau,0,longueur-1);
        }
    }

    static void triFusion(Relation tableau,int deb,int fin)
    {
    if (deb!=fin)
        {
        int milieu=(fin+deb)/2;
        triFusion(tableau,deb,milieu);
        triFusion(tableau,milieu+1,fin);
        fusion(tableau,deb,milieu,fin);
        }
    }

    static void fusion(Relation tableau,int deb1,int fin1,int fin2)
    {
    int deb2=fin1+1;

    //on recopie les éléments du début du tableau
    Object[] table1[]=new Object[fin1-deb1+1][tableau.value[0].length];
    for(int i=deb1;i<=fin1;i++)
        {
        table1[i-deb1]=tableau.value[i];
        }
    
    int compt1=deb1;
    int compt2=deb2;
    
    for(int i=deb1;i<=fin2;i++)
        {        
        if (compt1==deb2) //c'est que tous les éléments du premier tableau ont été utilisés
            {
            break; //tous les éléments ont donc été classés
            }
        else if (compt2==(fin2+1)) //c'est que tous les éléments du second tableau ont été utilisés
            {
            tableau.value[i]=table1[compt1-deb1]; //on ajoute les éléments restants du premier tableau
            compt1++;
            }
        else if (String.valueOf(table1[compt1-deb1][0]).compareToIgnoreCase(String.valueOf(tableau.value[compt2][0])) < 0)
            {
            tableau.value[i]=table1[compt1-deb1]; //on ajoute un élément du premier tableau
            compt1++;
            }
        else
            {
            tableau.value[i]=tableau.value[compt2]; //on ajoute un élément du second tableau
            compt2++;
            }
        }
    }
    public void insertInto( String[] value )
    {
        int column = en_tete.length;
        
        if ( this.value == null )
        {
            this.value = new Object[line + 1][ column ];
        }
        
        Object[][] obj = new Object[ line + 1 ][column];

        for ( int i = 0 ; i != value.length ; i++ )
        {
            obj[line][i] = value[i];
        }

        this.value = obj;

        line++;

        printObj(this);

    }

    public Relation intersection( Relation rel )
    {
        if ( rel.value[0].length == this.value[0].length )
        {
            Relation new_r = new Relation( );

            Object[][] obj = new Object[( this.value.length + rel.value.length )][ this.value[0].length ];
        
            triFusion(this);

            triFusion(rel);

            int number_line = 0;
            
            int start = 0;
            
            for ( int i = 0 ; i != this.value.length ; i++ )
            {
                for ( int j = start ; j != rel.value.length; j++ )
                {
                    if ( Arrays.equals( rel.value[j] , this.value[i]) )
                    {
                        System.out.println( "miditra" );
                        obj[ number_line ] = this.value[i];
                        start = j;
                        number_line++;
                    }
                }
            }

            int row = 0 ; 
            
            int column = 0 ; 

            obj = cleanTable(obj);

            new_r.value = obj;

            new_r.en_tete = en_tete;

            new_r.nom = nom; 

            
            System.out.println("en tete "+Arrays.toString( en_tete ));

            printObj(new_r);


            return new_r;
        }else{
            return  null;
        }
    } 

    ///valeurs distinct d'un objet[][]
    //divers transformation
    public static Vector setObj( Object[] obj ){
        Vector v = new Vector();

        for ( int i = 0 ; i != obj.length ; i++ ){
            v.add( obj[i] );
        }

        return v;
    }

    public static Vector<Vector> toVector( Object[][] obj ){
        Set<Vector> v = new HashSet<Vector>();                  //pour avoir les valeurs distincts , utiliser interface Set

        for ( int i = 0 ; i != obj.length ; i++ ){
            v.add( setObj(obj[i]) );
        }

        Vector<Vector> valiny = new Vector<Vector>();

        Iterator iterator = v.iterator();

        while(iterator.hasNext()){
            valiny.add(  (Vector) iterator.next() );
        }

        return valiny;
    }

    public static Object[][] toArray( Vector<Vector> v ){
        Object[][] obj = new Object[ v.size() ][ v.get(0).size() ];
        for ( int i = 0 ; i != v.size() ; i++ ){
            obj[i] = v.get(i).toArray();
        }

        return obj;
    }

///Afficher les résultats
    public static void printObj( Object[][] obj ){
        for( int i = 0 ; i != obj.length ; i++ ){
            System.out.println("obj["+i+"] : "+Arrays.toString(obj[i]));
        }
    }

    public static  void printObj( Set<Vector> v ){
        Iterator iterator = v.iterator();

        while(iterator.hasNext()){
            System.out.println("v : "+( iterator.next() ));
        }

    }

    public static void printObj( Vector<Vector> v ){
        for ( int i = 0 ; i != v.size() ; i++ ){
            System.out.println(" v["+i+"]"+Arrays.toString( v.get(i).toArray() ));
        }

    }

/// distinction d'un obj[][]
    public static Object[][] distinct( Object[][] obj ){
        Vector<Vector> v = toVector(obj);

        return toArray(v);
    }

    public static boolean In( Object[][] obj , Object[] value ){            //si une valeur est présente dans un obj[][]
        for ( int i = 0 ; i != obj.length ; i++ ){
            if ( Arrays.equals( obj[i] , value) == true ){
                return true;
            }
        }
        return false;
    }

    //différence entre 2 obj[][]
    public static Object[][] difference( Object[][] a , Object[][] b ) throws Exception {
        if ( a[0].length != b[0].length ){
            throw new Exception( " different nombre de colonne " );
        }
        int count = 0 ;

        int i = 0  ; //itérateur
        //récupérer le nombre le ligne
        while ( i != a.length ){
            if ( In( a , a[i] ) == true && In( b , a[i] ) == false ){
                count++;
            }
            i++;
        }  
        
        System.out.println("count : "+count);

        Object[][] obj = new Object[ count ][ a[0].length ];

        count = 0;

        i =  0;
        while ( i != a.length ){
            if ( In( a , a[i] ) == true && In( b , a[i] ) == false ){           //dans A et pas dans B
                obj[count++] = a[i];
            }
            i++;
        }  
        
        printObj(obj);

        return obj;
    }

    public Relation difference( Relation rel ) throws Exception {             //différence A/B
        try{
            Relation new_r = new Relation();
            new_r.en_tete = this.en_tete;
            new_r.value = difference( this.value , rel.value );

            System.out.println(" diff entre :  ");
            printObj(this);
            System.out.println("et");
            printObj( rel );

            System.out.println("diff result : ");
            printObj(new_r);
            return new_r;
        }catch( Exception e ){
            throw e;
        }
    }

    
    public String[] getCol( String[] not ){
        Vector<String> valiny = new Vector<String>();
        int count = 0 ;
        List<String> liste = new ArrayList<>();
        liste = Arrays.asList( not );
        for ( int i = 0 ; i != en_tete.length ; i++ ){
            System.out.println(" izay miditra : "+this.en_tete[i]+"  "+Arrays.asList( not ).contains( this.en_tete[i] ));
            if ( liste.contains( this.en_tete[i] ) == false  ){              //si la colonne n'est pas utilisée pour la division
                valiny.add(String.valueOf(this.en_tete[i]));
            }
        }
        String[] val = new String[ valiny.size() ];

        int j = 0;
        for ( int i = 0 ; i != val.length ; i++ ){
            val[j++] = valiny.get(i);
        }

        return val;
    }

    public Relation division( Relation r , String[] col1 , String[] col2 ) throws Exception {
        try{
            String[] name =  col2 ;

            System.out.println("  colonne 1 : "+Arrays.toString(col1)+"  colonne 2 : "+Arrays.toString( col2 ));

            System.out.println(" name :  "+Arrays.toString(this.getCol(col1)));

            Relation r3 = this.project( this.getCol(col1) );         //colonnes hafa akotranle name ex : (nom et prénom)!!! / coursX

            Relation r4 = r.project(name);

            System.out.println(" distinct ");

            r3.value = distinct( r3.value );

            Relation r5 = r3.produit_cartesien(r4);                     //possibilité rehetra

            Relation r6 = r5.difference(this);                          //ze rehetra tsy izy 

            Relation r7 = r6.project(this.getCol(name));             //alaina ze colonne ilaina amn valiny

            Relation r8 = r3.difference(r7);                            //asorina ze rehetra tsy izy

            return r8;
        }catch( Exception e ){
            throw e;
        }
    }

    public void default_sort()
    {
        while( !Order_list(  ) )
        {
            for ( int i = 0 ; i != value.length - 1; i++ )
            {
                if ( String.valueOf(this.value[i][0]).compareToIgnoreCase(String.valueOf( this.value[i + 1][0] )) > 0 )
                {
                    Object[] temp = this.value[i + 1];
                    this.value[i+1] = value[i];
                    value[i] = temp;
                }
            }
        }
    }

    public boolean Order_list()
    {
        for ( int i = 0 ; i != this.value.length -1  ; i++ )
        {
            if ( String.valueOf(this.value[i][0]).compareToIgnoreCase(String.valueOf( this.value[i + 1][0] )) > 0  )
            {
                return false;
            }
        }
        return true;
    }

    public void printObj( Relation obj )
    {
        for ( int i = 0 ; i!= obj.value.length ; i++ )
        {
            System.out.println("value "+Arrays.toString( obj.value[i] ));
        } 
        System.out.println(" \n \n ");
    }

    void printObjNull( Relation obj )
    {
        int i = 0 , j = 0 ; 
        while ( obj.value[i][j] != null )
        {
            for ( j = 0 ; j != obj.value[0].length ; j++ )
            {
                System.out.println("value["+i+"]["+j+"]: "+obj.value[i][j]);
            }
            j=0;
            i++;
        }
    }

    public Relation project( String[] nom )  throws Exception                   //projection
    {
        try{
            int[] value = includes( nom );

            Relation new_r = new Relation();

            Object[][] obj = new Object[ this.value.length ][ value.length ];

            for ( int i = 0 ; i != this.value.length ; i++ )
            {
                insert( obj , value , i );
            }


            new_r.value = obj;

            new_r.nom = this.nom;

            Object[] new_title = new Object[ value.length ]; 

            insert( new_title , value );

            new_r.en_tete  = new_title;

            System.out.println("en tete "+Arrays.toString( new_r.en_tete ));

            printObj(new_r);

            return new_r;
        }catch( Exception e ){
            throw e;
        }
    }

    public void insert( Object[][] obj ,  int[] val , int i )
    {
        for ( int j = 0 ; j != obj[0].length ; j++ )
        {
            if ( val[j] != -1 )
                obj[i][j] = this.value[i][val[j]];
        }
    }

    public void insert( Object[] obj , int[] val )
    {
        for ( int i = 0 ; i != obj.length ; i++ )
        {
            obj[i] = this.en_tete[val[i]];
        }
    }

    public int include( String nom )
    {
        for ( int i = 0  ; i != en_tete.length ; i++ )
        {
            if ( String.valueOf(en_tete[i]).compareToIgnoreCase( nom ) == 0 )
            { 
                return i;
            }

        }
        return -1;
    }

    public int[] includes( String[] nom ) throws Exception
    {
        int[] value = new int[nom.length];

        for ( int i = 0  ; i != value.length ; i++ )
        {
            value[i] = include(nom[i]);
            if ( value[i] == -1 )   throw new Exception(" une colonne *"+nom[i]+"* n'est pas presente dans la table ");
        }

        return value;
    }

    public void setEn_tete( String[] nom )
    {
        Object[] en_tete = new Object[ nom.length ];

        for ( int i = 0 ; i != nom.length ; i++ )
        {
            en_tete[i] = nom[i];
        }

        this.en_tete = en_tete;
    }

    public void printEn_tete()
    {
        for ( int i = 0 ; i != en_tete.length ; i++ )
        {
            System.out.println(" en_tete["+i+"]"+en_tete[i]);
        }
    }

    public Relation selection( String colonne , String action , String attribut )
    {
        int line = include( colonne );
        
        Object[][] valiny = new Object[ this.value.length ][ this.value[0].length ];

        int count = 0;

        for ( int i = 0 ; i != this.value.length ; i++ )
        {
            if ( action.compareToIgnoreCase("like") == 0 )
            {
                if ( String.valueOf(this.value[i][line]).contains( attribut ) )
                {
                    valiny[count] = this.value[i];
                    count++;
                }
            }
            if ( action.compareToIgnoreCase("=") == 0 )
            {
                if ( String.valueOf(this.value[i][line]).compareToIgnoreCase(attribut) == 0 )
                {
                    valiny[count] = this.value[i];
                    count++;
                }
            }
        }

        valiny = cleanTable(valiny);

        Relation new_r = new Relation( this.nom , valiny , this.en_tete  );

        int j = 0; 
        int i = 0;

        System.out.println("en tete "+Arrays.toString( new_r.en_tete ));

        printObj(new_r);

        return new_r;

    }

    public Relation produit_cartesien( Relation rel )
    {
        Relation new_r = new Relation();

        int length_one = this.value.length ;

        int length_two = rel.value.length ;

        int col_length1 = this.value[0].length;

        int col_length2 = rel.value[0].length;

        Object[][] obj = new Object[ length_one * length_two ][ col_length1 + col_length2 ];

        int count  = 0;

        for ( int i = 0 ; i != this.value.length ; i++ )
        {
            for ( int j = 0 ; j != rel.value.length ; j++ )
            {
                System.arraycopy( this.value[i] , 0 , obj[count] , 0 , col_length1 );

                System.arraycopy( rel.value[j] , 0 , obj[count] , col_length1 , col_length2 );

                count++;
            }
        }

        for ( int i = 0 ; i!= obj.length ; i++ )
        {
            System.out.println(" obj "+Arrays.toString( obj[i] ));
        }

        Object[] new_en_tete = new Object[ col_length1 + col_length2];

        System.arraycopy( this.en_tete , 0 , new_en_tete , 0 , col_length1 );

        System.arraycopy( rel.en_tete , 0 , new_en_tete , col_length1 , col_length2 );

        System.out.println(" en_tete "+Arrays.toString( new_en_tete ));

        new_r.value = obj;

        System.out.println(" size : "+new_r.value.length);

        new_r.en_tete = new_en_tete;

        return new_r;
    }

    public Relation jointure( Relation rel , String att1 , String att2 )
    {
        Object[] tete = new Object[ this.en_tete.length ];

        int id1 = this.include( att1 );

        int id2 = rel.include( att2 );

        rel.permute( id2 , tete);

        triFusion(this);

        triFusion( rel );

        int length_one = this.value.length ;

        int length_two = rel.value.length ;

        int col_length1 = this.value[0].length;

        int col_length2 = rel.value[0].length;


        Object[][] obj = new Object[ length_one * length_two  ][ col_length1 + col_length2 - 1 ];

        int count = 0 ;

        for ( int i = 0 ; i != this.value.length ; i++ )
        {
            for ( int j = 0 ; j != rel.value.length ; j++ )
            {
                System.out.println("  id1 : "+id1);
                String one = String.valueOf(value[i][id1]);
                String two = String.valueOf( rel.value[j][col_length2 - 1]);
                if ( one.compareToIgnoreCase( two ) == 0 )
                {
                    System.arraycopy( this.value[i] , 0 , obj[count] , 0 , col_length1 );   //a partir de l'indice 0 copier dans obj[count] la valuer de value[i] et prener l'element de 0  jusqu'a col_length1

                    System.arraycopy( rel.value[j] , 0 , obj[count] , col_length1 , col_length2 - 1 );

                    count++;
                }
            }
        }

        System.out.println(" en_tete "+Arrays.toString( en_tete ));

        for ( int i = 0 ; i!= obj.length ; i++ )
        {
            System.out.println(" obj "+Arrays.toString( obj[i] ));
        } 

        obj = cleanTable(obj);

        Relation new_r = new Relation( this.nom , obj , funsionEn_tete(rel) );

        printObj(new_r);

        return new_r;

    }


    public void permute( int id , Object[] en_tete)
    {
        System.out.println(" l'id utilise : "+id);
        int final_element = value[0].length - 1;

        for ( int i = 0 ; i!= this.value.length ; i++ )             //permuter 2 colonnes de la relation
        {
            Object obj = this.value[i][id];

            this.value[i][id] = this.value[i][final_element];

            this.value[i][final_element] =  obj;
        }

        Object obj = this.en_tete[ id ];

        this.en_tete[ id ] = this.en_tete[ final_element ];

        this.en_tete[ final_element ] = obj;

    }

    public String[] funsionEn_tete( Relation rel1 ){
        String[] valiny =  new String[ this.en_tete.length + rel1.en_tete.length - 1 ];

        System.arraycopy(this.en_tete, 0, valiny, 0, this.en_tete.length);

        System.arraycopy(rel1.en_tete, 0, valiny, this.en_tete.length , rel1.en_tete.length - 1 );

        return valiny;
    }

}