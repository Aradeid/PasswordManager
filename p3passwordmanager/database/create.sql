CREATE TABLE IF NOT EXISTS pm_user (
    userid int NOT NULL AUTO_INCREMENT,
	login VARCHAR(20) NOT NULL,
   	password VARCHAR(100) NOT NULL,
   	PRIMARY KEY(userid)
);

CREATE TABLE IF NOT EXISTS pm_password (
    passid INT NOT NULL AUTO_INCREMENT,
    passvalue VARCHAR(20) NOT NULL,
    canContainUppercase BOOLEAN,
    canCotainDigits BOOLEAN,
    canCotainSpecials BOOLEAN,
    minCount INT,
    maxLength INT,
   	PRIMARY KEY(passid)
);

CREATE TABLE IF NOT EXISTS pm_entry (
    entryid INT NOT NULL AUTO_INCREMENT,
    website VARCHAR(50) NOT NULL,
    login VARCHAR(30),
    timeadded DATETIME,
    timeupdated DATETIME,
    passid INT,
    userid INT,
   	PRIMARY KEY(entryid),
    FOREIGN KEY(passid) REFERENCES pm_password(passid),
    FOREIGN KEY(userid) REFERENCES pm_user(userid)
);