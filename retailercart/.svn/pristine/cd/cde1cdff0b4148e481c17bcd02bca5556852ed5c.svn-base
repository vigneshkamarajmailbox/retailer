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

-- DAYNIG
-- DAYMOR
-- WEKONE
-- WEKTWO
-- MONONE


INSERT INTO systemnotification (CmpCode, MessageType, ValidFrom, ValidTo, MessageQuery, Message, 
IsActive, Intervals, UploadFlag, ModDt) 
VALUES ('TGBL', 'RESACT', CURDATE(), CURDATE(), 
'SELECT ua.LoginCode                        AS loginCode,
       DATE_FORMAT(CURDATE(), ''%Y-%m-%d'') AS MessageDate,
       TIME_FORMAT(NOW(), ''%H:%i:%s'')     AS MessageTime,
       ua.UserName                          AS userName,
       ua.LoginCode                         AS mobileNo
FROM useractivation ua
WHERE ua.ActivationDt IS NULL && ua.MsgRequestDt < DATE_SUB(CURDATE(), INTERVAL 2 DAY)', 
null, 'Y', 'DAYNIG', 'N', NOW());



INSERT INTO systemnotification (CmpCode, MessageType, ValidFrom, ValidTo, MessageQuery, Message, 
IsActive, Intervals, UploadFlag, ModDt) 
VALUES ('TGBL', 'EMPORD', CURDATE(), CURDATE(), 
'SELECT ua.LoginCode                        AS loginCode,
       DATE_FORMAT(CURDATE(), ''%Y-%m-%d'') AS MessageDate,
       TIME_FORMAT(NOW(), ''%H:%i:%s'')     AS MessageTime,
       ua.UserName                          AS userName,
       ua.LoginCode                         AS mobileNo
FROM useractivation ua
         INNER JOIN
     customer c ON ua.LoginCode = c.MobileNo
         LEFT JOIN
     orderbookingheader oh ON c.CmpCode = oh.CmpCode
         AND c.DistrCode = oh.DistrCode
         AND c.CustomerCode = oh.CustomerCode
WHERE ua.ActivationDt <= DATE_SUB(CURDATE(), INTERVAL 2 DAY)
  AND oh.CustomerCode IS NULL', 
'Hi {0},\n\nRetailer App usage is empty', 'Y', 'DAYNIG', 'N', NOW());
