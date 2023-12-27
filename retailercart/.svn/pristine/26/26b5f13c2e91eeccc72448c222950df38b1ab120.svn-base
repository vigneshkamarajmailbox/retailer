-- Application Update
INSERT INTO applicationupdate (DbVersion, AppVersion, URL, FileName, Remarks, ModDt)
VALUES ('1.0.0', '1.0.0', 'http://localhost:9443/', 'retailerSSFA.apk', 'Application Release Version 1.0.0', NOW());

-- Configuration
INSERT INTO configuration (TableName,Code,Description,GroupName,UsedBy,Component,Input1,Input2,Input3,Input4,Input5, UploadFlag, ModDt) 
VALUES ('APKAutoUpdate','N','Enable APK AutoUpdate in Play Store','System', 'System', NULL,NULL,NULL,NULL,NULL,NULL, 'N', NOW()),
('ImagePath','<folder path>','Provide the image path','System','System',NULL,NULL,NULL,NULL,NULL,NULL,'N',NOW()),
('SchemeProduct','#1396ed','Display Color for Scheme products in Mobile','System','System',NULL,NULL,NULL,NULL,NULL,NULL,'N',NOW()),
('FocusProduct','#000000','Display Color for Focus proudcts in Mobile','System','System',NULL,NULL,NULL,NULL,NULL,NULL,'N',NOW()),
('OutofStockProduct','#000000','Display Color for Out of Stock products in Mobile','System','System',NULL,NULL,NULL,NULL,NULL,NULL,'N',NOW()),
('NewProduct','#000000','Display Color for New products in Mobile','System','System',NULL,NULL,NULL,NULL,NULL,NULL,'N',NOW()),
('MustSellProduct','#000000','Display Color for Must Sell products in Mobile','System','System',NULL,NULL,NULL,NULL,NULL,NULL,'N',NOW()),
('topsku','#000000','Display Color for Top SKU products in Mobile','System','System',NULL,NULL,NULL,NULL,NULL,NULL,'N',NOW()),
('TaxableValueWithoutScheme','Y','Value as Y, then taxable value is without scheme','System', 'System', NULL,NULL,NULL,NULL,NULL,NULL, 'N', NOW()),
('CustomerWiseProduct','Y','Value as Y, then filter the product for the customer','System', 'System', NULL,NULL,NULL,NULL,NULL,NULL, 'N', NOW()), -- Nestle = 'Y' Others = 'N'
('ShowSchemeTag','N','Value as Y, then show the scheme tag','System', 'System', NULL,NULL,NULL,NULL,NULL,NULL, 'N', NOW()),-- Nestle = 'N' TCPL = 'N' Others = 'Y'
('EnableSOQ','N','Value as Y, then show SOQ for the customer','System', 'System', NULL,NULL,NULL,NULL,NULL,NULL, 'N', NOW()),-- Nestle = 'N' Others = 'Y'
('LoggerExportFreq','2','Logger exporter frequency in minutes','System', 'System', NULL,NULL,NULL,NULL,NULL,NULL, 'N', NOW());

-- gststatemaster
INSERT INTO gststatemaster (GSTStateCode,GSTStateName,UnionTerritoryFlag,UploadFlag,ModDt) VALUES ('01','Jammu & Kashmir','N','N',NOW()),
('02','Himachal Pradesh','N','N',NOW()),
('03','Punjab','N','N',NOW()),
('04','Chandigarh','Y','N',NOW()),
('05','Uttranchal','N','N',NOW()),
('06','Haryana','N','N',NOW()),
('07','Delhi','Y','N',NOW()),
('08','Rajasthan','N','N',NOW()),
('09','Uttar Pradesh','N','N',NOW()),
('10','Bihar','N','N',NOW()),
('11','Sikkim','N','N',NOW()),
('12','Arunachal Pradesh','N','N',NOW()),
('13','Nagaland','N','N',NOW()),
('14','Manipur','N','N',NOW()),
('15','Mizoram','N','N',NOW()),
('16','Tripura','N','N',NOW()),
('17','Meghalaya','N','N',NOW()),
('18','Assam','N','N',NOW()),
('19','West Bengal','N','N',NOW()),
('20','Jharkhand','N','N',NOW()),
('21','Orissa','N','N',NOW()),
('22','Chhattisgarh','N','N',NOW()),
('23','Madhya Pradesh','N','N',NOW()),
('24','Gujarat','N','N',NOW()),
('25','Daman & Diu','Y','N',NOW()),
('26','Dadra & Nagar Haveli','Y','N',NOW()),
('27','Maharashtra','N','N',NOW()),
('29','Karnataka','N','N',NOW()),
('30','Goa','N','N',NOW()),
('31','Lakshdweep','Y','N',NOW()),
('32','Kerala','N','N',NOW()),
('33','Tamil Nadu','N','N',NOW()),
('34','Pondicherry','Y','N',NOW()),
('35','Andaman & Nicobar Islands','Y','N',NOW()),
('36','Telangana','N','N',NOW()),
('37','Andhra Pradesh','N','N',NOW());

-- keygenerator
INSERT INTO keygenerator(LoginCode,ScreenName,Prefix,SuffixYY,SuffixNN,UploadFlag,ModDt)
VALUES('DEFAULT','Order Booking','ORD',DATE_FORMAT(CURDATE(), '%y'),1,'N',NOW());

-- screen 
INSERT INTO screen(ModuleNo,ScreenNo,ModuleName,ScreenName,ScreenType,Sequence,UploadFlag,ModDt)
VALUES (1,1,'Landing','New Order','T',1,'N',NOW()),
(1,2,'Landing','Order Details','R',2,'N',NOW()),
(1,3,'Landing','Day Summary','R',3,'N',NOW()),
(1,4,'Landing','Product','M',4,'N',NOW()),
(1,5,'Landing','Scheme','M',5,'N',NOW()),
(1,6,'Landing','Feedback','T',6,'N',NOW()),
(1,7,'Landing','Distributor Info','M',7,'N',NOW()),
(1,8,'Landing','Update Location','T',8,'N',NOW()),
(1,9,'Landing','Reports','R',9,'N',NOW()),
(1,10,'Landing','Change Password','T',10,'N',NOW()),
(1,11,'Landing','My Profile','M',11,'N',NOW());

-- screenaccess
INSERT INTO screenaccess
SELECT 'DEFAULT','DEFAULT',ModuleNo,ScreenNo,'Y','N',NOW()
FROM screen;

-- Customer Feedback & Store Visibility
INSERT INTO feedbackmaster (FeedBackType, FeedBackName,ModDt) VALUES 
('FB','FEEDBACK',NOW()), 
('SV','STORE VISIBILITY',NOW());

-- Notification Type
INSERT INTO notificationtypemaster (NotificationType, NotificationName, ModDt) VALUES 
('GN','GENERAL',NOW()), 
('CV','COVID-19',NOW());

-- Language Master
INSERT INTO languagemaster (LanguageCode, LanguageDescription)
VALUES ('EN', 'English'),
       ('TM', 'Tamil'),
       ('HN', 'Hindi'),
       ('PB', 'Punjabi');

-- Category Sequence - Others
INSERT INTO categorysequence (Category, SequenceNo, ModDt)
VALUES ('Focus', 1, NOW()),
('Must Sell', 2, NOW()),
('Promo', 3, NOW());

-- Category Sequence - Nestle
INSERT INTO categorysequence (Category, SequenceNo, ModDt)
VALUES ('IND', 1, NOW()),
('INE', 2, NOW()),
('27F', 3, NOW()),
('27G', 4, NOW()),
('27H', 5, NOW()),
('INC', 6, NOW()),
('INA', 7, NOW()),
('INF', 8, NOW()),
('27O', 9, NOW()),
('Business ID', 10, NOW());

-- Banner Image Template Default Value for each company
INSERT INTO bannerimagetemplate (CmpCode, BannerDesc, FileName, DistrCode, UploadFlag, ModDt)
VALUES ('AMUL', 'AMUL Default Banner', 'AMUL.jpeg', null, 'N', NOW()),
       ('DIL', 'Dabur Default Banner', 'DIL.jpeg', null, 'N', NOW()),
       ('IN14', 'Nestle Default Banner', 'IN14.jpeg', null, 'N', NOW()),
       ('Perfetti', 'Perfetti Default Banner', 'Perfetti.jpeg', null, 'N', NOW());


