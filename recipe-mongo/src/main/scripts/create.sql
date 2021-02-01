# Use to run mysql db docker image:
# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
# or
# docker run --name mysqldb -p 3306:3306 -v /my/own/datadir:/var/lib/mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# Connect to mysql and run as root

# 1. Create databases
CREATE DATABASE IF NOT EXISTS sfg_recipe_dev;
CREATE DATABASE IF NOT EXISTS sfg_recipe_prod;

# 2. Create db accounts
CREATE USER 'sfg_dev_user'@'localhost' IDENTIFIED BY 'dev';
CREATE USER 'sfg_dev_user'@'%' IDENTIFIED BY 'dev';
CREATE USER 'sfg_prod_user'@'localhost' IDENTIFIED BY 'prod';
CREATE USER 'sfg_prod_user'@'%' IDENTIFIED BY 'prod';

# 3. Database grants
GRANT INSERT, UPDATE, DELETE, SELECT
    ON sfg_recipe_dev.*
    TO 'sfg_dev_user'@'localhost';
GRANT INSERT, UPDATE, DELETE, SELECT
    ON sfg_recipe_dev.*
    TO 'sfg_dev_user'@'%';

GRANT INSERT, UPDATE, DELETE, SELECT
    ON sfg_recipe_prod.*
    TO 'sfg_prod_user'@'localhost';
GRANT INSERT, UPDATE, DELETE, SELECT
    ON sfg_recipe_prod.*
    TO 'sfg_prod_user'@'%';
