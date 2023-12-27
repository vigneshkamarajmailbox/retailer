INSERT INTO systemnotification (CmpCode, MessageType, ValidFrom, ValidTo, MessageQuery, Message, 
IsActive, Intervals, UploadFlag, ModDt) 
VALUES ('TGBL', 'RESACT', CURDATE(), CURDATE(), 
'SELECT ua.LoginCode                        AS loginCode,
       DATE_FORMAT(CURDATE(), ''%Y-%m-%d'') AS MessageDate,
       TIME_FORMAT(NOW(), ''%H:%i:%s'')     AS MessageTime,
       ua.UserName                          AS userName,
       ua.LoginCode                         AS mobileNo
FROM useractivation ua
         INNER JOIN customer c
                    ON ua.LoginCode = c.MobileNo
                        AND c.RetailerStatus = ''Y''
WHERE ua.ActivationDt IS NULL && ua.MsgRequestDt < DATE_SUB(CURDATE(), INTERVAL 2 DAY)', 
null, 'Y', 'DAYMOR', 'N', NOW());



INSERT INTO systemnotification (CmpCode, MessageType, ValidFrom, ValidTo, MessageQuery, Message, 
IsActive, Intervals, UploadFlag, ModDt) 
VALUES ('TGBL', 'EMPORD', CURDATE(), CURDATE(), 
'SELECT DISTINCT ua.LoginCode                        AS loginCode,
                DATE_FORMAT(CURDATE(), ''%Y-%m-%d'') AS MessageDate,
                TIME_FORMAT(NOW(), ''%H:%i:%s'')     AS MessageTime,
                ua.UserName                          AS userName,
                ua.LoginCode                         AS mobileNo
FROM useractivation ua
         INNER JOIN customer c
                    ON ua.LoginCode = c.MobileNo
                        AND c.RetailerStatus = ''Y''
         LEFT JOIN
     (SELECT m.MobileNo,
             MAX(m.MessageDate) AS MessageDate
      FROM message m
               INNER JOIN useractivation ua
                          ON ua.LoginCode = m.MobileNo
      WHERE ua.ActivationDt < DATE_SUB(CURDATE(), INTERVAL 2 DAY)
        AND m.refCode = ''EMPORD''
      GROUP BY m.MobileNo) tmp
     ON ua.LoginCode = tmp.MobileNo
         LEFT JOIN orderbookingheader oh
                   ON c.CmpCode = oh.CmpCode
                       AND c.DistrCode = oh.DistrCode
                       AND c.CustomerCode = oh.CustomerCode
WHERE ua.ActivationDt < DATE_SUB(CURDATE(), INTERVAL 2 DAY)
  AND tmp.MessageDate < DATE_SUB(CURDATE(), INTERVAL 2 DAY)
  AND oh.CustomerCode IS NULL', 
'Hurry and place orders for Tata consumer products from your Tata consumer retailer app. and avail Rs 3/ kg additional discount for all orders placed through the app', 'Y', 'DAYMOR', 'N', NOW());


INSERT INTO systemnotification (CmpCode, MessageType, ValidFrom, ValidTo, MessageQuery, Message, 
IsActive, Intervals, UploadFlag, ModDt) 
VALUES ('TGBL', 'REPORD', CURDATE(), CURDATE(), 
'SELECT DISTINCT ua.LoginCode                        AS loginCode,
                DATE_FORMAT(CURDATE(), ''%Y-%m-%d'') AS MessageDate,
                TIME_FORMAT(NOW(), ''%H:%i:%s'')     AS MessageTime,
                ua.UserName                          AS userName,
                ua.LoginCode                         AS mobileNo
FROM useractivation ua
         INNER JOIN customer c
                    ON ua.LoginCode = c.MobileNo
                        AND c.RetailerStatus = ''Y''
         INNER JOIN orderbookingheader oh
                    ON c.CmpCode = oh.CmpCode
                        AND c.DistrCode = oh.DistrCode
                        AND c.CustomerCode = oh.CustomerCode
WHERE oh.CustomerCode IS NOT NULL', 
'Hurry and place more orders through your Tata consumer Retailer app and avail Rs 3/ kg additional discount.\n\nOffer valid for limited period only', 'Y', 'WEKTWO', 'N', NOW());
