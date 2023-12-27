DROP TABLE IF EXISTS distributorsaleshierarchy;
CREATE TABLE distributorsaleshierarchy (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    SalesForceCode VARCHAR(50) NOT NULL,
    LobCode VARCHAR(50) NOT NULL,
    SalesHierPath VARCHAR(100) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , SalesForceCode , LobCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS companyuser;
CREATE TABLE companyuser (
    CmpCode VARCHAR(10) NOT NULL,
    LoginCode VARCHAR(100) NOT NULL,
    UserName VARCHAR(250) NOT NULL,
    UserType CHAR(1) DEFAULT 'C',
    MappedCode VARCHAR(100) NOT NULL,
    EmailId VARCHAR(250) DEFAULT NULL,
    MobileNo VARCHAR(15) DEFAULT NULL,
    UserStatus CHAR(1) DEFAULT 'Y',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , LoginCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;
