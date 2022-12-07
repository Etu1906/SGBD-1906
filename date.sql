create table pointage(
    numeroEmploye int,
    dateHeureDepart datetime,
    dateHeureSotrie datetime
);


insert into pointage values(  1 ,'2022-01-02:10:00:00' , '2022-01-02:20:00:00' );
insert into pointage values(  2 ,'2022-01-02:12:00:00' , '2022-01-02:13:00:00' );
insert into pointage values(  3 ,'2022-01-02:08:00:00' , '2022-01-02:21:00:00' );
insert into pointage values(  4 ,'2022-01-02:06:00:00' , '2022-01-02:12:00:00' );
insert into pointage values(  5 ,'2022-01-02:22:00:00' , '2022-01-02:23:00:00' );
insert into pointage values(  6 ,'2022-01-02:10:00:00' , '2022-01-02:11:00:00' );
insert into pointage values(  6 ,'2022-02-02:10:00:00' , '2022-02-02:11:00:00' );
insert into pointage values(  6 ,'2022-02-03:10:00:00' , '2022-02-03:11:00:00' );
insert into pointage values(  6 ,'2022-02-04:10:00:00' , '2022-02-04:21:00:00' );
insert into pointage values(  6 ,'2022-02-05:10:00:00' , '2022-02-05:21:00:00' );
insert into pointage values(  6 ,'2022-01-15:10:00:00' , '2022-01-15:11:00:00' );
insert into pointage values(  7 ,'2022-01-15:10:00:00' , '2022-01-15:11:00:00' );
insert into pointage values(  8 ,'2022-01-30:10:00:00' , '2022-01-30:11:00:00' );
insert into pointage values(  9 ,'2022-01-25:10:00:00' , '2022-01-26:08:00:00' );
insert into pointage values(  10 ,'2022-01-25:10:30:00' , '2022-01-26:08:00:00' );
insert into pointage values(  1 ,'2022-01-25:10:30:00' , to_date(  to_char( now()::timestamp(0) , 'MI:SS'   ) , 'MI:SS' ) );
insert into pointage values(  1 ,'2022-12-06:19:30:00' , '2022-12-06:20:30:00' );

select now()::timestamp(0) - dateHeureDepart from pointage;
-- Total heure niasan  olona iray par jour ? par semaine ? par mois ?

-- jour
select timeDiff( dateHeureSotrie , dateHeureDepart ) as heure_par_empl , numeroEmploye, extract( year from dateHeureDepart ) as annee , ExtRACT( day from dateHeureDepart ) as jour  ,extract( month from dateHeureDepart ) as mois
from pointage
group by ExtRACT( year from dateHeureDepart ) , ExtRACT( month from dateHeureDepart ), ExtRACT( day from dateHeureDepart )  ,numeroEmploye 
;

-- mois
select   hour ( sum(timeDiff(  dateHeureSotrie  ,   dateHeureDepart )) )   as heure_par_empl , numeroEmploye, extract( year from dateHeureDepart ) as annee  ,extract( month from dateHeureDepart ) as mois
from pointage
group by ExtRACT( year from dateHeureDepart ) , ExtRACT( month from dateHeureDepart ),numeroEmploye
;


-- semaine
select hour ( sum(timeDiff(  dateHeureSotrie  ,   dateHeureDepart )) ) as heure_par_empl , numeroEmploye, extract( year from dateHeureDepart ) as annee  ,extract( month from dateHeureDepart ) as mois , extract( week from dateHeureDepart ) as week
from pointage
group by ExtRACT( year from dateHeureDepart ) , ExtRACT( month from dateHeureDepart ), ExtRACT( week from dateHeureDepart )  ,numeroEmploye
;


-- Total heure niasan olona rehetra par jour ? par semaine ? par mois ?


-- jour
select hour ( sum(timeDiff(  dateHeureSotrie  ,   dateHeureDepart )) ) , ExtRACT( year from dateHeureDepart )  as annee , ExtRACT( month from dateHeureDepart ) as mois , ExtRACT( day from dateHeureDepart ) as jour
from pointage
group by ExtRACT( year_month from dateHeureDepart ) , ExtRACT( day from dateHeureDepart ) 
;
-- mois
select hour ( sum(timeDiff(  dateHeureSotrie  ,   dateHeureDepart )) ) , ExtRACT( year from dateHeureDepart )  as annee , ExtRACT( month from dateHeureDepart ) as mois
from pointage
group by ExtRACT( year_month from dateHeureDepart )  
;


-- semaine

-- Liste olona tsy nahavita 40h/semaine (heure tsy vitany eo akaikiny)


select (40 - hour ( sum(timeDiff(  dateHeureSotrie  ,   dateHeureDepart )) )) as heure_requise , numeroEmploye , ExtRACT( week from dateHeureDepart )
from pointage
having sum((extract( hour from dateHeureSotrie ) - extract( hour from dateHeureDepart ))) < 40
group by ExtRACT( year from dateHeureDepart ) ,   ExtRACT( week from dateHeureDepart ) , numeroEmploye  
;

create or replace view heure_req as (
    select (40 - hour ( sum(timeDiff(  dateHeureSotrie  ,   dateHeureDepart )) )) as heure_requise , numeroEmploye , ExtRACT( week from dateHeureDepart ) as week
    from pointage
    group by ExtRACT( year from dateHeureDepart ) ,   ExtRACT( week from dateHeureDepart ) , numeroEmploye  
);





select heure_requise , numeroEmploye , week
from heure_req
where heure_requise < 40;



-- Isanny olona niasa par jour /semaine/mois ? (Misy pointage)

-- jour
select count( distinct( numeroEmploye ) ) as nbr , extract( year from dateHeureDepart ) as annee , extract( month from dateHeureDepart ) as mois , extract( day from dateHeureDepart ) as jour  from pointage
group by ExtRACT( year_month from dateHeureDepart ) ,ExtRACT( day from dateHeureDepart )
;


-- mois
select count( distinct( numeroEmploye ) ) as nbr , extract( year from dateHeureDepart ) as annee , extract( month from dateHeureDepart ) as mois , extract( day from dateHeureDepart ) as jour  from pointage
group by ExtRACT( year_month from dateHeureDepart ) 
;

-- semaine
select count( distinct( numeroEmploye ) ) , extract( year from dateHeureDepart ) , extract( month from dateHeureDepart )  , extract( week from dateHeureDepart )  from pointage
group by ExtRACT( year_month from dateHeureDepart ) ,ExtRACT( week from dateHeureDepart )
;

-- mois 
select count( distinct( numeroEmploye ) ) , extract( year from dateHeureDepart ) , extract( month from dateHeureDepart )  from pointage
group by ExtRACT( year from dateHeureDepart ) ,ExtRACT( month from dateHeureDepart )
;