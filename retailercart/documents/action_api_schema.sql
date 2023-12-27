DROP TABLE IF EXISTS action_order_status;
CREATE TABLE action_order_status (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    OrderNo VARCHAR(100) NOT NULL,
    OrderStatus VARCHAR(10) NOT NULL,
    ActionTime DATETIME NOT NULL,
    FreeText LONGTEXT DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , OrderNo , OrderStatus)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


DROP TABLE IF EXISTS action_order_invoice_value;
CREATE TABLE action_order_invoice_value (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    OrderNo VARCHAR(100) NOT NULL,
    InvoiceNo VARCHAR(100) NOT NULL,
    OrderValue DECIMAL(22 , 6 ) DEFAULT '0.000000',
    InvoiceValue DECIMAL(22 , 6 ) DEFAULT '0.000000',
    ActionTime DATETIME NOT NULL,
    FreeText LONGTEXT DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , OrderNo , InvoiceNo)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


DROP TABLE IF EXISTS action_order_invoice_line_level;
CREATE TABLE action_order_invoice_line_level (
    CmpCode VARCHAR(10) NOT NULL,
    DistrCode VARCHAR(50) NOT NULL,
    OrderNo VARCHAR(100) NOT NULL,
    InvoiceNo VARCHAR(100) NOT NULL,
    ProdCode VARCHAR(50) NOT NULL,
    ProdBatchCode VARCHAR(100) NOT NULL,
    OrderQty INT(11) DEFAULT '0',
    InvoiceQty INT(11) DEFAULT '0',
    LineType CHAR(1) DEFAULT 'E',
    ActionTime DATETIME NOT NULL,
    FreeText LONGTEXT DEFAULT NULL,
    UploadFlag CHAR(1) DEFAULT 'N',
    ModDt DATETIME NOT NULL,
    PRIMARY KEY (CmpCode , DistrCode , OrderNo , InvoiceNo , ProdCode , ProdBatchCode)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;

