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
                                     role_id INT  PRIMARY KEY AUTO_INCREMENT,
                                     name VARCHAR(500) NOT NULL

);

# DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS UserRole (
                                        user_role_id INT PRIMARY KEY,
                                        account_id INT NOT NULL ,
                                        role_id INT(6) NOT NULL,
                                        FOREIGN KEY (account_id) REFERENCES Account(account_id),
                                        FOREIGN KEY (role_id) REFERENCES Roles(role_id)


);

