package syntaxe;

import java.io.File;
import java.util.Vector;
import donnees.Fichier;
import base.Relation;
import syntaxe.Delete;

public class Show extends Grammaire{

    Fichier f ;

    public void analysSyntaxe( String[] req )throws Exception{
        if( req.length >= 3 ){
            throw new Exception(" syntaxe : SHOW + dbb ou table ");
        }

        if( req[1].compareToIgnoreCase("databases") != 0 && req[1].compareToIgnoreCase("tables") != 0  )
            throw new Exception(" voir seulement dbb ou table ");
    }

    public Object[] Show( String[] req , String bdd )throws Exception{
        
        if( bdd == null && req[1].compareToIgnoreCase("tables") == 0 ){
            throw new Exception(" vous n'aver pas choisit de  vase de données");
        }
        if( req[1].compareToIgnoreCase("tables") == 0 ){
            f = new Fichier(bdd);
        }

        if( req[1].compareToIgnoreCase("databases") == 0 ){
            f = new Fichier();
        }

        Vector<String> all_name = new Vector<String>();

        all_name.add(" listes "+req[1]);

        File[] liste = f.listFiles();
        for(File item : liste){
            String[] split = item.getName().split("\\.");
            all_name.add(split[0]);
        }

        return all_name.toArray();

    }
}
