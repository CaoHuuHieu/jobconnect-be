CREATE TABLE roles (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    code VARCHAR(50)
);

INSERT INTO roles(id, name, code) values
(1, 'Super Admin', 'SUPER_ADMIN'),
(2, 'Organization Admin', 'ORG_ADMIN'),
(3, 'Human Resource', 'HUMAN_RESOURCE')