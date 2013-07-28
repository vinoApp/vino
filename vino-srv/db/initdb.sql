-- Drop tables
DROP TABLE IF EXISTS cellar;
DROP TABLE IF EXISTS bottles;
DROP TABLE IF EXISTS domains;
DROP TABLE IF EXISTS aocs;
DROP TABLE IF EXISTS regions;

-- Create data tables
CREATE TABLE IF NOT EXISTS regions (
  regionID   INT PRIMARY KEY AUTO_INCREMENT,
  regionName TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS aocs (
  aocID    INT PRIMARY KEY AUTO_INCREMENT,
  aocName  TEXT NOT NULL,
  regionID INT  NOT NULL,
  FOREIGN KEY (regionID) REFERENCES regions (regionID)
);

CREATE TABLE IF NOT EXISTS domains (
  domainID   INT PRIMARY KEY AUTO_INCREMENT,
  domainName TEXT NOT NULL,
  aocID      INT  NOT NULL,
  FOREIGN KEY (aocID) REFERENCES aocs (aocID)
);

CREATE TABLE IF NOT EXISTS bottles (
  bottleID     INT PRIMARY KEY AUTO_INCREMENT,
  domainID     INT         NOT NULL,
  vintage      INT         NOT NULL,
  barcode      VARCHAR(30) NOT NULL UNIQUE,
  stickerImage LONGBLOB,
  FOREIGN KEY (domainID) REFERENCES domains (domainID)
);

CREATE TABLE IF NOT EXISTS cellar (
  bottleID INT PRIMARY KEY AUTO_INCREMENT,
  qty      INT NOT NULL,
  FOREIGN KEY (bottleID) REFERENCES bottles (bottleID)
);

-- Insert data about regions and aocs (regions and AOCs are not supposed to change)
INSERT INTO regions (regionName) VALUES ('Médoc');
INSERT INTO regions (regionName) VALUES ('Libournais');
INSERT INTO regions (regionName) VALUES ('Blayais');
INSERT INTO regions (regionName) VALUES ('Graves');

INSERT INTO aocs (aocName, regionID) VALUES ('Margaux', 1);
INSERT INTO aocs (aocName, regionID) VALUES ('Moulis', 1);
INSERT INTO aocs (aocName, regionID) VALUES ('Listrac', 1);
INSERT INTO aocs (aocName, regionID) VALUES ('Saint-Julien', 1);
INSERT INTO aocs (aocName, regionID) VALUES ('Pauillac', 1);
INSERT INTO aocs (aocName, regionID) VALUES ('Saint-Estèphe', 1);

INSERT INTO aocs (aocName, regionID) VALUES ('Fronsac', 2);
INSERT INTO aocs (aocName, regionID) VALUES ('Pomerol', 2);
INSERT INTO aocs (aocName, regionID) VALUES ('Lalande de Pomerol', 2);
INSERT INTO aocs (aocName, regionID) VALUES ('Saint-Emilion', 2);
INSERT INTO aocs (aocName, regionID) VALUES ('Montagne Saint-Emilion', 2);
INSERT INTO aocs (aocName, regionID) VALUES ('Lussac Saint-Emilion', 2);
INSERT INTO aocs (aocName, regionID) VALUES ('Puisseguin Saint-Emilion', 2);
INSERT INTO aocs (aocName, regionID) VALUES ('Cotes-de-Castillon', 2);
INSERT INTO aocs (aocName, regionID) VALUES ('Bordeaux Cotes-de-Francs', 2);

INSERT INTO aocs (aocName, regionID) VALUES ('Blaye', 3);
INSERT INTO aocs (aocName, regionID) VALUES ('Cotes-de-Blaye', 3);
INSERT INTO aocs (aocName, regionID) VALUES ('Blaye-Cotes-de-Bordeaux', 3);
INSERT INTO aocs (aocName, regionID) VALUES ('Cotes-de-Bourg', 3);

INSERT INTO aocs (aocName, regionID) VALUES ('Graves', 4);
INSERT INTO aocs (aocName, regionID) VALUES ('Graves Superieurs', 4);
INSERT INTO aocs (aocName, regionID) VALUES ('Pessac-Léognan', 4);
INSERT INTO aocs (aocName, regionID) VALUES ('Cérons', 4);

-- Fake

INSERT INTO domains (domainName, aocID) VALUES ('Haut-Brion', 22);
INSERT INTO domains (domainName, aocID) VALUES ('Pape Clément', 22);
INSERT INTO domains (domainName, aocID) VALUES ('Petrus', 8);

INSERT INTO bottles (domainID, vintage, barcode) VALUES (1, 2008, '1');
INSERT INTO bottles (domainID, vintage, barcode) VALUES (3, 2005, '2');
INSERT INTO bottles (domainID, vintage, barcode) VALUES (2, 2003, '3');

INSERT INTO cellar (bottleID, qty) VALUES (1, 12);
