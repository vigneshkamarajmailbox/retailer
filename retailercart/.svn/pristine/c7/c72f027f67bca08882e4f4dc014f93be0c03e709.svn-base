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
