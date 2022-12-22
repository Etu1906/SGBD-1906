DOCUMENTATION GLOBALE DU PROJET:
Objectif : créer son propre language sql

initialisation : 
choisir dbb : use db1

sortir : 
bye 
+ bouton entrée

syntaxe création :
create table tab as col1 type , col2 type end  (  type number et string pour le moment )
create database nomDatabase end

syntaxe insertion : insert into ... col ... values ... , .... end
insert into tab col col1 , col2 values val1 , val2 end iks
insert into tab values val1 , val2 end
    si string :  values  'huhu'
    si number : values 2

syntaxe show :
show tables
show databases

syntaxe select : 
select * from table where nom = huhu ( pas de cote ) end 
select * from table where nom like huhu ( pas de cote ) end 
select * from table where nom like huhu and prenom = huhu ( pas de cote ) end 
select * from table where nom like huhu or prenom = huhu ( pas de cote ) end 
select * from table where nom like huhu - prenom = huhu ( pas de cote ) ( difference ) end 
select * from tab1 join tab2 on tab1.col1 = tab2.col2 end 
select col1,col2,col3 from tab1 end ( pas d'espace entre les colonnes )
select * from tab1 div tab2 on tab1.col1,tab1.col2 / tab2.col1,tab2.col2 end ( division )  
select * from tab1 diff tab2 end (différence) ( Exception ne marche pas encore )
select * from tab1 join tab2 on tab1.col1 = tab2.col2 end ( 1ere colonne tab1 et 2 colonne tab2 )



syntaxe delete :
delete from tab end
delete from tab where col1 = val1 end 
delete from tab where col1 like val1 end

syntaxe update : 
update tab set col1 = val1 where col = val end 
update tab set col1 = val1 , col2 = val2  where col = val end 


=======
exemple de syntaxe : script.sql

initialisation : 
choisir dbb : use db1

sortir : 
bye 
+ bouton entrée

syntaxe création :
create table tab as col1 type , col2 type end  (  type number et string pour le moment )
create database nomDatabase end

syntaxe insertion : insert into ... col ... values ... , .... end
insert into tab col col1 , col2 values val1 , val2 end iks
insert into tab values val1 , val2 end
    si string :  values  'huhu'
    si number : values 2

syntaxe show :
show tables
show databases

syntaxe select : 
select * from table where nom = huhu ( pas de cote ) end 
select * from table where nom like huhu ( pas de cote ) end 
select * from table where nom like huhu and prenom = huhu ( pas de cote ) end 
select * from table where nom like huhu or prenom = huhu ( pas de cote ) end 
select * from table where nom like huhu - prenom = huhu ( pas de cote ) ( difference ) end 
select * from tab1 join tab2 on tab1.col1 = tab2.col2 end 
select col1,col2,col3 from tab1 end ( pas d'espace entre les colonnes )
select * from tab1 div tab2 on tab1.col1,tab1.col2 / tab2.col1,tab2.col2 end ( division )  
select * from tab1 diff tab2 end (différence) ( Exception ne marche pas encore )
select * from tab1 join tab2 on tab1.col1 = tab2.col2 end ( 1ere colonne tab1 et 2 colonne tab2 )



syntaxe delete :
delete from tab end
delete from tab where col1 = val1 end 
delete from tab where col1 like val1 end

syntaxe update : 
update tab set col1 = val1 where col = val end 
update tab set col1 = val1 , col2 = val2  where col = val end 



exemple syntaxe : 

-- initialisation
use defaut

-- create dbb
create database test end 
-- use dbb
use test

-- show dbb
show databases end
-- show table
show tables end

-- creation table
create table etudiant as  nom string  , etu  number  end  
create table note as id_note number ,  id_cours number , etu number   , note number    end 
create table cours as id_c number , nom string end 
-- insertion
insert into etudiant values 'etudiant1' , 1 end  
insert into etudiant col nom , etu  values  'etudiant2' , 2 end  
insert into etudiant col nom , etu  values  'etudiant5' , 3 end  


insert into note values 1 , 2 , 1 , 4 end
insert into note values 2 , 1 , 1 , 12 end


-- erreur
insert into etudiant col nom , etu  values  etudiant5 , 3 end  
insert into etudiant col nom , etu  values  'etudiant5' , lala end  


insert into note  col id_note , etu , note , id_cours values   3 ,  2 , 6  , 2 end
insert into note col id_note , etu , note , id_cours values 4  , 2 , 18 , 1  end

insert into note values  5 , 1 , 3 , 4 end

insert into cours values 1 , 'Algebre' end
insert into cours values 2 ,  'Analyse' end


-- selection 
select * from etudiant  end 
select * from etudiant where nom = etudiant1 end 
select * from etudiant where nom like etudiant end 
select nom from etudiant where nom like etudiant end
select note,etu from note end

-- cours d'analyse
select * from note where id_cours = 2 and etu = 2 end
select * from note where id_cours = 2 or note = 18 end

select * from etudiant join note on etudiant.etu = note.etu end 

-- division
create table eleve as nom string , salle string end
create table salle as nom_salle string end
insert into eleve values 'rakoto' , 's1' end
insert into eleve values 'rakoto' , 's2' end
insert into eleve values 'rakoto' , 's3' end
insert into eleve values 'rabe' , 's1' end
insert into eleve values 'rabe' , 's2' end
insert into eleve values 'rasoa' , 's1' end
insert into salle values 's1' end
insert into salle values 's2' end
insert into salle values 's3' end
 
select * from eleve div salle on eleve.salle / salle.nom_salle end

-- update
update etudiant set nom = 'etudiant3' where etu = 2 end

-- delete
delete from note where etu = 2 end
delete from cours end

-- drop table
drop table note end

-- drop database 
drop database test end

-- quit
bye

+entrée



résultats : 
base de données
    defaut
    -- test :
        etudiant
            nom         | etu |
            etudiant1       1
            etudiant2       2
            etudiant5       3

        note 
            id_note |   id_cours         | etu |    note
            1           2                    1       4
            2           1                   1       12
            3           2                   2       6
            4           1                   2       18
            5           1                   3       4

        cours 
            id_c |  nom
            1       Algebre
            2       Analyse
        eleve
            nom    |     salle
            rakoto        s1
            rakoto        s2 
            rakoto        s3
            rabe          s1
            rabe          s2
            rasoa         s1

        salle 
            nom_salle
            s1
            s2
            s3  



