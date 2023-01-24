CREATE TABLE customer(
    id INT AUTO_INCREMENT primary key,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL unique
);