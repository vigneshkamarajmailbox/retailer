DROP TABLE IF EXISTS route;
CREATE TABLE route (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    RouteCode VARCHAR(50) NOT NULL,
    RouteName VARCHAR(100) NOT NULL,
    RouteType CHAR(1) DEFAULT 'S',
    RouteStatus CHAR(1) DEFAULT 'Y',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , RouteCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


DROP TABLE IF EXISTS salesman;
CREATE TABLE salesman (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    SalesmanCode VARCHAR(50) NOT NULL,
    SalesmanName VARCHAR(100) NOT NULL,
    MobileNo VARCHAR(10) DEFAULT NULL,
    EmailID VARCHAR(50) DEFAULT NULL,
    Status CHAR(1) DEFAULT 'Y',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , SalesmanCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


DROP TABLE IF EXISTS salesmanroutemapping;
CREATE TABLE salesmanroutemapping (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    SalesmanCode VARCHAR(50) NOT NULL,
    RouteCode VARCHAR(50) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , SalesmanCode , RouteCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


DROP TABLE IF EXISTS customerroutemapping;
CREATE TABLE customerroutemapping (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    CustomerCode VARCHAR(50) NOT NULL,
    RouteCode VARCHAR(50) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , CustomerCode , RouteCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

ALTER TABLE customer
ADD COLUMN District VARCHAR(100) DEFAULT NULL AFTER PanNo,
ADD COLUMN OutstandingAmt DECIMAL(22 , 6 ) DEFAULT '0.000000' AFTER District;
