CREATE TABLE people (
  id VARCHAR(36) PRIMARY KEY,  -- UUID length
  cpf VARCHAR(11) NOT NULL,  -- CPF length in Brazil
  name VARCHAR(100) NOT NULL,  -- Reasonable length for names
  createdAt TIMESTAMP DEFAULT NOW(),
  updatedAt TIMESTAMP,
  password VARCHAR(60) NOT NULL,  -- Length for hashed passwords
  isAdmin BOOLEAN NOT NULL DEFAULT FALSE
);