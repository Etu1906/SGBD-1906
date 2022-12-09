-- initialisation
use default

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
create table note as nom string , etu number   , note number    end 
create table cours as nom string end 
-- insertion
insert into etudiant values 'etudiant1' , 1 end  
insert into etudiant col nom , etu  values  'etudiant2' , 2 end  
insert into etudiant col nom , etu  values  'etudiant5' , 3 end  


insert into note values 'Analyse' , 1 , 4 end
insert into note values 'Algebre' , 1 , 12 end


insert into note  col etu , note , nom values   2 , 6  , 'Analyse' end
insert into note  col etu , note , nom values   2 , 18 , 'Algebre'  end

insert into note values 'Algebre' , 3 , 4 end

insert into cours values 'Algebre' end
insert into cours values 'Analyse' end


-- selection 
select * from etudiant  end 
select * from etudiant where nom = etudiant1 end 
select * from etudiant where nom like etudiant end 

select nom from etudiant where nom like etudiant end
select note,etu from note end


select * from note where nom = Analyse and etu = 2 end
select * from note where nom = Analyse or note = 18 end

select * from note where nom = Analyse - note = 6 end


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
