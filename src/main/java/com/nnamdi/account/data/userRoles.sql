CREATE DATABASE IF NOT EXISTS  AccountMicroservice;
# USE AccountMicroservice;
# DROP TABLE IF EXISTS Account;
CREATE TABLE IF NOT EXISTS Account  (
                                        account_id INT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(500) NOT NULL,
                                        address VARCHAR(5000) NOT NULL,
                                        phone_number nvarchar(512) NOT NULL,
                                        email nvarchar(100) NOT NULL,
                                        password nvarchar(512) NOT NULL,
                                        username nvarchar(512) NOT NULL

);

# DROP TABLE IF EXISTS Roles;
CREATE TABLE IF NOT EXISTS Roles (
                                     role_id int(11) NOT NULL AUTO_INCREMENT,
                                     name VARCHAR(500) NOT NULL,
                                     PRIMARY KEY (role_id)

);

INSERT INTO Roles(name) VALUES('ROLE_USER');
INSERT INTO Roles(name) VALUES('ROLE_ADMIN');
# DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS user_role (
                                        user_role_id int(11) NOT NULL AUTO_INCREMENT,
                                        account_id INT NOT NULL ,
                                        role_id INT(6) NOT NULL,
                                        FOREIGN KEY (account_id) REFERENCES Account(account_id),
                                        FOREIGN KEY (role_id) REFERENCES Roles(role_id),
                                        PRIMARY KEY (user_role_id)


);



