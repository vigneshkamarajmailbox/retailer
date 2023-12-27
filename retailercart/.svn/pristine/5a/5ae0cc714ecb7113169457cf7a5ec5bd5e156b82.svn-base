-- process type T transcation from RSSFA to console
-- process type M data from console to RSSFA

-- master
-- company
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'company',
'M',
'Y',
'SELECT 
  CmpCode, 
  CmpName, 
  CmpAddr1, 
  CmpAddr2, 
  CmpAddr3, 
  City, 
  State, 
  Country, 
  PostalCode, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_Company(NOLOCK)',
'',
'com.botree.interdbentity.model.CompanyEntity',
1,
null,
null,
null,
'N',
'N',
0,
'24HRS');

-- customer
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'customer',
'M',
'Y',
'SELECT 
  CmpCode, 
  DistrCode, 
  CustomerCode, 
  CustomerName, 
  MobileNo, 
  PinCode, 
  PhoneNo, 
  ContactPerson, 
  EmailID, 
  RetailerStatus, 
  FssaiNo, 
  CreditBills, 
  CreditDays, 
  CreditLimit, 
  CashDiscPerc, 
  ChannelCode, 
  SubChannelCode, 
  GroupCode, 
  ClassCode, 
  StoreType, 
  ParentCustomerCode, 
  Latitude, 
  Longitude, 
  CustomerType, 
  GSTTinNo, 
  PanNo, 
  District, 
  OutstandingAmt, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_Customer(NOLOCK) 
WHERE 
  DistrCode IN (:ids) 
  AND MobileNo IS NOT NULL 
  AND LEN(MobileNo) = 10',
'',
'com.botree.interdbentity.model.CustomerEntity',
2,
null,
null,
null,
'Y',
'N',
0,
'24HRS');



-- customer route mappping
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'customerroutemapping',
'M',
'Y',
'SELECT 
  cr.CmpCode, 
  cr.DistrCode, 
  cr.CustomerCode, 
  cr.RouteCode, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_CustomerRouteMapping(NOLOCK) cr 
  INNER JOIN Console2RtrApp_Customer c ON c.CmpCode = cr.CmpCode 
  AND c.DistrCode = cr.DistrCode 
  AND c.CustomerCode = cr.CustomerCode 
WHERE 
  c.DistrCode IN (:ids) 
  AND c.MobileNo IS NOT NULL 
  AND LEN(c.MobileNo) = 10',
'',
'com.botree.interdbentity.model.CustomerRouteMappingEntity',
3,
null,
null,
null,
'Y',
'N',
0,
'24HRS');

-- customershipaddress
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'customershipaddress',
'M',
'Y',
'SELECT 
  DISTINCT csa.CmpCode, 
  csa.DistrCode, 
  csa.CustomerCode, 
  csa.CustomerShipCode, 
  csa.CustomerShipAddr1, 
  csa.CustomerShipAddr2, 
  csa.CustomerShipAddr3, 
  csa.City, 
  csa.GSTStateCode, 
  csa.PhoneNo, 
  csa.DefaultShipAddr, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_CustomerShipAddress(NOLOCK) csa 
  INNER JOIN Console2RtrApp_Customer c ON c.CmpCode = csa.CmpCode 
  AND c.DistrCode = csa.DistrCode 
  AND c.CustomerCode = csa.CustomerCode 
WHERE 
  c.DistrCode IN (:ids) 
  AND c.MobileNo IS NOT NULL 
  AND LEN(c.MobileNo) = 10',
'',
'com.botree.interdbentity.model.CustomerShipAddressEntity',
4,
null,
null,
null,
'Y',
'N',
0,
'24HRS');

-- distributor
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'distributor',
'M',
'Y',
'SELECT 
  CmpCode, 
  DistrCode, 
  DistrName, 
  DistrAddr1, 
  DistrAddr2, 
  DistrAddr3, 
  PinCode, 
  PhoneNo, 
  MobileNo, 
  ContactPerson, 
  EmailID, 
  GSTStateCode, 
  DayOff, 
  DistrStatus, 
  LoadStockProd, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_Distributor(NOLOCK)
WHERE 
  DistrCode IN (:ids)',
'',
'com.botree.interdbentity.model.DistributorEntity',
5,
null,
null,
null,
'Y',
'N',
0,
'24HRS');

-- distributorlobmapping
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'distributorlobmapping',
'M',
'Y',
'SELECT 
  CmpCode, 
  DistrCode, 
  LOBCode, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_DistributorLOBMapping(NOLOCK) 
WHERE 
  DistrCode IN (:ids)',
'',
'com.botree.interdbentity.model.DistributorLOBMappingEntity',
6,
null,
null,
null,
'Y',
'N',
0,
'24HRS');



-- distributorsaleshierarchy
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'distributorsaleshierarchy',
'M',
'Y',
'SELECT 
  CmpCode, 
  DistrCode, 
  salesForceCode, 
  LOBCode, 
  SalesHierPath, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_DistributorSalesHierarchy(NOLOCK) 
WHERE 
  DistrCode IN (:ids)',
'',
'com.botree.interdbentity.model.DistributorSalesHierarchyEntity',
7,
null,
null,
null,
'Y',
'N',
0,
'24HRS');

-- lobmaster
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'lobmaster',
'M',
'Y',
'SELECT 
  CmpCode, 
  LOBCode, 
  LOBName, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_LOBMaster(NOLOCK)',
'',
'com.botree.interdbentity.model.LOBMasterEntity',
8,
null,
null,
null,
'N',
'N',
0,
'24HRS');

-- producthierlevel
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'producthierlevel',
'M',
'Y',
'SELECT CmpCode,
       ProdHierLvlCode,
       ProdHierLvlName,
       ''N'' AS UploadFlag,
       GETDATE() AS ModDt
FROM Console2RtrApp_ProductHierLevel(NOLOCK)',
'',
'com.botree.interdbentity.model.ProductHierLevelEntity',
9,
null,
null,
null,
'N',
'N',
0,
'24HRS');

-- producthiervalue
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'producthiervalue',
'M',
'Y',
'SELECT CmpCode,
       ProdHierLvlCode,
       ProdHierValCode,
       ProdHierValName,
       ParentCode,
       ''N'' AS UploadFlag,
       GETDATE() AS ModDt
FROM Console2RtrApp_ProductHierValue(NOLOCK)',
'',
'com.botree.interdbentity.model.ProductHierValueEntity',
10,
null,
null,
null,
'N',
'N',
0,
'24HRS');

-- product
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'product',
'M',
'Y',
'SELECT 
  p.CmpCode, 
  p.ProdCode, 
  p.ProdName, 
  p.ProdShortName, 
  p.ProdStatus, 
  p.ProdType, 
  p.ProdShelfLife, 
  p.ProdNetWgt, 
  p.ProdWgtType, 
  l.LOBCode, 
  l.LOBName, 
  p.HsnCode, 
  p.HsnName, 
  p.ProductHierPath AS ProductHierPathCode, 
  p.ProductHierPathName, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_Product(NOLOCK) p 
  INNER JOIN Console2RtrApp_LOBMaster l ON p.CmpCode = l.CmpCode 
  AND p.ProductHierPath LIKE CONCAT(''%/'', l.LOBCode, ''/%'')',
'',
'com.botree.interdbentity.model.ProductEntity',
11,
null,
null,
null,
'N',
'N',
0,
'24HRS');

-- productbatch
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'productbatch',
'M',
'Y',
'SELECT 
  CmpCode, 
  BatchLevel, 
  ProdCode, 
  ProdBatchCode, 
  MRP, 
  SellPrice AS SellRate, 
  SellRateWithTax, 
  ManfDate, 
  ExpiryDate, 
  LatestBatch, 
  GeoLevelBatch, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_ProductBatch(NOLOCK) 
WHERE 
  BatchLevel IN (:ids)',
'',
'com.botree.interdbentity.model.ProductBatchEntity',
12,
null,
null,
null,
'Y',
'N',
0,
'24HRS');

-- productuom
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'productuom',
'M',
'Y',
'SELECT 
  CmpCode, 
  ProdCode, 
  UOMCode, 
  UOMDescription, 
  UomConvFactor, 
  BaseUOM, 
  DefaultUOM, 
  ''N'' AS UploadFlag, 
  GETDATE() AS ModDt 
FROM 
  Console2RtrApp_ProductUOM(NOLOCK)
',
'',
'com.botree.interdbentity.model.ProductUomEntity',
13,
null,
null,
null,
'N',
'N',
0,
'24HRS');

-- retailercategory
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'retailercategory',
'M',
'Y',
'SELECT CmpCode,
       ChannelCode,
       ChannelName,
       SubChannelCode,
       SubChannelName,
       GroupCode,
       GroupName,
       ClassCode,
       ClassName,
       ''N'' AS UploadFlag,
       GETDATE() AS ModDt
FROM Console2RtrApp_RetailerCategory(NOLOCK)',
'',
'com.botree.interdbentity.model.RetailerCategoryEntity',
14,
null,
null,
null,
'N',
'N',
0,
'24HRS');

-- route
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'route',
'M',
'Y',
'SELECT CmpCode,
       DistrCode,
       RouteCode,
       RouteName,
       RouteType,
       RouteStatus,
       ''N'' AS UploadFlag, 
       GETDATE() AS ModDt
FROM Console2RtrApp_Route(NOLOCK)
WHERE DistrCode IN (:ids)',
'',
'com.botree.interdbentity.model.RouteEntity',
15,
null,
null,
null,
'Y',
'N',
0,
'24HRS');


-- salesman
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'salesman',
'M',
'Y',
'SELECT CmpCode,
       DistrCode,
       SalesmanCode,
       SalesmanName,
       MobileNo,
       EmailID,
       Status,
       ''N'' AS UploadFlag,
       GETDATE() AS ModDt
FROM Console2RtrApp_Salesman(NOLOCK)
WHERE DistrCode IN (:ids)',
'',
'com.botree.interdbentity.model.SalesmanEntity',
16,
null,
null,
null,
'Y',
'N',
0,
'24HRS');


-- salesmanroutemapping
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'salesmanroutemapping',
'M',
'Y',
'SELECT CmpCode,
       DistrCode,
       SalesmanCode,
       RouteCode,
       CoverageDay,
       ''N'' AS UploadFlag,
       GETDATE() AS ModDt
FROM Console2RtrApp_SalesmanRouteMapping(NOLOCK)
WHERE DistrCode IN (:ids)',
'',
'com.botree.interdbentity.model.SalesmanRouteMappingEntity',
17,
null,
null,
null,
'Y',
'N',
0,
'24HRS');


-- schemedefinition
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'schemedefinition',
'M',
'Y',
'SELECT DISTINCT CmpCode,
                SchemeCode,
                SchemeDescription,
                SchemeBase,
                SchemeFromDt,
                SchemeToDt,
                PayOutType,
                IsSkuLevel,
                IsActive,
                Combi,
                ''N''   AS UploadFlag,
                GETDATE() 	AS ModDt
FROM Console2RtrApp_SchemeDefinition
WHERE GETDATE() BETWEEN SchemeFromDt AND SchemeToDt',
'',
'com.botree.interdbentity.model.SchemeDefinitionEntity',
18,
null,
null,
null,
'N',
'N',
0,
'24HRS');

-- schemedistributorbudget
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'schemedistributorbudget',
'M',
'Y',
'SELECT CmpCode,
       SchemeCode,
       DistrCode,
       IsUnlimited,
       Budget,
       IsActive,
       UtilizedAmt,
       BudgetOs,
       ''N'' AS UploadFlag,
       GETDATE() AS ModDt
FROM Console2RtrApp_SchemeDistributorBudget(NOLOCK)',
'',
'com.botree.interdbentity.model.SchemeDistributorBudgetEntity',
19,
null,
null,
null,
'N',
'N',
0,
'24HRS');


-- schemeslab
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'schemeslab',
'M',
'Y',
'SELECT CmpCode,
       SchemeCode,
       SlabNo,
       SlabFrom,
       SlabTo,
       Payout,
       SlabRange,
       Forevery,
       UomCode,
       ''N'' AS UploadFlag, 
       GETDATE() AS ModDt
FROM Console2RtrApp_SchemeSlab(NOLOCK)',
'',
'com.botree.interdbentity.model.SchemeSlabEntity',
20,
null,
null,
null,
'N',
'N',
0,
'24HRS');



-- schemecustomer
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'schemecustomer',
'M',
'Y',
'SELECT CmpCode,
       SchemeCode,
       CustomerCode,
       ''N'' AS UploadFlag, 
       GETDATE() AS ModDt
FROM Console2RtrApp_SchemeCustomer(NOLOCK)',
'',
'com.botree.interdbentity.model.SchemeCustomerEntity',
21,
null,
null,
null,
'N',
'N',
0,
'24HRS');


-- schemeretailercategory
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'schemeretailercategory',
'M',
'Y',
'SELECT CmpCode,
       SchemeCode,
       ChannelCode,
       SubChannelCode,
       GroupCode,
       ClassCode,
       ''N'' AS UploadFlag, 
       GETDATE() AS ModDt
FROM Console2RtrApp_SchemeRetailerCategory(NOLOCK)',
'',
'com.botree.interdbentity.model.SchemeRetailerCategoryEntity',
22,
null,
null,
null,
'N',
'N',
0,
'24HRS');


-- schemeproduct
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'schemeproduct',
'M',
'Y',
'SELECT CmpCode,
       SchemeCode,
       ProdCode,
       ProdName,
       ''N'' AS UploadFlag, 
       GETDATE() AS ModDt
FROM Console2RtrApp_SchemeProduct(NOLOCK)',
'',
'com.botree.interdbentity.model.SchemeProductEntity',
23,
null,
null,
null,
'N',
'N',
0,
'24HRS');


-- schemeproductcategory
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'schemeproductcategory',
'M',
'Y',
'SELECT CmpCode,
       SchemeCode,
       ProdHierLvlCode,
       ProdHierValCode,
       ProdHierValName,
       ''N'' AS UploadFlag, 
       GETDATE() AS ModDt
FROM Console2RtrApp_SchemeProductCategory(NOLOCK)',
'',
'com.botree.interdbentity.model.SchemeProductCategoryEntity',
24,
null,
null,
null,
'N',
'N',
0,
'24HRS');


-- schemeproductcategory
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'taxstructure',
'M',
'Y',
'SELECT CmpCode,
       TaxStateCode,
       ProdCode,
       CGST,
       SGST,
       IGST,
       CESS,
       AdditionalTax,
       ''N'' AS UploadFlag, 
       GETDATE() AS ModDt
FROM Console2RtrApp_Taxstructure(NOLOCK)',
'',
'com.botree.interdbentity.model.TaxStructureEntity',
25,
null,
null,
null,
'N',
'N',
0,
'24HRS');


-- schemeslabproduct
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'schemeslabproduct',
'M',
'Y',
'SELECT ssp.CmpCode,
       ssp.DistrCode,
       ssp.SchemeCode,
       ssp.SlabNo,
       ssp.ProdCode,
       ssp.ProdName,
       ssp.Qty,
       ssp.IsMandatory,
       ''N''   	AS UploadFlag,
       NOW() 	AS ModDt
FROM schemeslabproduct ssp
WHERE ssp.SchemeCode IN (SELECT DISTINCT sd.SchemeCode
                        FROM schemedefinition sd
                        WHERE CURDATE() BETWEEN SchemeFromDt AND SchemeToDt
                          AND sd.DistrCode IN (:ids))
AND ssp.DistrCode IN (:ids)',
'',
'com.botree.interdbentity.model.SchemeSlabProductEntity',
17,
null,
null,
null,
'Y',
'N',
0,
'24HRS');


INSERT INTO ExternalProcessDetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Intervals)
VALUES
('DIL',
'order',
'T',
'Y',
'',
'',
'com.botree.interdbentity.model.OrderBookingHeaderEntity',
1,
null,
null,
null,
'Y',
'N');


-- Login Referral
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'loginReferral',
'T',
'Y',
'',
'',
'com.botree.interdbentity.model.LoginReferralEntity',
1,
null,
null,
null,
'N',
'N',
0,
'24HRS');


-- Order Referral
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'orderReferral',
'T',
'Y',
'',
'',
'com.botree.interdbentity.model.OrderReferralEntity',
1,
null,
null,
null,
'N',
'N',
0,
'24HRS');


-- Sync Log
INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'syncLog',
'T',
'Y',
'',
'',
'com.botree.interdbentity.model.SyncLogEntity',
1,
null,
null,
null,
'N',
'N',
0,
'24HRS');



INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'Widget',
'M',
'Y',
'SELECT CmpCode,
    DistrCode,
    CustomerCode,
    DisplayCode1,
    DisplayValue1,
    DisplayIcon1,
    DisplayColor1,
    DisplayCode2,
    DisplayValue2,
    DisplayIcon2,
    DisplayColor2,
    DisplayCode3,
    DisplayValue3,
    DisplayIcon3,
    DisplayColor3,
    DisplayCode4,
    DisplayValue4,
    DisplayIcon4,
    DisplayColor4,
    DisplayCode5,
    DisplayValue5,
    DisplayIcon5,
    DisplayColor5,
    DisplayCode6,
    DisplayValue6,
    DisplayIcon6,
    DisplayColor6,
    DisplayCode7,
    DisplayValue7,
    DisplayIcon7,
    DisplayColor7,
    DisplayCode8,
    DisplayValue8,
    DisplayIcon8,
    DisplayColor8,
    DisplayCode9,
    DisplayValue9,
    DisplayIcon9,
    DisplayColor9,
    DisplayCode10,
    DisplayValue10,
    DisplayIcon10,
    DisplayColor10,
    ''N'' AS UploadFlag, 
    GETDATE() AS ModDt
FROM Console2RtrApp_Dashboard_Widget(NOLOCK)',
'',
'com.botree.interdbentity.model.DashboardEntity',
1,
null,
null,
null,
'N',
'N',
0,
'24HRS');




INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'CustomerStock',
'M',
'Y',
'SELECT CmpCode,
   DistrCode,
   CustomerCode,
   Stock,
   ''N'' AS UploadFlag, 
   GETDATE() AS ModDt
FROM RtrApptoConsole_CustomerStock',
'',
'com.botree.interdbentity.model.CustomerStockEntity',
1,
null,
null,
null,
'N',
'N',
0,
'24HRS');


INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'DistributorStock',
'M',
'Y',
'SELECT 
	CmpCode,
	DistrCode,
	SaleableStock,
      	''N'' AS UploadFlag, 
   	GETDATE() AS ModDt
FROM Console2RtrApp_DistributorStock',
'',
'com.botree.interdbentity.model.DistributorStockEntity',
1,
null,
null,
null,
'N',
'N',
0,
'24HRS');



INSERT INTO externalprocessdetail
(CmpCode,
InterDBProcess,
ProcessType,
ProcessEnable,
InterDBQuery,
InterDBUpdateQuery,
Entity,
ProcessSequence,
ProcessStatus,
StartDate,
EndDate,
DistributorWise,
Incremental,
MaxChangeNo,
Intervals)
VALUES
('DIL',
'CompanyUser',
'M',
'Y',
'SELECT 
    CmpCode,
    LoginCode,
    UserName,
    UserType,
    MappedCode,
    EmailId,
    MobileNo,
    UserStatus,
    UploadFlag,
    ModDt
FROM
    Console2Rtrapp_CompanyUser(NOLOCK)',
'',
'com.botree.interdbentity.model.CompanyUserEntity',
1,
null,
null,
null,
'N',
'N',
0,
'24HRS');






