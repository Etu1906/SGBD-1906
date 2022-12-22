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
