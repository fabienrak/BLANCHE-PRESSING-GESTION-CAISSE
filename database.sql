DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id_user INTEGER PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255),
    contact VARCHAR(255),
    role VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS client;
CREATE TABLE client (
    id_client INTEGER PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    adresse_livraison VARCHAR(255) NOT NULL,
    contact VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS sites;
CREATE TABLE sites (
    id_site INTEGER PRIMARY KEY AUTOINCREMENT,
    nom_site VARCHAR(255),
    lieu VARCHAR(255),
    contact VARCHAR(255),
    code_site VARCHAR(255)
);

CREATE TABLE articles (
	id_article INTEGER PRIMARY KEY AUTOINCREMENT,
	nom_article TEXT,
	prix INTEGER,
	prefix_code TEXT
);

DROP TABLE IF EXISTS marchandises;
CREATE TABLE marchandises (
    id_marchandise INTEGER PRIMARY KEY AUTOINCREMENT,
    type_marchandise VARCHAR(255),
    prix INTEGER,
    codage VARCHAR(255),
    nombre_article INTEGER,
    status INTEGER,	--	0 annuler, 1 en cour,	2 terminer
    client_id INTEGER,
    article_id INTEGER,
    FOREIGN KEY (client_id) REFERENCES clients(id_client),
    FOREIGN KEY (article_id) REFERENCES articles(id_article)
);

DROP TABLE IF EXISTS mouvement;
CREATE TABLE mouvement (
    id_mouvement INTEGER PRIMARY KEY,
    date_mouvement VARCHAR(255),
    type_mouvement VARCHAR(255) NOT NULL,
    montant INTEGER NOT NULL,
    avance INTEGER NOT NULL,
    reste_payer INTEGER NOT NULL,
    marchandise_id INTEGER NOT NULL,
    client_id INTEGER NOT NULL,
    users_id INTEGER NOT NULL,
    site_id INTEGER NOT NULL,
    FOREIGN KEY (marchandise_id)  REFERENCES marchandises(id_marchandise),
    FOREIGN KEY (client_id) REFERENCES client(id_client),
    FOREIGN KEY (users_id) REFERENCES users(id_user),
    FOREIGN KEY (site_id) REFERENCES site(id_site)
);

DROP TABLE IF EXISTS commande;
CREATE TABLE commande (
    id_commande INTEGER PRIMARY KEY,
    date_commande VARCHAR(255),
    date_livraison VARCHAR(255),
    mouvement_id INTEGER NOT NULL,
    codage VARCHAR(255) NOT NULL,
    -- etat_cmd INTEGER NOT NULL DEFAULT 0,    -- 0 annuler, 1 accepter, 2 refaire
    etat_cmd INTEGER NOT NULL DEFAULT 0,    -- DELIVERED : livrer, CANCELLED : annuler, PROGRESS : en cour, SUCCESS : fini
    FOREIGN KEY(mouvement_id) REFERENCES mouvement(id_mouvement)
);

DROP TABLE IF EXISTS code;
CREATE TABLE code (
    id_code INTEGER PRIMARY KEY,
    prefix VARCHAR(255) NOT NULL
);
