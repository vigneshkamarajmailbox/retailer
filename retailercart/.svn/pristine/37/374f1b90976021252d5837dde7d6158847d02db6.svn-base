DROP DATABASE IF EXISTS retailer_ssfa;
CREATE DATABASE retailer_ssfa;
USE retailer_ssfa;

DROP TABLE IF EXISTS applicationupdate;
CREATE TABLE applicationupdate (
    Sno INT(11) NOT NULL AUTO_INCREMENT,
    DbVersion VARCHAR(30) NOT NULL,
    AppVersion VARCHAR(30) NOT NULL,
    URL VARCHAR(50) NOT NULL,
    FileName VARCHAR(50) NOT NULL,
    Remarks VARCHAR(50) NOT NULL,
    ModDt DATETIME DEFAULT NULL,
    PRIMARY KEY (Sno)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS configuration;
CREATE TABLE configuration (
    TableName VARCHAR(50) NOT NULL,
    Code VARCHAR(150) NOT NULL,
    Description VARCHAR(150) NOT NULL,
    GroupName VARCHAR(50) DEFAULT NULL,
    UsedBy VARCHAR(10) DEFAULT NULL,
    Component VARCHAR(10) DEFAULT NULL,
    Input1 VARCHAR(50) DEFAULT NULL,
    Input2 VARCHAR(50) DEFAULT NULL,
    Input3 VARCHAR(50) DEFAULT NULL,
    Input4 VARCHAR(50) DEFAULT NULL,
    Input5 VARCHAR(50) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (TableName)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS gststatemaster;
CREATE TABLE gststatemaster (
    GSTStateCode VARCHAR(50) NOT NULL,
    GSTStateName VARCHAR(100) NOT NULL,
    UnionTerritoryFlag CHAR(1) NOT NULL DEFAULT 'N',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (GSTStateCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS keygenerator;
CREATE TABLE keygenerator (
    LoginCode VARCHAR(50) NOT NULL,
    ScreenName VARCHAR(50) NOT NULL,
    Prefix VARCHAR(3) NOT NULL,
    SuffixYY VARCHAR(2) NOT NULL,
    SuffixNN INT(11) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (LoginCode , ScreenName)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS screen;
CREATE TABLE screen (
    ModuleNo INT(11) NOT NULL,
    ScreenNo INT(11) NOT NULL,
    ModuleName VARCHAR(250) NOT NULL,
    ScreenName VARCHAR(250) NOT NULL,
    ScreenType CHAR(1) NOT NULL,
    Sequence INT(5) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (ModuleNo , ScreenNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS screenaccess;
CREATE TABLE screenaccess (
    GroupCode VARCHAR(50) NOT NULL,
    GroupName VARCHAR(50) NOT NULL,
    ModuleNo INT(11) NOT NULL,
    ScreenNo INT(11) NOT NULL,
    IsAccess CHAR(1) DEFAULT 'Y',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (GroupCode , ModuleNo , ScreenNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS appuser;
CREATE TABLE appuser (
    LoginCode VARCHAR(100) NOT NULL,
    Password VARCHAR(250) NOT NULL,
    UserName VARCHAR(250) NOT NULL,
    UserStatus CHAR(1) DEFAULT 'Y',
    NewPassword CHAR(1) DEFAULT 'Y',
    GroupCode VARCHAR(50) DEFAULT NULL,
    LoginStatus CHAR(1) DEFAULT 'N',
    DeviceId VARCHAR(100) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (LoginCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS company;
CREATE TABLE company (
    CmpCode VARCHAR(10) NOT NULL,
    CmpName VARCHAR(100) DEFAULT NULL,
    CmpAddr1 VARCHAR(30) NOT NULL,
    CmpAddr2 VARCHAR(30) DEFAULT NULL,
    CmpAddr3 VARCHAR(30) DEFAULT NULL,
    City VARCHAR(50) NOT NULL,
    State VARCHAR(50) NOT NULL,
    Country VARCHAR(50) NOT NULL,
    PostalCode INT(11) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    CustomerCode VARCHAR(50) NOT NULL,
    CustomerName VARCHAR(100) NOT NULL,
    MobileNo VARCHAR(10) NOT NULL,
    PinCode VARCHAR(10) DEFAULT NULL,
    PhoneNo VARCHAR(15) DEFAULT NULL,
    ContactPerson VARCHAR(50) DEFAULT NULL,
    EmailID VARCHAR(50) DEFAULT NULL,
    RetailerStatus CHAR(1) DEFAULT 'Y',
    FssaiNo VARCHAR(50) DEFAULT NULL,
    CreditBills INT(11) DEFAULT '0',
    CreditDays INT(11) DEFAULT '0',
    CreditLimit DECIMAL(22 , 6 ) DEFAULT '0.000000',
    CashDiscPerc DECIMAL(22 , 6 ) DEFAULT '0.000000',
    ChannelCode VARCHAR(50) NOT NULL,
    SubChannelCode VARCHAR(50) NOT NULL,
    GroupCode VARCHAR(50) NOT NULL,
    ClassCode VARCHAR(50) NOT NULL,
    StoreType CHAR(1) DEFAULT 'I',
    ParentCustomerCode VARCHAR(50) DEFAULT NULL,
    Latitude VARCHAR(50) DEFAULT NULL,
    Longitude VARCHAR(50) DEFAULT NULL,
    CustomerType CHAR(1) DEFAULT 'U',
    GSTTinNo VARCHAR(50) DEFAULT NULL,
    PanNo VARCHAR(50) DEFAULT NULL,
    District VARCHAR(100) DEFAULT NULL,
    OutstandingAmt DECIMAL(22 , 6 ) DEFAULT '0.000000',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , CustomerCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS customershipaddress;
CREATE TABLE customershipaddress (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    CustomerCode VARCHAR(50) NOT NULL,
    CustomerShipCode VARCHAR(50) NOT NULL,
    CustomerShipAddr1 VARCHAR(50) DEFAULT NULL,
    CustomerShipAddr2 VARCHAR(50) DEFAULT NULL,
    CustomerShipAddr3 VARCHAR(50) DEFAULT NULL,
    City VARCHAR(50) NOT NULL,
    GSTStateCode VARCHAR(50) NOT NULL,
    PhoneNo VARCHAR(15) DEFAULT NULL,
    DefaultShipAddr CHAR(1) DEFAULT 'N',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , CustomerCode , CustomerShipCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS distributor;
CREATE TABLE distributor (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    DistrName VARCHAR(100) NOT NULL,
    DistrAddr1 VARCHAR(100) NOT NULL,
    DistrAddr2 VARCHAR(100) DEFAULT NULL,
    DistrAddr3 VARCHAR(100) DEFAULT NULL,
    PinCode VARCHAR(10) DEFAULT NULL,
    PhoneNo VARCHAR(15) DEFAULT NULL,
    MobileNo VARCHAR(10) DEFAULT NULL,
    ContactPerson VARCHAR(50) DEFAULT NULL,
    EmailID VARCHAR(50) DEFAULT NULL,
    GSTStateCode VARCHAR(50) NOT NULL,
    DayOff VARCHAR(250) DEFAULT NULL,
    DistrStatus CHAR(1) DEFAULT 'Y',
    LoadStockProd CHAR(1) DEFAULT 'N',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS distributorlobmapping;
CREATE TABLE distributorlobmapping (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    LOBCode VARCHAR(50) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , LOBCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS lobmaster;
CREATE TABLE lobmaster (
    CmpCode VARCHAR(10) NOT NULL,
    LOBCode VARCHAR(50) NOT NULL,
    LOBName VARCHAR(100) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , LOBCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS producthierlevel;
CREATE TABLE producthierlevel (
    CmpCode VARCHAR(10) NOT NULL,
    ProdHierLvlCode INT(11) NOT NULL,
    ProdHierLvlName VARCHAR(50) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , ProdHierLvlCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS producthiervalue;
CREATE TABLE producthiervalue (
    CmpCode VARCHAR(10) NOT NULL,
    ProdHierLvlCode INT(11) NOT NULL,
    ProdHierValCode VARCHAR(50) NOT NULL,
    ProdHierValName VARCHAR(100) NOT NULL,
    ParentCode VARCHAR(50) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , ProdHierLvlCode , ProdHierValCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS product;
CREATE TABLE product (
    CmpCode VARCHAR(10) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    ProdName VARCHAR(100) NOT NULL,
    ProdShortName VARCHAR(100) NOT NULL,
    ProdStatus CHAR(1) DEFAULT 'Y',
    ProdType CHAR(1) DEFAULT NULL,
    ProdShelfLife INT(11) DEFAULT NULL,
    ProdNetWgt DECIMAL(22 , 6 ) NOT NULL,
    ProdWgtType VARCHAR(10) NOT NULL,
    LOBCode VARCHAR(50) NOT NULL,
    LOBName VARCHAR(100) NOT NULL,
    HSNCode VARCHAR(50) NOT NULL,
    HSNName VARCHAR(500) NOT NULL,
    ProductHierPathCode VARCHAR(500) NOT NULL,
    ProductHierPathName VARCHAR(500) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , ProdCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS productbatch;
CREATE TABLE productbatch (
    CmpCode VARCHAR(10) NOT NULL,
    BatchLevel VARCHAR(50) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    ProdBatchCode VARCHAR(100) NOT NULL,
    MRP DECIMAL(22 , 6 ) NOT NULL,
    SellRate DECIMAL(22 , 6 ) NOT NULL,
    SellRateWithTax DECIMAL(22 , 6 ) NOT NULL,
    ManfDate DATE NOT NULL,
    ExpiryDate DATE NOT NULL,
    LatestBatch CHAR(1) DEFAULT 'N',
    GeoLevelBatch CHAR(1) DEFAULT 'Y',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , BatchLevel , ProdCode , ProdBatchCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS productuom;
CREATE TABLE productuom (
    CmpCode VARCHAR(10) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    UOMCode VARCHAR(10) NOT NULL,
    UOMDescription VARCHAR(50) NOT NULL,
    UomConvFactor INT(11) NOT NULL DEFAULT '0',
    BaseUOM VARCHAR(10) NOT NULL,
    DefaultUOM VARCHAR(10) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , ProdCode , UOMCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS retailercategory;
CREATE TABLE retailercategory (
    CmpCode VARCHAR(10) NOT NULL,
    ChannelCode VARCHAR(50) NOT NULL,
    ChannelName VARCHAR(100) NOT NULL,
    SubChannelCode VARCHAR(50) NOT NULL,
    SubChannelName VARCHAR(100) NOT NULL,
    GroupCode VARCHAR(50) NOT NULL,
    GroupName VARCHAR(100) NOT NULL,
    ClassCode VARCHAR(50) NOT NULL,
    ClassName VARCHAR(100) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , ChannelCode , SubChannelCode , GroupCode , ClassCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemedefinition;
CREATE TABLE schemedefinition (
    CmpCode VARCHAR(10) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    SchemeDescription VARCHAR(250) NOT NULL,
    SchemeBase VARCHAR(6) NOT NULL,
    SchemeFromDt DATE NOT NULL,
    SchemeToDt DATE NOT NULL,
    PayOutType VARCHAR(10) NOT NULL,
    IsSkuLevel CHAR(1) DEFAULT 'N',
    IsActive CHAR(1) DEFAULT 'N',
    Combi CHAR(1) DEFAULT 'N',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , SchemeCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemedistributorbudget;
CREATE TABLE schemedistributorbudget (
    CmpCode VARCHAR(10) NOT NULL,
    SchemeCode VARCHAR(60) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    IsUnlimited CHAR(1) NOT NULL,
    Budget DECIMAL(22 , 6 ) DEFAULT NULL,
    IsActive CHAR(1) NOT NULL DEFAULT 'Y',
    UtilizedAmt DECIMAL(22 , 6 ) DEFAULT NULL,
    BudgetOs DECIMAL(22 , 6 ) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , SchemeCode , DistrCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemeslab;
CREATE TABLE schemeslab (
    CmpCode VARCHAR(10) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    SlabNo INT(11) NOT NULL,
    SlabFrom DECIMAL(18 , 6 ) NOT NULL,
    SlabTo DECIMAL(18 , 6 ) NOT NULL,
    Payout DECIMAL(18 , 6 ) NOT NULL,
    SlabRange CHAR(1) DEFAULT 'N',
    Forevery DECIMAL(18 , 6 ) NOT NULL,
    UomCode VARCHAR(10) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , SchemeCode , SlabNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemeslabproduct;
CREATE TABLE schemeslabproduct (
    CmpCode VARCHAR(10) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    SlabNo INT(11) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    ProdName VARCHAR(100) DEFAULT NULL,
    Qty INT(11) NOT NULL,
    IsMandatory CHAR(1) DEFAULT 'N',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , SchemeCode , SlabNo , ProdCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemecustomer;
CREATE TABLE schemecustomer (
    CmpCode VARCHAR(10) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    CustomerCode VARCHAR(50) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , SchemeCode , CustomerCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemeretailercategory;
CREATE TABLE schemeretailercategory (
    CmpCode VARCHAR(10) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    ChannelCode VARCHAR(50) NOT NULL,
    SubChannelCode VARCHAR(50) NOT NULL,
    GroupCode VARCHAR(50) NOT NULL,
    ClassCode VARCHAR(50) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , SchemeCode , ChannelCode , SubChannelCode , GroupCode , ClassCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemeproduct;
CREATE TABLE schemeproduct (
    CmpCode VARCHAR(10) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    ProdName VARCHAR(100) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , SchemeCode , ProdCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemeproductcategory;
CREATE TABLE schemeproductcategory (
    CmpCode VARCHAR(10) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    ProdHierLvlCode VARCHAR(50) NOT NULL,
    ProdHierValCode VARCHAR(50) NOT NULL,
    ProdHierValName VARCHAR(100) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , SchemeCode , ProdHierLvlCode , ProdHierValCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemeattributes;
CREATE TABLE schemeattributes (
    CmpCode VARCHAR(10) NOT NULL,
    AttributeCode VARCHAR(30) NOT NULL,
    AttributeValueCode INT(11) DEFAULT NULL,
    AttrInputValues VARCHAR(50) DEFAULT NULL,
    RefNo VARCHAR(100) NOT NULL,
    SNo INT(11) NOT NULL DEFAULT '1',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , AttributeCode , RefNo , SNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS schemecombiproduct;
CREATE TABLE schemecombiproduct (
    CmpCode VARCHAR(10) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    SlabNo INT(11) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    ProdName VARCHAR(100) DEFAULT NULL,
    MinValue DECIMAL(18 , 6 ) NOT NULL,
    IsMandatory CHAR(1) DEFAULT 'N',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , SchemeCode , SlabNo , ProdCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS taxstructure;
CREATE TABLE taxstructure (
    CmpCode VARCHAR(10) NOT NULL,
    TaxStateCode VARCHAR(50) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    CGST DECIMAL(18 , 6 ) DEFAULT NULL,
    SGST DECIMAL(18 , 6 ) DEFAULT NULL,
    IGST DECIMAL(18 , 6 ) DEFAULT NULL,
    CESS DECIMAL(18 , 6 ) DEFAULT NULL,
    AdditionalTax DECIMAL(18 , 6 ) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , TaxStateCode , ProdCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS deviceinformation;
CREATE TABLE deviceinformation (
    LoginCode VARCHAR(50) NOT NULL,
    AdvId VARCHAR(50) NOT NULL,
    Devicebrand VARCHAR(50) NOT NULL,
    Devicemodel VARCHAR(50) NOT NULL,
    Deviceversion VARCHAR(50) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (LoginCode , AdvId)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS firebaseuser;
CREATE TABLE firebaseuser (
    FireBaseKey VARCHAR(500) NOT NULL,
    LoginCode VARCHAR(100) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (FireBaseKey , LoginCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS message;
CREATE TABLE message (
    MessageNo INT(11) NOT NULL AUTO_INCREMENT,
    LoginCode VARCHAR(30) NOT NULL,
    MessageDate VARCHAR(50) NOT NULL,
    MessageTime VARCHAR(50) NOT NULL,
    UserName VARCHAR(50) DEFAULT NULL,
    MobileNo VARCHAR(10) DEFAULT NULL,
    OtpCode VARCHAR(10) DEFAULT NULL,
    RefCode VARCHAR(10) DEFAULT NULL,
    Message LONGTEXT,
    DeliveryStatus LONGTEXT DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (MessageNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS synclog;
CREATE TABLE synclog (
    SyncNo INT(11) NOT NULL AUTO_INCREMENT,
    LoginCode VARCHAR(100) NOT NULL,
    AppVersion VARCHAR(30) NOT NULL,
    SyncDt DATE NOT NULL,
    SyncStartTime VARCHAR(50) NOT NULL,
    SyncEndTime VARCHAR(50) NOT NULL,
    Mode VARCHAR(50) NOT NULL,
    ProcessName VARCHAR(50) NOT NULL,
    UserName VARCHAR(250) NOT NULL,
    Message VARCHAR(500) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (SyncNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS useraccessdetail;
CREATE TABLE useraccessdetail (
    LoginCode VARCHAR(100) NOT NULL,
    RemoteAddr VARCHAR(100) NOT NULL,
    Token VARCHAR(100) NOT NULL,
    TokenGenerationTime DATETIME NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (LoginCode , RemoteAddr)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS orderbookingheader;
CREATE TABLE orderbookingheader (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    OrderNo VARCHAR(100) NOT NULL,
    customerRefNo VARCHAR(100) NOT NULL,
    CustomerCode VARCHAR(50) NOT NULL,
    CustomerShipCode VARCHAR(100) DEFAULT NULL,
    OrderDt DATE NOT NULL,
    Remarks VARCHAR(150) DEFAULT NULL,
    Latitude VARCHAR(20) DEFAULT NULL,
    Longitude VARCHAR(20) DEFAULT NULL,
    StartTime DATETIME DEFAULT NULL,
    EndTime DATETIME DEFAULT NULL,
    OrderStatus VARCHAR(10) DEFAULT 'P',
    TotalGrossValue DECIMAL(22 , 6 ) DEFAULT '0.000000',
    TotalDiscount DECIMAL(22 , 6 ) DEFAULT '0.000000',
    TotalTax DECIMAL(22 , 6 ) DEFAULT '0.000000',
    TotalOrderValue DECIMAL(22 , 6 ) DEFAULT '0.000000',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , OrderNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS orderbookingdetails;
CREATE TABLE orderbookingdetails (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    OrderNo VARCHAR(100) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    ProdName VARCHAR(100) DEFAULT NULL,
    ProdBatchCode VARCHAR(100) NOT NULL,
    OrderQty INT(11) DEFAULT '0',
    ServicedQty INT(11) DEFAULT '0',
    UomCode VARCHAR(13) NOT NULL,
    inputStr VARCHAR(100) DEFAULT NULL,
    SellRate DECIMAL(22 , 6 ) DEFAULT '0.000000',
    ActualSellRate DECIMAL(22 , 6 ) DEFAULT '0.000000',
    GrossValue DECIMAL(22 , 6 ) DEFAULT '0.000000',
    SchAmt DECIMAL(22 , 6 ) DEFAULT '0.000000',
    TaxAmt DECIMAL(22 , 6 ) DEFAULT '0.000000',
    TaxCode VARCHAR(100) DEFAULT NULL,
    CgstPerc DECIMAL(22 , 6 ) DEFAULT '0.000000',
    CgstAmt DECIMAL(22 , 6 ) DEFAULT '0.000000',
    SgstPerc DECIMAL(22 , 6 ) DEFAULT '0.000000',
    SgstAmt DECIMAL(22 , 6 ) DEFAULT '0.000000',
    UgstPerc DECIMAL(22 , 6 ) DEFAULT '0.000000',
    UgstAmt DECIMAL(22 , 6 ) DEFAULT '0.000000',
    IgstPerc DECIMAL(22 , 6 ) DEFAULT '0.000000',
    IgstAmt DECIMAL(22 , 6 ) DEFAULT '0.000000',
    OrderValue DECIMAL(22 , 6 ) DEFAULT '0.000000',
    ProdType VARCHAR(25) DEFAULT 'N',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , OrderNo , ProdCode , ProdBatchCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS orderbookingservicedetails;
CREATE TABLE orderbookingservicedetails (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    OrderNo VARCHAR(100) NOT NULL,
    InvoiceNo VARCHAR(100) DEFAULT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    ProdName VARCHAR(100) DEFAULT NULL,
    ProdBatchCode VARCHAR(100) NOT NULL,
    SellRate DECIMAL(22 , 6 ) DEFAULT '0.000000',
    SoqQty INT(11) DEFAULT '0',
    SoqValue DECIMAL(22 , 6 ) DEFAULT '0.000000',
    OrderQty INT(11) DEFAULT '0',
    OrderValue DECIMAL(22 , 6 ) DEFAULT '0.000000',
    ServicedQty INT(11) DEFAULT '0',
    ServicedValue DECIMAL(22 , 6 ) DEFAULT '0.000000',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , OrderNo , ProdCode , ProdBatchCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS orderbookingschemedetails;
CREATE TABLE orderbookingschemedetails (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    OrderNo VARCHAR(100) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    SlabNo VARCHAR(10) NOT NULL,
    FreeProdCode VARCHAR(50) NOT NULL DEFAULT '',
    FreeQty INT(11) DEFAULT '0',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , OrderNo , SchemeCode , SlabNo , FreeProdCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS orderbookingschemeproductrule;
CREATE TABLE orderbookingschemeproductrule (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    OrderNo VARCHAR(100) NOT NULL,
    SchemeCode VARCHAR(50) NOT NULL,
    SlabNo VARCHAR(10) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    DiscPerc DECIMAL(22 , 6 ) DEFAULT '0.000000',
    DiscAmt DECIMAL(22 , 6 ) DEFAULT '0.000000',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , OrderNo , SchemeCode , SlabNo , ProdCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS feedbackmaster;
CREATE TABLE feedbackmaster (
    FeedBackType VARCHAR(10) NOT NULL,
    FeedBackName VARCHAR(100) NOT NULL,
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (FeedBackType)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS feedback;
CREATE TABLE feedback (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    FeedbackNo VARCHAR(100) NOT NULL,
    CustomerCode VARCHAR(50) NOT NULL,
    FeedBackDt DATE NOT NULL,
    FeedbackType CHAR(2) NOT NULL,
    Message VARCHAR(250) DEFAULT NULL,
    ImagePath VARCHAR(250) DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , FeedbackNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS notificationtypemaster;
CREATE TABLE notificationtypemaster (
  NotificationType varchar(10) NOT NULL,
  NotificationName varchar(100) NOT NULL,
  ModDt datetime NOT NULL,
  PRIMARY KEY (NotificationType)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS notification;
CREATE TABLE notification (
    NotificationId INT(11) NOT NULL AUTO_INCREMENT,
    LoginCode VARCHAR(100) NOT NULL,
    NotificationType VARCHAR(10) NOT NULL,
    CreatedDt DATE NOT NULL,
    CreatedTime VARCHAR(50) NOT NULL,
    Subject VARCHAR(250) NOT NULL,
    Message LONGTEXT NOT NULL,
    MessageDetail LONGTEXT NOT NULL,
    FileName VARCHAR(250) DEFAULT NULL,
    Important VARCHAR(10) DEFAULT NULL,
    CmpCode VARCHAR(10) DEFAULT NULL,
    UploadStatus CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (NotificationId)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS locationupdate;
CREATE TABLE locationupdate (
    LoginCode VARCHAR(50) NOT NULL,
    UpdateDt DATE NOT NULL,
    Latitude VARCHAR(20) DEFAULT NULL,
    Longitude VARCHAR(20) DEFAULT NULL,
    ImagePath VARCHAR(500) DEFAULT NULL,
    PostalCode INT(11) DEFAULT NULL,
    UploadTo VARCHAR(500) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (LoginCode , UpdateDt)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS locationupdatestatus;
CREATE TABLE locationupdatestatus (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    LoginCode VARCHAR(50) NOT NULL,
    UpdateDt DATE NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode, DistrCode, LoginCode , UpdateDt)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS actionapi;
CREATE TABLE actionapi (
    SyncNo INT(11) NOT NULL AUTO_INCREMENT,
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) DEFAULT NULL,
    ActionTime DATETIME NOT NULL,
    ActionCode VARCHAR(100) NOT NULL,
    ActionTemplate LONGTEXT NOT NULL,
    ProcessedFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (SyncNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS languagemaster;
CREATE TABLE languagemaster (
    LanguageCode VARCHAR(10) NOT NULL,
    LanguageDescription VARCHAR(10) NOT NULL,
    PRIMARY KEY (LanguageCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS languagesupport;
CREATE TABLE languagesupport (
    LanguageCode VARCHAR(10) NOT NULL,
    CmpCode VARCHAR(10) NOT NULL,
    TableName VARCHAR(100) NOT NULL,
    ColumnName VARCHAR(100) NOT NULL,
    ValueCode VARCHAR(500) NOT NULL,
    ValueName VARCHAR(500) NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (LanguageCode, CmpCode, TableName, ColumnName, ValueCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS user_logs_t;
CREATE TABLE IF NOT EXISTS user_logs_t (
    user_code VARCHAR(255) NOT NULL,
    last_login_time DATETIME NOT NULL,
    last_login_ip VARCHAR(50) NOT NULL,
    is_logged_in BOOLEAN NOT NULL,
    logout_time DATETIME,
    PRIMARY KEY (user_code)
);

DROP TABLE IF EXISTS oauth_access_token;
CREATE TABLE oauth_access_token (
    authentication_id VARCHAR(255) NOT NULL,
    token_id VARCHAR(255) NOT NULL,
    token BLOB NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    client_id VARCHAR(255) NOT NULL,
    authentication BLOB NOT NULL,
    refresh_token VARCHAR(255) NOT NULL,
    PRIMARY KEY (authentication_id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS oauth_refresh_token;
CREATE TABLE oauth_refresh_token (
    token_id VARCHAR(255) NOT NULL,
    token BLOB NOT NULL,
    authentication BLOB NOT NULL
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS consumers;
CREATE TABLE consumers (
    LoginCode VARCHAR(100) NOT NULL,
    Password VARCHAR(250) NOT NULL,
    UserName VARCHAR(250) NOT NULL,
    UserStatus CHAR(1) DEFAULT 'Y',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (LoginCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS otherattributes;
CREATE TABLE otherattributes (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    AttributeCode VARCHAR(30) NOT NULL,
    AttributeValueCode INT(11) DEFAULT NULL,
    AttrInputValues VARCHAR(50) DEFAULT NULL,
    RefNo VARCHAR(65) NOT NULL,
    SNo INT(11) NOT NULL DEFAULT '1',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , AttributeCode , RefNo , SNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS distributorstock;
CREATE TABLE distributorstock (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    SaleableStock LONGTEXT NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode, DistrCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS customerstock;
CREATE TABLE customerstock (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    CustomerCode VARCHAR(50) NOT NULL,
    Stock LONGTEXT NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode, DistrCode, CustomerCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS categorysequence;
CREATE TABLE categorysequence (
    Category VARCHAR(30) NOT NULL,
    SequenceNo INT(11) NOT NULL,
    ModDt DATETIME DEFAULT NULL,
    PRIMARY KEY (Category)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS bannerimagetemplate;
CREATE TABLE bannerimagetemplate
(
    BannerId   INT(11)      NOT NULL AUTO_INCREMENT,
    CmpCode    VARCHAR(10)  NOT NULL,
    BannerDesc VARCHAR(100) NOT NULL,
    BannerType CHAR(1)  DEFAULT 'I',
    FileName   LONGTEXT     NOT NULL,
    DistrCode  LONGTEXT DEFAULT NULL,
    UploadFlag CHAR(1)  DEFAULT 'N',
    ModDt      DATETIME     NOT NULL,
    PRIMARY KEY (BannerId)
) ENGINE = INNODB DEFAULT CHARSET = UTF8;

DROP TABLE IF EXISTS favoriteproduct;
CREATE TABLE favoriteproduct (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    CustomerCode VARCHAR(50) NOT NULL,
    FavProdCode LONGTEXT NOT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode, DistrCode, CustomerCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS useractivation;
CREATE TABLE useractivation (
    LoginCode VARCHAR(100) NOT NULL,
    UserName VARCHAR(250) NOT NULL,
    UserStatus CHAR(1) DEFAULT 'C',
    CreationDt DATETIME NOT NULL,
    MsgRequestDt DATETIME DEFAULT NULL,
    ActivationDt DATETIME DEFAULT NULL,
    DeactivationDt DATETIME DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (LoginCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

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

DROP TABLE IF EXISTS systemnotification;
CREATE TABLE systemnotification (
    CmpCode VARCHAR(10) NOT NULL,
    MessageType VARCHAR(30) NOT NULL,
    ValidFrom DATE NOT NULL,
    ValidTo DATE NOT NULL,
    MessageQuery LONGTEXT NOT NULL,
    Message LONGTEXT DEFAULT NULL,
    IsActive CHAR(1) DEFAULT 'N',
    Intervals VARCHAR(10) DEFAULT 'DAYNIG',
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode, MessageType)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

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
    CoverageDay	VARCHAR(100) DEFAULT NULL,
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

