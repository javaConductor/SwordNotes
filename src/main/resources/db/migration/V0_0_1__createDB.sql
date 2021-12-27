CREATE ROLE "sword_explorer" LOGIN
        NOSUPERUSER
        NOCREATEDB
        NOCREATEROLE
        INHERIT
        NOREPLICATION
        CONNECTION LIMIT -1
        PASSWORD '4$wbX*cbC-GxC2i';

create database  "SwordExplorer"
  with owner sword_explorer;
