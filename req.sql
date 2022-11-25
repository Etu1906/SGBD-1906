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



