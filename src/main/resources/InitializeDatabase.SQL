DROP DATABASE IF EXISTS intraclass;
CREATE DATABASE intraclass;

CREATE USER IF NOT EXISTS 'userIntraClass'@'localhost' IDENTIFIED BY 'userIntraClassPW';
GRANT ALL ON intraclass.* TO 'userIntraClass'@'localhost';
FLUSH PRIVILEGES;