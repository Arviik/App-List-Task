CREATE DATABASE IF NOT EXISTS `app-list-task`;
USE `app-list-task`;

DROP TABLE IF EXISTS `compte`;
CREATE TABLE IF NOT EXISTS `compte`(
    `id_compte` int(11) NOT NULL AUTO_INCREMENT,
    `nom` varchar(255) NOT NULL,
    `prenom` varchar(255) NOT NULL,
    `email` varchar(255) UNIQUE NOT NULL,
    `mdp` varchar(255) NOT NULL,
    `estAdmin` boolean DEFAULT FALSE,
    PRIMARY KEY (`id_compte`)
);

DROP TABLE IF EXISTS `gere`;
CREATE TABLE IF NOT EXISTS `gere`(
    `ref_compte` int(11) NOT NULL,
    `ref_liste` int(11) NOT NULL,
    PRIMARY KEY (`ref_compte`,`ref_liste`)
);

DROP TABLE IF EXISTS `liste`;
CREATE TABLE IF NOT EXISTS `liste`(
    `id_liste` int(11) NOT NULL AUTO_INCREMENT,
    `libelle` varchar(255) NOT NULL,
    `description` varchar(255),
    PRIMARY KEY (`id_liste`)
);

DROP TABLE IF EXISTS `tache`;
CREATE TABLE IF NOT EXISTS `tache`(
    `id_tache` int(11) NOT NULL AUTO_INCREMENT,
    `libelle` varchar(255) NOT NULL,
    `description` varchar(255),
    `difficulte` varchar(255),
    `date_debut` date NOT NULL,
    `date_fin` date NOT NULL,
    `date_butoir` date NOT NULL,
    `ref_compte` int(11) NOT NULL,
    `ref_liste` int(11) NOT NULL,
    `ref_etat` int(11) DEFAULT '1',
    `ref_type` int(11),
    PRIMARY KEY (`id_tache`)
);

DROP TABLE IF EXISTS `type`;
CREATE TABLE IF NOT EXISTS `type`(
    `id_type` int(11) NOT NULL AUTO_INCREMENT,
    `libelle` varchar(255) NOT NULL,
    `ref_type` int(11),
    PRIMARY KEY (`id_type`)
);

DROP TABLE IF EXISTS `etat`;
CREATE TABLE IF NOT EXISTS `etat`(
    `id_etat` int (11) NOT NULL AUTO_INCREMENT,
    `etat` varchar(255) NOT NULL,
    PRIMARY KEY (`id_etat`)
);

ALTER TABLE `gere`
    ADD CONSTRAINT `fk_gere_compte` FOREIGN KEY (`ref_compte`) REFERENCES `compte` (`id_compte`),
    ADD CONSTRAINT `fk_gere_liste` FOREIGN KEY (`ref_liste`) REFERENCES `liste` (`id_liste`);

ALTER TABLE `tache`
    ADD CONSTRAINT `fk_tache_compte` FOREIGN KEY (`ref_compte`) REFERENCES  `compte` (`id_compte`),
    ADD CONSTRAINT `fk_tache_liste` FOREIGN KEY (`ref_liste`) REFERENCES  `liste` (`id_liste`),
    ADD CONSTRAINT `fk_tache_type` FOREIGN KEY (`ref_type`) REFERENCES `type` (`id_type`),
    ADD CONSTRAINT `fk_tache_etat` FOREIGN KEY (`ref_etat`) REFERENCES `etat` (`id_etat`);

ALTER TABLE `type`
    ADD CONSTRAINT `fk_type_type` FOREIGN KEY (`ref_type`) REFERENCES `type` (`id_type`);

INSERT INTO `etat`(etat)
VALUES ('à finir'),
       ('en retard'),
       ('expiré'),
       ('terminé');

INSERT INTO `type`(libelle)
VALUES ('développement'),
       ('production'),
       ('édition');