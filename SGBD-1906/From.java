package syntaxe;

import java.util.Arrays;

import javax.management.relation.RelationException;

import base.Relation;
import donnees.Data;
import donnees.Fichier;
import syntaxe.Join;
public class From extends Grammaire {
    
    public void setValue( Object[] value ) throws Exception {           //donner la table de départ
        
        Fichier f = new Fichier(String.valueOf(value[0]) , bdd);

        this.value = new Relation[1];

        this.value[0] = f.getRelation(String.valueOf(value));               //récupérer les données du fichier de la table

        defaut = (Relation) this.value[0];

    }

    public static Relation include( Relation[] l_r , String name ) throws Exception {           //pour sauvegarder une table , voir si elle existe 

        for ( int i = 0 ; i != l_r.length ; i++ ){
            if ( l_r[i].getNom().compareToIgnoreCase(name) == 0 ){
                return l_r[i];
            }
        }

        throw new Exception( " table invalide , aucune relation de ce type " );

    }

    public Relation requete( String[] req , Relation rel) throws Exception {          
        try{
            Relation table = (Relation) this.value[0];

            System.out.println(" transform : "+Arrays.toString(this.previous_gram.value));

            Relation new_r = new Relation();

            if ( this.next_gram != null ){                                                  //si la req ne se termine pas apres from

                if ( next_gram instanceof Where ){
                    Relation final_r = (Relation) this.next_gram.value[0];                          // la table final apres where et join

                    new_r = final_r.project(transformString(this.previous_gram.value , final_r));             //projection
                }
                if ( next_gram instanceof Join ){
                    Relation final_r = this.next_gram.action(req, (Relation) this.value[0] , bdd); 

                    final_r.printObj( final_r );

                    new_r = final_r.project(transformString(this.previous_gram.value , final_r));

                    new_r.printObj(new_r);

                }
                if ( next_gram instanceof Div ||  next_gram instanceof Diff ){
                    Relation final_r = this.next_gram.action(req, (Relation) this.value[0] , bdd); 
                    
                    new_r = final_r;
                }

            }else{                      

                System.out.println(" else miditra ");

                new_r = table.project(transformString(this.previous_gram.value , table));

            }

            return new_r;
        }catch( Exception e ){
            throw e;
        }
    }   

}
