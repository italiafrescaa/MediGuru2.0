-- Database Name:
CREATE DATABASE user_manager_v1;

USE user_manager_v1;

-- USER TABLE STRUCTURE:
CREATE TABLE utenti(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome varchar(40) NOT NULL,
    cognome varchar(40) NOT NULL,
    email varchar(40) NOT NULL,
    password varchar(255) NOT NULL );