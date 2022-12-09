select * from rel1 as lolo where nom like ha
select * from Inscription AS i div cours AS c on i.cours,i.lozitra / c.cours,c.lozitra
 
create table rel1(
    nom varchar(50),
    chien varchar(50)
);

create table hoho(
    chien varchar(50)
);

insert into rel1 values( 'huhu' , 'James' ),
('haha' , 'huhio'),
('Ralph' , 'lala'),
('yuoi' , 'fifi'),
('haha' , 'zozo')
;

insert into hoho values( 'James' ),
('huhio');


create table huhu(
    nom varchar( 10 )
);
create table lala(
    nom varchar( 10 ),
    prenom varchar( 10 )
);

select huhu.nom as h , lala.prenom as l from huhu join lala on huhu.nom = lala.prenom ;


select prenom from lulu join hoho on lulu.chien = hoho.chien

select * from hoho

select * from hoho join rel1 on hoho.chien = rel1.chien

select * from hoho AS hu join rel1  on hu.chien = rel1.chien

select * from rel1 join hoho on rel1.chien = hoho.chien where nom = ralp or nom = huhu

select * from rel1  where nom = ralp or nom = huhu


update huhu set nom = 'ralph' where nom = gigi end

create table lala as hoho String , lili String end

select * from etudiant
join etu on et.etu = e.nombre
end

select * from etudiant
join etu on et.etu = e.nombre
where etu = 24 
end


select * from etudiant join etu on etudiant.etu = etu.nombre join huhu on etudiant.nom = huhu.nom where etu = 24 or etu = 25 end





select * from etudiant join etu on etudiant.etu = etu.nombre join huhu on etudiant.nom = huhu.nom  where etu = 24 or etu = 25 end

select * from etudiant join etu on etudiant.etu = etu.nombre join huhu on etudiant.nom = huhu.nom  end


select * from etudiant join etu on etudiant.etu = etu.nombre end


select * from etudiant join cours on etudiant.cours = cours.cours  where cours = c1 end

 +------+------+--------+------+------+
| etu  | nom  | nombre | nom  | hoho |
+------+------+--------+------+------+
|   29 |    2 |     29 |    2 | lala |
|   24 |    2 |     24 |    2 | lala |
|   25 |    2 |     25 |    2 | lala |
+------+------+--------+------+------+

select * from etudiant join cours on etudiant.cours = cours.cours  where cours = c1 or nom = Ralph end