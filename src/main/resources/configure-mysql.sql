-- Use following command in terminal to run mysql db docker image
-- docker run --name recipe-app-mysql -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

-- Connect to mysql and run following lines as root user
-- Create Databases (you need to refresh the schemas to see the dbs once created)
CREATE DATABASE db_dev;
CREATE DATABASE db_prod;

-- Create database service accounts ('identified by' sets the password)
CREATE USER 'dev_user'@'localhost' IDENTIFIED BY 'devpassword';
CREATE USER 'prod_user'@'localhost' IDENTIFIED BY 'prodpassword';

-- Grants database DML basic operations rights (there might be a warning appearing saying that the grand was not successful
-- but it should still have worked, take a look at the users -> schemas privileges to ensure everything's ok)
GRANT SELECT ON db_dev.* to 'dev_user'@'localhost';
GRANT INSERT ON db_dev.* to 'dev_user'@'localhost';
GRANT DELETE ON db_dev.* to 'dev_user'@'localhost';
GRANT UPDATE ON db_dev.* to 'dev_user'@'localhost';
GRANT SELECT ON db_prod.* to 'prod_user'@'localhost';
GRANT INSERT ON db_prod.* to 'prod_user'@'localhost';
GRANT DELETE ON db_prod.* to 'prod_user'@'localhost';
GRANT UPDATE ON db_prod.* to 'prod_user'@'localhost';

-- For local installation, previous lines are sufficient, but for docker mysql, you need to add the following accounts
-- because domain is not localhost from within the container (it seems we are connecting from another computer in docker's eyes)
-- % means any host
CREATE USER 'dev_user'@'%' IDENTIFIED BY 'devpassword';
CREATE USER 'prod_user'@'%' IDENTIFIED BY 'prodpassword';

GRANT SELECT ON db_dev.* to 'dev_user'@'%';
GRANT INSERT ON db_dev.* to 'dev_user'@'%';
GRANT DELETE ON db_dev.* to 'dev_user'@'%';
GRANT UPDATE ON db_dev.* to 'dev_user'@'%';
GRANT SELECT ON db_prod.* to 'prod_user'@'%';
GRANT INSERT ON db_prod.* to 'prod_user'@'%';
GRANT DELETE ON db_prod.* to 'prod_user'@'%';
GRANT UPDATE ON db_prod.* to 'prod_user'@'%';
