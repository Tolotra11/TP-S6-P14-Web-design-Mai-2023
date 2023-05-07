CREATE TABLE Admin (
  id          SERIAL NOT NULL, 
  nom         varchar(50) NOT NULL, 
  prenom      varchar(50) NOT NULL, 
  identifiant varchar(50) NOT NULL, 
  mdp         varchar(100) NOT NULL, 
  PRIMARY KEY (id));
CREATE TABLE article (
  id              SERIAL NOT NULL, 
  resume          text NOT NULL, 
  titre           varchar(100) NOT NULL, 
  contenu         text NOT NULL, 
  categorieid     int4 NOT NULL, 
  Adminid         int4 NOT NULL, 
  etat            int2 DEFAULT 0 NOT NULL, 
  datePublication timestamp DEFAULT NULL, 
  PRIMARY KEY (id));

CREATE TABLE categorie (
  id     SERIAL NOT NULL, 
  nomCat varchar(100) NOT NULL UNIQUE, 
  PRIMARY KEY (id));
CREATE TABLE image (
  image     varchar(100) NOT NULL, 
  articleid int4 NOT NULL, 
  dateImage date DEFAULT CURRENT_DATE NOT NULL);
ALTER TABLE image ADD CONSTRAINT FKimage550475 FOREIGN KEY (articleid) REFERENCES article (id);
ALTER TABLE article ADD CONSTRAINT FKarticle190122 FOREIGN KEY (categorieid) REFERENCES categorie (id);
ALTER TABLE article ADD CONSTRAINT FKarticle245446 FOREIGN KEY (Adminid) REFERENCES Admin (id);

INSERT INTO categorie VALUES(DEFAULT,'Actualite');
INSERT INTO categorie VALUES(DEFAULT,'Produit');
INSERT INTO categorie VALUES(DEFAULT,'Publicité');
INSERT INTO categorie VALUES(DEFAULT,'Annonce');


INSERT INTO Admin VALUES(DEFAULT,'Ralijaona','Tolotra','Admin@admin.mg',md5('root'));

ALTER TABLE image
ALTER COLUMN dateImage TYPE timestamp;

CREATE OR REPLACE FUNCTION recentImage(Integer) RETURNS VARCHAR AS $$
  SELECT image FROM image WHERE articleId=$1 ORDER BY dateImage DESC LIMIT 1;
$$ LANGUAGE SQL;

CREATE OR REPLACE View v_article AS 
SELECT a.*,nomCat,nom,prenom,recentImage(a.id) AS nomImage FROM article a 
JOIN categorie c 
ON a.categorieId = c.id
JOIN admin ad
ON a.Adminid = ad.id;

ALTER TABLE article 
ALTER COLUMN titre TYPE text;