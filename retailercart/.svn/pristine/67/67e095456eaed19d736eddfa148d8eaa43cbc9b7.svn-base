DROP TABLE IF EXISTS bannerimagetemplate;
CREATE TABLE bannerimagetemplate
(
    BannerId   INT(11)      NOT NULL AUTO_INCREMENT,
    CmpCode    VARCHAR(10)  NOT NULL,
    BannerDesc VARCHAR(100) NOT NULL,
    FileName   VARCHAR(500) NOT NULL,
    DistrCode  LONGTEXT DEFAULT NULL,
    UploadFlag CHAR(1)  DEFAULT 'N',
    ModDt      DATETIME     NOT NULL,
    PRIMARY KEY (BannerId)
) ENGINE = INNODB DEFAULT CHARSET = UTF8;

INSERT INTO bannerimagetemplate (CmpCode, BannerDesc, FileName, DistrCode, UploadFlag, ModDt)
VALUES ('AMUL', 'AMUL Default Banner', 'AMUL.jpeg', null, 'N', NOW()),
       ('DIL', 'Dabur Default Banner', 'DIL.jpeg', null, 'N', NOW()),
       ('IN14', 'Nestle Default Banner', 'IN14.jpeg', null, 'N', NOW()),
       ('Perfetti', 'Perfetti Default Banner', 'Perfetti.jpeg', null, 'N', NOW());

-- sample records
INSERT INTO bannerimagetemplate (CmpCode, BannerDesc, FileName, DistrCode, UploadFlag, ModDt)
VALUES ('AMUL', 'AMUL Banner 1', 'AMUL_BANNER_1.jpeg', '0003508781,00097970479', 'N', NOW());
INSERT INTO bannerimagetemplate (CmpCode, BannerDesc, FileName, DistrCode, UploadFlag, ModDt)
VALUES ('AMUL', 'AMUL Banner 2', 'AMUL_BANNER_2.jpeg', '0003508781', 'N', NOW());
INSERT INTO bannerimagetemplate (CmpCode, BannerDesc, FileName, DistrCode, UploadFlag, ModDt)
VALUES ('AMUL', 'AMUL Banner 3', 'AMUL_BANNER_3.jpeg', '00097970479', 'N', NOW());
INSERT INTO bannerimagetemplate (CmpCode, BannerDesc, FileName, DistrCode, UploadFlag, ModDt)
VALUES ('AMUL', 'AMUL Banner 4', 'AMUL_BANNER_4.jpeg', null, 'N', NOW());

