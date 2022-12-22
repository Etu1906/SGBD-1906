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

