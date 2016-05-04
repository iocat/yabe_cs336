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
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE representative(
    username CHAR(30) PRIMARY KEY,
    name CHAR(255) NOT NULL,
    email CHAR(255) NOT NULL,
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE user(
    username CHAR(30) PRIMARY KEY,
    name CHAR(255) NOT NULL,
    email CHAR(255) NOT NULL,
    address CHAR( 200 ) NOT NULL,
    profilePicture CHAR(36),
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE ON UPDATE CASCADE
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
    itemId INT AUTO_INCREMENT,
    name VARCHAR(100),
    manufacturer CHAR(30),
    cond ENUM('NEW', 'NEW_OTHER','MANU_REFUR' ,'SELL_REFUR','USED','FOR_PARTS') ,
    description TEXT CHARACTER SET utf8,
    picture CHAR(36),
    PRIMARY KEY (itemId)
);

ALTER TABLE item AUTO_INCREMENT=100;

CREATE TABLE automaticallyBidsOn (
    itemId INT,
    username CHAR(30),
    time DATETIME,
    maximumPrice FLOAT NOT NULL,
    PRIMARY KEY (itemId,username,time),
    FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (itemId) REFERENCES item(itemId) ON DELETE CASCADE ON UPDATE CASCADE
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
    FOREIGN KEY (purchaser) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (seller) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (itemId) REFERENCES item(itemId) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Merge bidsOn with time ( make time an attribute ) 
CREATE TABLE bidsOn (
    itemId INT,
    bidder CHAR(30),
    amount FLOAT,
    
    time DATETIME,
    PRIMARY KEY (itemId, bidder, amount),
    FOREIGN KEY (itemId) REFERENCES auction(itemId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (bidder) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE alert (
    username CHAR(30),
    itemId INT,
    PRIMARY KEY (username, itemId),
    FOREIGN KEY (username) REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (itemId) REFERENCES item (itemId) ON DELETE CASCADE ON UPDATE CASCADE
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
    FOREIGN KEY (itemId) REFERENCES item(itemId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (graphicCardName) REFERENCES graphicCard(graphicCardName) ON DELETE CASCADE ON UPDATE CASCADE
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
    FOREIGN KEY (itemId) REFERENCES computer(itemId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (driveId) REFERENCES hardDrive(driveId) ON DELETE CASCADE ON UPDATE CASCADE
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
    FOREIGN KEY (cameraId) REFERENCES computerCamera(cameraId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (itemId) REFERENCES computer(itemId) ON DELETE CASCADE ON UPDATE CASCADE
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
    FOREIGN KEY (itemId) REFERENCES computer(itemId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (connectionId) REFERENCES connection (connectionId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE bluetooth(
    connectionId INT,
    version CHAR(10),
    PRIMARY KEY (connectionId),
    FOREIGN KEY (connectionId) REFERENCES connection(connectionId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE wifi (
    connectionId INT,
    bandwidth INT,
    PRIMARY KEY (connectionId),
    FOREIGN KEY (connectionId) REFERENCES connection(connectionId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE GPS(
    connectionId INT,
    PRIMARY KEY (connectionId),
    FOREIGN KEY (connectionId) REFERENCES connection(connectionId) ON DELETE CASCADE ON UPDATE CASCADE
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
    FOREIGN KEY (itemId) REFERENCES computer(itemId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (processorName, manufacturer) REFERENCES processor( processorName, manufacturer) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE laptop(
    itemId INT,
    PRIMARY KEY (itemId),
    FOREIGN KEY (itemId) REFERENCES computer(itemId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE desktop(
    itemId INT, 
    PRIMARY KEY ( itemId),
    FOREIGN KEY (itemId) REFERENCES computer(itemId) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Merge up, add a boolean isTablet? 
-- Denormalize: merge external memory to handheld.
CREATE TABLE handheld(
    itemId INT,
    isTablet TINYINT(1),


    externalMemoryMaxSize INT,
    externalMemoryType CHAR(30),

    hasSimLock TINYINT(1),
    networkProvider CHAR(30),

    simType ENUM('STANDARD','MICRO', 'NANO') ,

    PRIMARY KEY (itemId),
    FOREIGN KEY (itemId) REFERENCES computer(itemId) ON DELETE CASCADE ON UPDATE CASCADE
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
    

-- TRIGGER FOR AUTOMATIC BIDDING SYSYEM
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER automaticBid
AFTER INSERT ON bidsOn
FOR EACH ROW BEGIN
    IF EXISTS(SELECT * 
              FROM automaticallyBidsOn A, auction X 
              WHERE NEW.itemId = A.itemId AND
                    NEW.bidder <> A.username AND
                    (NEW.amount + X.minimumIncrement) < A.maximumPrice) THEN
        INSERT INTO bidsOn (itemId, bidder, amount, time)
        VALUES (A.itemId, A.username, NEW.amount + X.minimumIncrement, A.time);
    END IF;
END$$
DELIMITER ;


INSERT INTO account(username, password)
VALUES ('admin', 'admin'),
     ('rep', 'rep'),
     ('jst', '7e58d63b60197ceb55a1c487989a3720'), -- Password: user2
     ('mc', '92877af7a45fd6a2ed7fe81e1236b78'), -- Paswword: user3
     ('tcn33','bb9836f5e2ce756d59b9a4556283bc0'); -- password: user1

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
(5, 'Acer Chromebook 15 - NJ Only', 'Acer', 'NEW', 'Hot!','5'),
(6, 'Galaxy s6', 'Samsung', 'SELL_REFUR', 'Works just like New!','6'),
(7, 'Dell Venue 10', 'Dell', 'NEW', 'Comes with 5 year warranty', '7'),
(8, 'IBM PS1 Desktop', 'IBM', 'FOR_PARTS', 'VINTAGE COMPUTER PARTS!', '8'),
(9, 'Sony Xperia z5', 'Sony', 'MANU_REFUR', 'Why pay more for a new phone?', '9'),
(10, 'Acer Aspire TC', 'Acer', 'NEW', 'BRAND NEW FACTORY MODEL', '10'),
(11, 'Lenovo Tab 2', 'Lenovo', 'NEW_OTHER', 'Never used but no box', '11');
    
INSERT INTO computer(itemId,ram,brandName, weight, operatingSystem, screenType, screenWidth, screenHeight, screenResolutionX, screenResolutionY, sizeWidth, sizeHeight, sizeDepth, color, batteryCapacity) VALUES
    (1, 32, 'Apple', 4.59, 'Apple', 'RETINA', 13.3, 13.3, 1280, 800, 12.00, 0.95, 8.75, 'Silver', 7),
    (2, 64, 'Apple', 1.99, 'Windows', 'RETINA', 12.0,12.0, 1280, 800, 11.50, 0.50, 8.55, 'Gold', 6),
    (3, 4, 'Apple', 3.05, 'Android', 'LCD', 13.0,13.0,1280,800,12.78, 0.75, 8.50, 'Gray', 7),
    (4, 32, 'Asus', 2.02, 'Windows', 'LED', 13.3,13.3, 1280,800, 11.50, 0.65, 8.45, 'Black', 8),
    (5, 4,'Google', 3.56,'Chrome OS', 'LED', 15.0,15.0, 1280, 800, 12.9,0.80,9.0,'White',11),
    (6, 4, 'Samsung', 4.59, 'Android', 'AMOLED', 2.83, 5.1, 1080, 1920, 2.85, 5.74, 0.35, 'Black', 6),
    (7, 8, 'Dell', 0.86, 'Android', 'LED', 8.0,8.0,1280,800, 5.1,8.0,0.35,'Gray', 8),
    (8, 64, 'IBM', 100.00, 'Windows', 'IDKLOL',2.0,2.0,100,100,100.0,100.0,100.0,'white',0),
    (9, 8, 'Sony', 4.50, 'Android', 'LCD', 2.5, 5.75, 1080, 1920, 2.83 , 5.75 ,0.29, 'Silver', 9),
    (10, 32, 'Acer', 16.00, 'Windows', 'NONE', 0.00,0.00,0,0,6.9,15.0,16.3,'Black', 0),
    (11, 8, 'Lenovo', 10.8, 'Android', 'CTP', 10.1,10.1, 1920,1200,6.73,9.72,0.35,'Black', 10);


INSERT INTO auction(itemId, seller, openDate, closeDate, minimumPrice, minimumIncrement) VALUES
    ( 1, 'tcn33', '2016-4-20 00:00:00', '2016-5-06 00:00:00', 300, 20.12 ),
    ( 2, 'mc', '2016-4-21 00:00:00', '2016-5-10 00:00:00',200, 100 ),
    ( 3, 'jst', '2016-4-6 00:00:00', '2016-4-8 00:00:00', 400, 102.23),
    ( 4, 'mc', '2016-4-1 00:00:00', '2016-4-2 00:00:00', 20, 10),
    ( 5, 'jst', '2016-3-1 00:00:00', '2016-3-2 00:00:00', 10, 20.23),
    ( 6, 'jst', '2016-4-21 00:00:00', '2016-5-15 00:00:00', 400, 200),
    ( 7, 'tcn33', '2016-4-24 00:00:00', '2016-5-16 00:00:00',2500, 1000),
    ( 8, 'jst', '2016-4-20 00:00:00', '2016-5-21 00:00:00', 500, 10),
    ( 9, 'mc', '2016-4-24 00:00:00', '2016-5-17 00:00:00', 1000, 45),
    ( 10, 'tcn33', '2016-4-6 00:00:00', '2016-5-16 00:00:00', 1400, 55),
    ( 11, 'jst', '2016-4-1 00:00:00', '2016-5-17 00:00:00', 50, 500);


INSERT INTO laptop VALUES (3);
INSERT INTO desktop VALUES
    (1),
    (2),
    (4),
    (5),
    (8),
    (10);


INSERT INTO handheld (itemId, isTablet) VALUES
    (6,0),
    (7,1),
    (9,0),
    (11,1);

INSERT INTO bidsOn
VALUES 
(1, 'mc',100,'2016-4-27 00:00:00'),
(1, 'jst',120,'2016-4-27 00:00:00'),
(1, 'mc',150, '2016-4-30 12:43:00'),
(6, 'tcn33',400,'2016-4-27 00:00:00'),
(6, 'jst',600,'2016-4-28 00:00:00'),
(7, 'jst',2500,'2016-4-28 00:00:00'),
(7, 'mc',1000, '2016-4-30 11:46:00'),
(8, 'tcn33',500,'2016-4-23 11:30:00'),
(8, 'mc',510, '2016-4-30 12:43:00'),
(9, 'jst',1000,'2016-5-1 12:23:00'),
(11, 'tcn33',50, '2016-4-2 10:29:00');

