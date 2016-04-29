-- Team 7
-- THANH NGO tcn33
-- MICHAEL REID mar557
-- JUSTIN CHONG jsc224

DROP DATABASE IF EXISTS yabe;
CREATE DATABASE IF NOT EXISTS yabe;
USE yabe;

DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS representative;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS emails;
DROP TABLE IF EXISTS auction;
DROP TABLE IF EXISTS bidsOn;
DROP TABLE IF EXISTS alert;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS computer;
DROP TABLE IF EXISTS graphicCard;
DROP TABLE IF EXISTS hardDrive;
DROP TABLE IF EXISTS hasHardDrive;
DROP TABLE IF EXISTS computerCamera;
DROP TABLE IF EXISTS cameraSupports;
DROP TABLE IF EXISTS connection;
DROP TABLE IF EXISTS supportsConnection;
DROP TABLE IF EXISTS bluetooth;
DROP TABLE IF EXISTS wifi;
DROP TABLE IF EXISTS gps;
DROP TABLE IF EXISTS processor;
DROP TABLE IF EXISTS runByProcessor;
DROP TABLE IF EXISTS laptop;
DROP TABLE IF EXISTS desktop;
DROP TABLE IF EXISTS handheld;

CREATE TABLE account (
    username CHAR(30),
    password CHAR(40) NOT NULL,
    sessionId CHAR(32),
    PRIMARY KEY (username)
);

-- admin should not appear in the 
-- representative or the user table

CREATE TABLE admin(
    username CHAR(30),
    PRIMARY KEY (username),
    FOREIGN KEY (username) REFERENCES account(username)
);

CREATE TABLE representative(
    username CHAR(30) PRIMARY KEY,
    name CHAR(255) NOT NULL,
    email CHAR(255) NOT NULL,
    FOREIGN KEY (username) REFERENCES account(username)
);

CREATE TABLE user(
    username CHAR(30) PRIMARY KEY,
    name CHAR(255) NOT NULL,
    email CHAR(255) NOT NULL,
    address CHAR( 200 ) NOT NULL,
    profilePicture CHAR(36),
    FOREIGN KEY (username) REFERENCES account(username)
);

-- Merge table Date and Time into table email ( make date and time an attribute )
CREATE TABLE emails (
    fromEmail CHAR(255),
    toEmail CHAR(255),
    time DATETIME,
    subject TEXT CHARACTER SET utf8,
    content TEXT CHARACTER SET utf8,
    PRIMARY KEY (fromEmail, toEmail, time)
);

-- From the item part to every item specifications ( via ISA) the non NULL constraint is checked dynamically using triggers as item can either be on sale or on alert, if the item is on alert then it is a virtual item and can have NULL value for anything.

CREATE TABLE item(
    itemId INT,
    name VARCHAR(100),
    manufacturer CHAR(30),
    cond ENUM('NEW', 'NEW_OTHER','MANU_REFUR' ,'SELL_REFUR','USED','FOR_PARTS') ,
    description TEXT CHARACTER SET utf8,
    picture CHAR(36),
    PRIMARY KEY (itemId)
);

-- Merge Auction with sells and purchased(By someone) 
CREATE TABLE auction (
    itemId INT,
    PRIMARY KEY (itemId),
	seller CHAR(30) NOT NULL,
	
    purchaser CHAR(30),
    soldPrice FLOAT,
    soldTime DATETIME,

    openDate DATETIME NOT NULL,
    closeDate DATETIME NOT NULL,
    minimumPrice FLOAT NOT NULL,
    minimumIncrement FLOAT NOT NULL, 
    FOREIGN KEY (purchaser) REFERENCES user(username),
    FOREIGN KEY (seller) REFERENCES user(username),
    FOREIGN KEY (itemId) REFERENCES item(itemId)
);

-- Merge bidsOn with time ( make time an attribute ) 
CREATE TABLE bidsOn (
    itemId INT,
    bidder CHAR(30),
    time DATETIME,

    amount FLOAT,
    PRIMARY KEY (itemId, bidder, time ),
    FOREIGN KEY (itemId) REFERENCES auction(itemId),
    FOREIGN KEY (bidder) REFERENCES user(username)
);

CREATE TABLE alert (
    username CHAR(30),
    itemId INT,
    PRIMARY KEY (username, itemId),
    FOREIGN KEY (username) REFERENCES user(username),
    FOREIGN KEY (itemId) REFERENCES item (itemId)
);

CREATE TABLE graphicCard(
    graphicCardName CHAR(30),
    manufacturer CHAR(30),
    description TEXT CHARACTER SET utf8,
    PRIMARY KEY (graphicCardName)
);

-- Merge computer with has(Graphic card)
CREATE TABLE computer(
    itemId INT,
    ram INT,
    brandName CHAR(30),
    weight FLOAT,
    operatingSystem CHAR(30),

    screenType CHAR(30),
    screenWidth FLOAT,
    screenHeight FLOAT,
    screenResolutionX INT,
    screenResolutionY INT,

    sizeWidth FLOAT,
    sizeHeight FLOAT,
    sizeDepth FLOAT,
    color CHAR(6),
    batteryCapacity INT,

    graphicCardName CHAR(30),
    PRIMARY KEY (itemId),
    FOREIGN KEY (itemId) REFERENCES item(itemId),
    FOREIGN KEY (graphicCardName) REFERENCES graphicCard(graphicCardName)
);


CREATE TABLE hardDrive (
    driveId INT,
    driveType ENUM ('HHD','SSD'),
    driveName CHAR(30),
    PRIMARY KEY (driveId)
);

CREATE TABLE hasHardDrive (
    itemId INT,
    driveId INT,
    size INT,
    PRIMARY KEY (itemId, driveId),
    FOREIGN KEY (itemId) REFERENCES computer(itemId),
    FOREIGN KEY (driveId) REFERENCES hardDrive(driveId)
);

CREATE TABLE computerCamera (
    cameraId INT,
    hasFlash TINYINT(1), 
    megapixel FLOAT,
    isBack TINYINT(1),
    PRIMARY KEY (cameraId)
);

CREATE TABLE cameraSupports (
    itemId INT,
    cameraId INT,
    PRIMARY KEY (itemId, cameraId),
    FOREIGN KEY (cameraId) REFERENCES computerCamera(cameraId),
    FOREIGN KEY (itemId) REFERENCES computer(itemId)
);

-- Merge up back and front camera ( try adding isBack? Boolean value)

CREATE TABLE connection(
    connectionId INT,
    PRIMARY KEY (connectionId)
);

CREATE TABLE supportsConnection (
    itemId INT,
    connectionId INT,
    PRIMARY KEY (itemId,connectionId),
    FOREIGN KEY (itemId) REFERENCES computer(itemId),
    FOREIGN KEY (connectionId) REFERENCES connection (connectionId)
);


CREATE TABLE bluetooth(
    connectionId INT,
    version CHAR(10),
    PRIMARY KEY (connectionId),
    FOREIGN KEY (connectionId) REFERENCES connection(connectionId)
);

CREATE TABLE wifi (
    connectionId INT,
    bandwidth INT,
    PRIMARY KEY (connectionId),
    FOREIGN KEY (connectionId) REFERENCES connection(connectionId)
);

CREATE TABLE GPS(
    connectionId INT,
    PRIMARY KEY (connectionId),
    FOREIGN KEY (connectionId) REFERENCES connection(connectionId)
);

CREATE TABLE processor (
    processorName CHAR (30),
    manufacturer CHAR (30),
    speed FLOAT,
    PRIMARY KEY (processorName, manufacturer)
);

CREATE TABLE runByProcessor(
    itemId INT,
    processorName CHAR(30),
    manufacturer CHAR(30),
    PRIMARY KEY (itemId, processorName,manufacturer),
    FOREIGN KEY (itemId) REFERENCES computer(itemId),
    FOREIGN KEY (processorName, manufacturer) REFERENCES processor( processorName, manufacturer)
);

CREATE TABLE laptop(
    itemId INT,
    PRIMARY KEY (itemId),
    FOREIGN KEY (itemId) REFERENCES computer(itemId)
);

CREATE TABLE desktop(
    itemId INT, 
    PRIMARY KEY ( itemId),
    FOREIGN KEY (itemId) REFERENCES computer(itemId)
);

-- Merge up, add a boolean isTablet? 
-- Denormalize: merge external memory to handheld.
CREATE TABLE handheld(
    itemId INT,
    isTablet TINYINT(1),


    externalMemoryMaxSize INT,
    externalMemoryType CHAR(30),

    hasSimLock TINYINT(1),
    netWorkProvider CHAR(30),

    simType ENUM('STANDARD','MICRO', 'NANO') ,

    PRIMARY KEY (itemId),
    FOREIGN KEY (itemId) REFERENCES computer(itemId)
);

CREATE VIEW tablet AS 
SELECT * 
FROM handheld
WHERE isTablet = 0;

CREATE VIEW smartphone AS 
SELECT * 
FROM handheld
WHERE isTablet <> 0;

-- Trigger Constraint: 
-- 1. the bidder should not be the seller of the item.
DELIMITER $$
CREATE TRIGGER bidderNotSeller
BEFORE INSERT ON bidsOn
FOR EACH ROW BEGIN
    IF EXISTS(SELECT * 
              FROM auction A
              WHERE NEW.itemId = A.itemId AND
                    NEW.bidder = A.seller) THEN
        SIGNAL SQLSTATE '10000' SET MESSAGE_TEXT = 'the bidder cannot be the seller';
    END IF;
END$$
DELIMITER ;

-- 2. One item can have at most 1 auction tied with it. ( key constraint that is not covered.
-- 3. If an item is onSale then some of the fields must not be NULL. Because if an item is ‘virtually’ on alert NULL are allowed for most of the fields.
DELIMITER $$
CREATE TRIGGER itemHasAtMostOneAuction
BEFORE INSERT ON auction
FOR EACH ROW BEGIN
    IF (SELECT COUNT(*)
        FROM auction JOIN item ON item.itemId = auction.itemId
        WHERE item.itemId = NEW.itemId ) = 1 THEN
        SIGNAL SQLSTATE '10001' SET MESSAGE_TEXT = 'one item can only have at most one auction associated with it';
    END IF;
    /*-------------------------------------------------------------- IGNORE THIS FOR NOW
    IF NOT EXISTS ( SELECT *
                FROM auction AS A, item AS I, computer AS C 
                WHERE  A.itemId = I.itemId AND
                       A.itemId = C.itemId AND
                       NEW.itemId = I.itemId AND 
                       I.manufacturer IS NOT NULL AND
                       I.cond IS NOT NULL AND 
                       I.description IS NOT NULL AND
                       C.ram IS NOT NULL AND
                       C.brandName IS NOT NULL AND
                       C.weight IS NOT NULL AND
                       C.operatingSystem IS NOT NULL AND
                       C.screenType IS NOT NULL AND
                       C.screenWidth IS NOT NULL AND
                       C.screenHeight IS NOT NULL AND
                       C.screenResolutionX IS NOT NULL AND
                       C.screenResolutionY IS NOT NULL AND
                       C.sizeWidth IS NOT NULL AND
                       C.sizeHeight IS NOT NULL AND
                       C.sizeDepth IS NOT NULL AND
                       C.color IS NOT NULL AND
                       C.batteryCapacity IS NOT NULL ) THEN
       SIGNAL SQLSTATE '10002' SET MESSAGE_TEXT = 'The item needs enough information to be on sale';
    END IF;
    ---------------------------------------------------------------*/
    
END$$
DELIMITER ;

INSERT INTO account(username, password)
VALUES ('admin', 'admin'),
     ('rep', 'rep'),
     ('jst', 'user1'),
     ('mc', 'user2'),
     ('tcn33','fe');

INSERT INTO admin (username)
VALUES ('admin');

INSERT INTO representative
VALUES ('rep', 'Thanh Ngo', 'rep@gmail.com');

INSERT INTO user(username, name, email, address, profilePicture)
VALUES 
('jst', 'Justin Chong', 'jst@gmail.com', 'NJ','jst'),
('mc', 'Michael Reid', 'mc@gmail.com', 'NJ','mc'),
('tcn33','Thanh Ngo', 'felix.infinite@gmail.com','NJ','tcn33');

INSERT INTO item
VALUES
( 1, 'Macbook Pro', 'Apple', 'NEW', 'The Macbook Pro (Early 2015)', '1'),
( 2, 'Macbook Air', 'Apple', 'USED', 'Old but functional', '2'),
( 3, 'The Macbook', 'Apple', 'MANU_REFUR', 'The brand new version (Late 2016 - not a scam )', '3'),
( 4, 'Vivobook E403', 'Asus', 'USED', 'Brand new on the market, BUY NOW!!', '4'),
( 5, 'Acer Chromebook 15 - NJ Only', 'Acer', 'NEW', 'Hot!','5');
	
INSERT INTO computer(itemId,ram,brandName, operatingSystem) VALUES
    (1, 32, 'Apple', 'Mac OS'),
    (2, 64, 'Apple', 'Windows'),
    (3, 4, 'Apple', 'Android'),
    (4, 32, 'Asus', 'Windows'),
    (5, 4,'Google','Chrome OS');

INSERT INTO auction(itemId, seller, openDate, closeDate, minimumPrice, minimumIncrement) VALUES
    ( 1, 'tcn33', '2016-4-20 00:00:00', '2016-4-27 00:00:00', 300, 20.12 ),
    ( 2, 'tcn33', '2016-4-21 00:00:00', '2016-4-29 00:00:00',200, 100 ),
    ( 3, 'tcn33', '2016-4-6 00:00:00', '2016-4-8 00:00:00', 400, 102.23),
    ( 4, 'tcn33', '2016-4-1 00:00:00', '2016-4-2 00:00:00', 20, 10),
    ( 5, 'tcn33', '2016-3-1 00:00:00', '2016-3-2 00:00:00', 10, 20.23);


INSERT INTO laptop VALUES (3);
INSERT INTO desktop VALUES
    (1),
    (2),
    (4),
    (5);

