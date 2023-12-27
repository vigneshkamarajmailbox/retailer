DROP TABLE ExternalProcessDetail;

CREATE TABLE ExternalProcessDetail(
CmpCode varchar(10) NOT NULL,
InterDBProcess varchar(50) NOT NULL,
ProcessType char(1) NOT NULL,
ProcessEnable char(1) NOT NULL,
InterDBQuery TEXT NOT NULL,
InterDBUpdateQuery TEXT DEFAULT NULL,
Entity varchar(100) NULL,
ProcessSequence int NOT NULL,
ProcessStatus varchar(10) DEFAULT NULL,
StartDate datetime DEFAULT NULL,
EndDate datetime DEFAULT NULL,
DistributorWise char(1) DEFAULT 'Y',
Incremental CHAR(1) DEFAULT 'N',
MaxChangeNo INT DEFAULT 0,
Intervals VARCHAR(10) DEFAULT '24HRS',
PRIMARY KEY (CmpCode, InterDBProcess, ProcessType)
);

CREATE TABLE RetailerSSFAProduct(
CmpCode varchar(10) NOT NULL,
DistrCode varchar(50) NOT NULL,
ProdCode varchar(50) NOT NULL,
PRIMARY KEY (CmpCode, DistrCode, ProdCode)
);

CREATE TABLE RetailerSSFARetailer(
CmpCode varchar(10) NOT NULL,
CustomerCode varchar(50) NOT NULL,
PRIMARY KEY (CmpCode, CustomerCode)
);

-- master
-- company
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
DistributorWise)
VALUES
('TGBL',
'company',
'M',
'Y',
'SELECT c.CmpCode,
       CmpName,
       CmpAddr1,
       CmpAddr2,
       CmpAddr3,
       (SELECT city.geoName FROM geoHierValue city WHERE city.cmpCode=c.cmpCode and city.geoCode=c.city) as City,
       (SELECT state.geoName FROM geoHierValue state WHERE state.cmpCode=c.cmpCode and state.geoCode=c.cmpState) as State,
       (SELECT country.geoName FROM geoHierValue country WHERE country.cmpCode=c.cmpCode and country.geoCode=c.country) as Country,
       PostalCode,
       ''N'' AS UploadFlag,
       NOW() AS ModDt
FROM company c',
'',
'com.botree.interdbentity.model.CompanyEntity',
1,
null,
null,
null,
'N');

-- customer
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'customer',
'M',
'Y',
'SELECT DISTINCT c.cmpcode,
                   c.distrcode,
                   c.customercode,
                   c.customername,
                   c.phone                         AS MobileNo,
                   csa.postalcode                  AS PinCode,
                   c.phone                         AS PhoneNo,
                   c.contactperson,
                   c.emailid,
                   c.isactive                      AS RetailerStatus,
                   c.fssaino,
                   c.creditbills,
                   c.creditdays,
                   c.creditlimit,
                   c.cashdiscperc,
                   SUBSTRING_INDEX(c.channelcode,''/'',1)              As channelcode,
                   SUBSTRING_INDEX(c.channelcode,''/'',-1)                   AS SubChannelCode,
                   c.retlrgroupcode                AS GroupCode,
                   c.classcode,
                   c.storetype,
                   ''''                              AS ParentCustomerCode,
                   c.latitude,
                   c.longitude,
                   COALESCE(gcm.retailertype, ''U'') AS CustomerType,
                   csa.gsttinno,
                   gcm.panno,
                   status                          AS ApprovalStatus,
                   ''N''                             AS UploadFlag,
                   NOW()                       AS ModDt
   FROM   customer c
          INNER JOIN changelog cl
                  ON c.cmpcode = cl.cmpcode
                     AND c.cmpcode = cl.key1
                     AND c.customercode = cl.key2
          INNER JOIN customershipaddress csa
                  ON c.cmpcode = csa.cmpcode
                     AND c.customercode = csa.customercode
   	   INNER JOIN retailerssfaretailer rsfc		 
                  ON c.cmpcode = rsfc.cmpcode
                     AND c.customercode = rsfc.customercode	   
          INNER JOIN gstcustomermaster gcm
                  ON c.cmpcode = gcm.cmpcode
                     AND c.customercode = gcm.customercode
          INNER JOIN customerroute cr
                  ON c.cmpcode = cr.cmpcode
                     AND c.distrcode = cr.distrcode
                     AND c.distrbrcode = cr.distrbrcode
                     AND c.customercode = cr.customercode
          INNER JOIN route r
                  ON cr.cmpcode = R.cmpcode
                     AND cr.distrcode = R.distrcode
                     AND cr.distrbrcode = R.distrbrcode
                     AND cr.routecode = R.routecode
          INNER JOIN salesmanroute sr
                  ON cr.cmpcode = sr.cmpcode
                     AND cr.distrcode = sr.distrcode
                     AND cr.distrbrcode = sr.distrbrcode
                     AND cr.routecode = sr.routecode
          INNER JOIN salesman s
                  ON sr.cmpcode = s.cmpcode
                     AND sr.distrcode = s.distrcode
                     AND sr.distrbrcode = s.distrbrcode
                     AND sr.salesmancode = s.salesmancode
   WHERE  cl.objecttype = ''com.botree.csng.domain.Customer''
          AND cl.changeno between :minChangeNo and :maxChangeNo      
          AND c.cmpCode = ''TGBL''        
          AND cl.cmpCode = ''TGBL''     
          AND Length(c.phone) = 10
          AND csa.isdefault = ''Y''
          AND r.routetype = ''S''
          AND r.isactive = ''Y''
          AND s.isactive = ''Y''
          AND NOT EXISTS(SELECT 1
                         FROM   customer Cust
                         WHERE  Cust.cmpcode = c.cmpcode
                                AND cust.distrcode = c.distrcode
                                AND cust.phone = C.phone
                         GROUP  BY cust.phone
                         HAVING Count(DISTINCT cust.customercode) > 1)',
'',
'com.botree.interdbentity.model.CustomerEntity',
2,
null,
null,
null,
'Y','N');
-- customershipaddress
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'customershipaddress',
'M',
'Y',
'SELECT DISTINCT c.cmpcode,
                   c.distrcode,
                   csa.customercode,
                   customershipcode,
                   customershipaddr1,
                   customershipaddr2,
                   customershipaddr3,
                   city,
                   gststatecode,
                   ''''        AS PhoneNo,
                   isdefault AS DefaultShipAddr,
                   ''N''       AS UploadFlag,
                   NOW() AS ModDt
   FROM   customershipaddress csa
          INNER JOIN customer c
                  ON csa.cmpcode = c.cmpcode
                     AND csa.customercode = c.customercode     
   	   INNER JOIN retailerssfaretailer rsfc		 
                  ON c.cmpcode = rsfc.cmpcode
                     AND c.customercode = rsfc.customercode	   				  
          INNER JOIN changelog cl
                             ON c.cmpcode = cl.cmpcode
                                AND c.cmpcode = cl.key1
                                AND c.customercode = cl.key2
          INNER JOIN customerroute cr
                  ON c.cmpcode = cr.cmpcode
                     AND c.distrcode = cr.distrcode
                     AND c.distrbrcode = cr.distrbrcode
                     AND c.customercode = cr.customercode
          INNER JOIN route r
                  ON cr.cmpcode = R.cmpcode
                     AND cr.distrcode = R.distrcode
                     AND cr.distrbrcode = R.distrbrcode
                     AND cr.routecode = R.routecode
          INNER JOIN salesmanroute sr
                  ON cr.cmpcode = sr.cmpcode
                     AND cr.distrcode = sr.distrcode
                     AND cr.distrbrcode = sr.distrbrcode
                     AND cr.routecode = sr.routecode
          INNER JOIN salesman s
                  ON sr.cmpcode = s.cmpcode
                     AND sr.distrcode = s.distrcode
                     AND sr.distrbrcode = s.distrbrcode
                     AND sr.salesmancode = s.salesmancode
   WHERE  cl.changeno between :minChangeNo and :maxChangeNo
          AND Length(c.phone) = 10
          AND r.routetype = ''S''
          AND r.isactive = ''Y''
          AND s.isactive = ''Y'' 
          AND c.cmpCode = ''TGBL''     
          AND cl.cmpCode = ''TGBL''    
          AND cl.objecttype = ''com.botree.csng.domain.Customer''
          AND NOT EXISTS(SELECT 1
                         FROM   customer Cust
                         WHERE  Cust.cmpcode = c.cmpcode
                                AND cust.distrcode = c.distrcode
                                AND cust.phone = C.phone
                         GROUP  BY cust.phone
                         HAVING Count(DISTINCT cust.customercode) > 1)',
'',
'com.botree.interdbentity.model.CustomerShipAddressEntity',
3,
null,
null,
null,'Y','N');

-- Distributor
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'distributor',
'M',
'Y',
'SELECT distinct d.CmpCode,
       d.DistrCode,
       DistrName,
       db.DistrBrAddr1 as DistrAddr1,
       db.DistrBrAddr2 as DistrAddr2,
       db.DistrBrAddr3 as DistrAddr3,
       PostalCode as PinCode,
       Phone as PhoneNo,
       Phone as MobileNo,
       ContactPerson,
       EmailID,
       gd.GSTStateCode,
       0 as DayOff,
       d.isActive as DistrStatus,
       ''Y'' as LoadStockProd,
       ''N'' AS UploadFlag,
       NOW() AS ModDt
    FROM Distributor d
    INNER JOIN DistributorBranch db
        ON d.cmpCode=db.cmpCode
        AND d.distrCode=db.distrCode
    INNER JOIN GSTDistributor gd
        ON d.cmpCode=gd.cmpCode
        and d.distrCode=gd.distrcode
    INNER JOIN Changelog cl
        ON d.cmpcode=cl.cmpcode
        AND d.distrCode=cl.distrCode
        AND d.cmpCode = cl.key1
        AND d.distrCode = cl.key2
    WHERE cl.changeNo between :minChangeNo and :maxChangeNo
    AND d.distrcode IN (:ids)
    AND cl.objectType=''com.botree.csng.domain.Distributor''',
'',
'com.botree.interdbentity.model.DistributorEntity',
4,
null,
null,
null,
'Y','Y');

-- distributorlobmapping
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'distributorlobmapping',
'M',
'Y',
'SELECT distinct dsh.CmpCode,
       dsh.DistrCode,
       LOBCode,
       ''N''   	AS UploadFlag,
       NOW() 	AS ModDt
    FROM distributorsaleshierarchy dsh
    INNER JOIN Changelog cl
        ON dsh.cmpcode=cl.cmpcode
        AND dsh.distrCode=cl.distrCode
        AND dsh.cmpCode = cl.key1
        AND dsh.distrCode = cl.key2
    WHERE cl.objectType=''com.botree.csng.domain.DistributorSalesHierarchy''
    AND dsh.distrcode IN (:ids)
    AND cl.changeNo between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.DistributorLOBMappingEntity',
5,
null,
null,
null,'Y','Y');

-- lobmaster
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
DistributorWise)
VALUES
('TGBL',
'lobmaster',
'M',
'Y',
'SELECT DISTINCT CmpCode,
       LOBCode,
       LOBName,
       ''N'' AS UploadFlag,
       NOW() AS ModDt
FROM lobmaster',
'',
'com.botree.interdbentity.model.LOBMasterEntity',
6,
null,
null,
null,'N');


-- producthierlevel
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
DistributorWise)
VALUES
('TGBL',
'producthierlevel',
'M',
'Y',
'SELECT CmpCode,
       ProdHierLvlCode,
       ProdHierLvlName,
       ''N'' AS UploadFlag,
       NOW() AS ModDt
FROM producthierlevel',
'',
'com.botree.interdbentity.model.ProductHierLevelEntity',
7,
null,
null,
null,
'N');

-- producthiervalue
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'producthiervalue',
'M',
'Y',
'SELECT Distinct phv.CmpCode,
       ProdHierLvlCode,
       ProdHierValCode,
       ProdHierValName,
       ParentCode,
       ''N'' AS UploadFlag,
       NOW() AS ModDt
       FROM producthiervalue phv
       INNER JOIN Changelog cl
            ON phv.cmpcode=phv.cmpcode
            AND phv.cmpCode = cl.key1
            AND phv.ProdHierLvlCode = cl.key2
            AND phv.ProdHierValCode = cl.key3
       WHERE cl.changeNo between :minChangeNo and :maxChangeNo
       AND cl.objectType=''com.botree.csng.domain.ProductHierValue''',
'',
'com.botree.interdbentity.model.ProductHierValueEntity',
8,
null,
null,
null,'Y','N');

-- product
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'product',
'M',
'Y',
'SELECT distinct p.CmpCode,
                p.ProdCode,
                p.ProdName,
                (CASE WHEN p.ProdShortName IS NULL THEN p.ProdName ELSE p.ProdShortName END) AS ProdShortName,
                p.isActive as ProdStatus,
                p.ProdType,
                p.ShelfLife as ProdShelfLife,
                p.ProdNetWgt,
                p.ProdWgtType,
                lm.LOBCode,
                lm.LOBName,
                gp.HSNCode,
                gp.HSNName,
                p.ProductHierPath as ProductHierPathCode,
                CONCAT(''/'',cmp.prodHierValName,
                       ''/'',prodhier1.prodHierValName,
                       ''/'',prodhier2.ProdHierValName,
                       ''/'',prodhier3.ProdHierValName,
                       ''/'',prodhier4.ProdHierValName,
                       ''/'',prodhier5.ProdHierValName,
                       ''/'',prodhier6.ProdHierValName,
                       ''/'',prodhier7.ProdHierValName,
                       ''/'',prodhier8.ProdHierValName,					   
                       ''/'') as ProductHierPathName,
                ''N''     AS UploadFlag,
                NOW() AS ModDt
         FROM Product p
         INNER JOIN GstProduct gp
            ON p.CmpCode = gp.CmpCode
            AND p.prodCode = gp.prodCode
         INNER JOIN Changelog cl
            ON p.cmpcode=cl.cmpcode
            AND p.cmpCode = cl.key1
            AND p.prodCode = cl.key2    
         INNER JOIN ProductHierValue prodhier8
             ON p.cmpCode=prodhier8.cmpcode
             AND p.prodhiervalcode=prodhier8.prodhiervalcode
         INNER JOIN ProductHierValue prodhier7
             ON prodhier8.cmpCode=prodhier7.cmpcode
             AND  prodhier8.parentCode=prodhier7.prodhiervalcode
         INNER JOIN ProductHierValue prodhier6
             ON prodhier7.cmpCode=prodhier6.cmpcode
             AND  prodhier7.parentCode=prodhier6.prodhiervalcode
         INNER JOIN ProductHierValue prodhier5
             ON prodhier6.cmpCode=prodhier5.cmpcode
             AND  prodhier6.parentCode=prodhier5.prodhiervalcode
         INNER JOIN ProductHierValue prodhier4
             ON prodhier5.cmpCode=prodhier4.cmpcode
             AND  prodhier5.parentCode=prodhier4.prodhiervalcode
         INNER JOIN ProductHierValue prodhier3
             ON prodhier4.cmpCode=prodhier3.cmpcode
             AND  prodhier4.parentCode=prodhier3.prodhiervalcode
         INNER JOIN ProductHierValue prodhier2
             ON prodhier3.cmpCode=prodhier2.cmpcode
             AND  prodhier3.parentCode=prodhier2.prodhiervalcode
         INNER JOIN ProductHierValue prodhier1
             ON prodhier2.cmpCode=prodhier1.cmpcode
             AND  prodhier2.parentCode=prodhier1.prodhiervalcode			 
         INNER JOIN ProductHierValue cmp
             ON prodhier1.cmpCode=cmp.cmpcode
             AND  prodhier1.parentCode=cmp.prodhiervalcode     
          INNER JOIN LobMaster lm
             ON prodhier1.cmpCode=lm.cmpCode
             AND prodhier1.prodhiervalcode=lm.prodHierValCode
             AND prodhier1.prodHierLvlCode=lm.prodHierLvlCode    			 
WHERE cl.objectType=''com.botree.csng.domain.Product''
AND cl.changeNo between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.ProductEntity',
9,
null,
null,
null,'Y','N');


-- productbatch
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'productbatch',
'M',
'Y',
'SELECT distinct pp.CmpCode,
       dp.DistrCode AS BatchLevel,
       pp.ProdCode,
       pp.ProdBatchCode,
       pp.MRP,
       pp.sellPrice as SellRate,
       0.00 as SellRateWithTax,
       pb.ManfDt as ManfDate,
       pb.expiryDt as ExpiryDate,
       (CASE WHEN pb.ModDt = (SELECT Max(pb1.ModDt)
                 FROM   productpricing ppm,productbatch pb1
                 WHERE  ppm.Cmpcode = pp.CmpCode
                 AND ppm.ProdCode = pp.ProdCode
                 AND ppm.GeoCode = pp.GeoCode
                 AND ppm.EffectiveFromDate <= NOW()
                 AND ppm.FromLevel = pp.FromLevel
                 AND ppm.ToLevel = pp.ToLevel
                 AND ppm.cmpCode = pb1.cmpCode
                 AND ppm.prodCode = pb1.prodCode
                 AND ppm.prodBatchCode = pb1.prodBatchCode
				 AND pb1.ExpiryDt >= DATE_FORMAT(NOW(),''%Y-%m-%d'')) THEN ''Y'' ELSE ''N'' END)  as LatestBatch,
       ''N''     AS GeoLevelBatch,
       ''N''     AS UploadFlag,
       NOW()     AS ModDt
FROM DistributorProduct dp
        INNER JOIN distributorbranch db
                    ON db.CmpCode = dp.CmpCode
                        AND db.DistrCode = dp.DistrCode
        INNER JOIN supplychainmaster scm
                    ON scm.CmpCode = db.CmpCode
                        AND scm.MemberCode = db.DistrCode        
        INNER JOIN retailerssfaproduct rsfp
                    ON rsfp.CmpCode = dp.CmpCode
                        AND rsfp.DistrCode = dp.DistrCode						
						AND rsfp.ProdCode = dp.ProdCode              	
        INNER JOIN productpricing pp
                ON dp.CmpCode = pp.CmpCode
                    AND dp.ProdCode = pp.ProdCode
                    AND db.cmpCode=pp.cmpCode
                    AND db.distrbrstate = pp.geocode
                    AND pp.FromLevel = scm.LevelCode
                    AND pp.ToLevel = 9
                    AND pp.EffectiveFromDate <= NOW()
           INNER JOIN productbatch pb
                   ON dp.CmpCode = pb.CmpCode
                       AND pp.ProdCode = pb.ProdCode
                       AND pp.ProdBatchCode = pb.ProdBatchCode
					   AND pb.ExpiryDt >= DATE_FORMAT(NOW(),''%Y-%m-%d'')   
    INNER JOIN GeoHierValue ghv
        on pp.cmpCode=ghv.cmpCode
        AND pp.geoCode = ghv.geoCode  
    INNER JOIN Changelog cl
           ON pb.cmpcode=cl.cmpcode
           AND pb.cmpCode = cl.key1
           AND pb.ProdBatchCode = cl.key2
           AND pb.ProdCode = cl.key3
    WHERE cl.objectType=''com.botree.csng.domain.ProductBatch''
           AND db.distrcode IN (:ids)
           AND cl.changeNo between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.ProductBatchEntity',
10,
null,
null,
null,
'Y','Y');

-- productuom
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'productuom',
'M',
'Y',
'SELECT DISTINCT pu.CmpCode,
                pu.ProdCode,
                pu.UOMCode,
                uom.UOMName as UOMDescription,
                UOMConvFactor,
                pu.DefaultUom  as BaseUOM,
                pu.DefaultUom  as DefaultUOM,
                ''N'' AS UploadFlag,
                NOW() AS ModDt
FROM productuom pu
         Inner Join UOMMaster uom
                    ON pu.cmpCode=uom.cmpCode
                        AND pu.uomCode=uom.uomCode
         INNER JOIN Changelog cl
                    ON pu.cmpcode=cl.cmpcode
                        AND pu.cmpCode = cl.key1
                        AND pu.ProdCode = cl.key2
                        AND pu.UOMCode = cl.key3
WHERE cl.objectType=''com.botree.csng.domain.ProductUOM''
  AND cl.changeno between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.ProductUomEntity',
11,
null,
null,
null,'Y','N');

-- retailercategory
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
DistributorWise)
VALUES
('TGBL',
'retailercategory',
'M',
'Y',
'SELECT DISTINCT rcls.CmpCode,
       SUBSTRING_INDEX(rc.ChannelCode,''/'',1)   as ChannelCode,
       SUBSTRING_INDEX(rc.ChannelName,''/'',1)   as ChannelName,
       SUBSTRING_INDEX(rc.ChannelCode,''/'',-1) as SubChannelCode,
       SUBSTRING_INDEX(rc.ChannelName,''/'',-1) as SubChannelName,
       rg.retlrgroupcode as GroupCode,
       rg.retlrGroupName as GroupName,
       rcls.ClassCode,
       rcls.ClassName,
       ''N'' AS UploadFlag,
       NOW() AS ModDt
FROM RetailerClass rcls
INNER JOIN RetailerChannel rc on rcls.cmpCode=rc.cmpcode AND rcls.channelcode=rc.channelcode
INNER JOIN RetailerGroup rg on rcls.cmpCode=rg.cmpcode AND rcls.retlrgroupcode=rg.retlrgroupcode',
'',
'com.botree.interdbentity.model.RetailerCategoryEntity',
12,
null,
null,
null,
'N');

-- taxstructure
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'taxstructure',
'M',
'Y',
'SELECT Distinct A.CmpCode,
       A.TaxStateCode,
       A.ProdCode,
       COALESCE(Sum(cgst), 0.00)          AS CGST,
       COALESCE(Sum(sgst), 0.00)          AS SGST,
       COALESCE(Sum(igst), 0.00)          AS IGST,
       COALESCE(Sum(cess), 0.00)          AS CESS,
       COALESCE(Sum(additionaltax), 0.00) AS AdditionalTax,
       ''N''                                AS UploadFlag,
       NOW()                          AS ModDt
FROM   (SELECT DISTINCT pt.cmpcode,
                        pt.taxstate                         AS TaxStateCode,
                        pt.prodcode,
                        COALESCE(tdcsgt.inputtaxperc, 0.00) AS CGST,
                        COALESCE(tdsgst.inputtaxperc, 0.00) AS SGST,
                        COALESCE(tdigst.inputtaxperc, 0.00) AS IGST,
                        COALESCE(tdcess.inputtaxperc, 0.00) AS CESS,
                        COALESCE(tdaddl.inputtaxperc, 0.00) AS AdditionalTax
        FROM   producttax pt
               INNER JOIN (SELECT Max(ptx.moddt) AS ModDt,
                                  cmpcode,
                                  taxstate,
                                  prodcode,
                                  taxtype
                           FROM   producttax ptx
                           WHERE  effectivefrom <= NOW()
                                  AND taxtype IN( ''G'', ''I'' )
                           GROUP  BY cmpcode,
                                     taxstate,
                                     prodcode,
                                     taxtype) ptx
                       ON ptx.taxstate = pt.taxstate
                          AND ptx.prodcode = pt.prodcode
                          AND ptx.cmpcode = pt.cmpcode
                          AND ptx.taxtype = pt.taxtype
                          AND ptx.moddt = pt.moddt 
			   INNER JOIN GstDistributor gd
					  ON pt.cmpCode = gd.cmpCode
					     AND pt.taxState = gd.gstStateCode  						  
			   INNER JOIN Distributor d
					  ON gd.cmpCode = d.cmpCode
					     AND gd.distrCode = d.distrCode
						 AND d.distrcode IN (:ids)
               LEFT JOIN taxstructuredetails tdcsgt
                      ON pt.cmpcode = tdcsgt.cmpcode
                         AND pt.taxcode = tdcsgt.taxcode
                         AND pt.taxtype = tdcsgt.taxtype
                         AND pt.taxstate = tdcsgt.taxstate
                         AND pt.effectivefrom = tdcsgt.effectivefrom
                         AND tdcsgt.taxname = ''CGST''
               LEFT JOIN taxstructuredetails tdsgst
                      ON pt.cmpcode = tdsgst.cmpcode
                         AND pt.taxcode = tdsgst.taxcode
                         AND pt.taxtype = tdsgst.taxtype
                         AND pt.taxstate = tdsgst.taxstate
                         AND pt.effectivefrom = tdsgst.effectivefrom
                         AND tdsgst.taxname = ''SGST''
               LEFT JOIN taxstructuredetails tdigst
                      ON pt.cmpcode = tdigst.cmpcode
                         AND pt.taxcode = tdigst.taxcode
                         AND pt.taxtype = tdigst.taxtype
                         AND pt.taxstate = tdigst.taxstate
                         AND pt.effectivefrom = tdigst.effectivefrom
                         AND tdigst.taxname = ''IGST''
               LEFT JOIN taxstructuredetails tdcess
                      ON pt.cmpcode = tdcess.cmpcode
                         AND pt.taxcode = tdcess.taxcode
                         AND pt.taxtype = tdcess.taxtype
                         AND pt.taxstate = tdcess.taxstate
                         AND pt.effectivefrom = tdcess.effectivefrom
                         AND tdcess.taxname = ''CESS''
               LEFT JOIN taxstructuredetails tdaddl
                      ON pt.cmpcode = tdaddl.cmpcode
                         AND pt.taxcode = tdaddl.taxcode
                         AND pt.taxtype = tdaddl.taxtype
                         AND pt.taxstate = tdaddl.taxstate
                         AND pt.effectivefrom = tdaddl.effectivefrom
                         AND tdaddl.taxname = ''ADDL''  
        WHERE  pt.taxtype IN ( ''G'', ''I'' )  
        GROUP  BY pt.cmpcode,
                  pt.prodcode,
                  pt.taxstate,
                  tdcsgt.inputtaxperc,
                  tdsgst.inputtaxperc,
                  tdigst.inputtaxperc,
                  tdcess.inputtaxperc,
                  tdaddl.inputtaxperc) A
GROUP  BY A.cmpcode,
          A.prodcode,
          A.taxstatecode',
'',
'com.botree.interdbentity.model.TaxStructureEntity',
13,
null,
null,
null,'N','Y');

-- schemedefinition
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'schemedefinition',
'M',
'Y',
'SELECT distinct sd.CmpCode,
                sd.SchemeCode,
                schemeName as SchemeDescription,
                SchemeBase,
                schemestartdt as SchemeFromDt,
                schemeenddt as SchemeToDt,
                PayOutType,
                AppOnIndividualProd   AS IsSkuLevel,
                isopen   AS IsActive,
                IsCombi,
                ''N''   AS UploadFlag,
                NOW() 	AS ModDt
FROM schemedefinition sd
INNER JOIN schemedistributorbudget sdb
ON sd.cmpcode = sdb.cmpcode
AND sd.schemecode = sdb.schemecode
AND sdb.distrcode IN (:ids)	  
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   
INNER JOIN ChangeLog cl ON sd.cmpCode=cl.cmpCode
AND sd.cmpcode=cl.key1
AND sd.SchemeCode=cl.key2
Where cl.ObjectType=''com.botree.csng.domain.SchemeDefinition''
ANd cl.changeno between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.SchemeDefinitionEntity',
14,
null,
null,
null,'Y','Y');

-- schemedistributorbudget
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'schemedistributorbudget',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode,
                sbb.distrcode,
                sd.schemecode,
                sbb.isunlimited AS IsUnlimited,
                sbb.budget      AS Budget,
                sdb.isactive    AS IsActive,
                sbb.utilizedamt AS UtilizedAmt,
                sbb.budgetos    AS BudgetOs,
                ''N''             AS UploadFlag,
                NOW()       AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemebranchbudget sbb
               ON sd.cmpcode = sbb.cmpcode
                  AND sd.schemecode = sbb.schemecode  
				  AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   				  
       INNER JOIN schemedistributorbudget sdb
               ON sbb.cmpcode = sdb.cmpcode
                  AND sbb.schemecode = sdb.schemecode
                  AND sbb.distrcode = sdb.distrcode
       INNER JOIN changelog cl
               ON sd.cmpcode = cl.cmpcode
                  AND sd.cmpcode = cl.key1
                  AND sd.schemecode = cl.key2
WHERE  cl.objecttype = ''com.botree.csng.domain.SchemeDefinition''
       AND sbb.distrcode IN (:ids)
       AND cl.changeno between :minChangeNo and :maxChangeNo ',
'',
'com.botree.interdbentity.model.SchemeDistributorBudgetEntity',
15,
null,
null,
null,'Y','Y');

-- schemeslab
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'schemeslab',
'M',
'Y',
'SELECT DISTINCT sd.CmpCode,
                sd.SchemeCode,
                ss.SlabNo,
                COALESCE(ss.SlabFrom,0.00) as SlabFrom,
                COALESCE(ss.SlabTo,0.00) as SlabTo,
                COALESCE(ss.Payout,0.00) as Payout,
                ss.SlabRange,
                COALESCE(ss.forevery,0.00) as forevery,
                COALESCE(ss.uom,'''')    AS UomCode,
                ''N''       AS UploadFlag,
                NOW() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemeslab ss
               ON sd.cmpcode = ss.cmpcode
                  AND sd.schemecode = ss.schemecode  
				  AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   				  
       INNER JOIN schemedistributorbudget sdb
               ON sd.cmpcode = sdb.cmpcode
                  AND sd.schemecode = sdb.schemecode
				         AND sdb.distrcode IN (:ids)				  
       INNER JOIN changelog cl
               ON sd.cmpcode = cl.cmpcode
                  AND sd.cmpcode = cl.key1
                  AND sd.schemecode = cl.key2
WHERE  cl.objecttype = ''com.botree.csng.domain.SchemeDefinition''
       AND cl.changeno between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.SchemeSlabEntity',
16,
null,
null,
null,
'Y','Y');


-- schemeslabproduct
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'schemeslabproduct',
'M',
'Y',
'SELECT DISTINCT ss.cmpcode,
                 sdb.distrcode,
                 ss.schemecode,
                 ss.slabno,
                 ss.freeprodcode as prodCode,
                 p.prodname,
                 ss.payout AS Qty,
                 ss.slabrange AS ismandatory,
                 ''N''         AS UploadFlag,
                 NOW()    AS ModDt
 FROM   schemedefinition sd
        INNER JOIN schemedistributorbudget sdb
                ON sd.cmpcode = sdb.cmpcode
                   AND sd.schemecode = sdb.schemecode  
 				  AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   
                  AND sd.PayOutType = ''Free''            
        INNER JOIN schemeslab ss
                ON sdb.cmpcode = ss.cmpcode
                   AND sdb.schemecode = ss.schemecode
                   AND ss.freeprodCode IS NOT NULL  
        INNER JOIN product p
                ON ss.cmpcode = p.cmpcode
                   AND ss.freeprodcode = p.prodcode
        INNER JOIN changelog cl
                ON sd.cmpcode = cl.cmpcode
                   AND sd.cmpcode = cl.key1
                   AND sd.schemecode = cl.key2
 WHERE  cl.objecttype = ''com.botree.csng.domain.SchemeDefinition''
        AND sdb.distrcode IN (:ids)
        AND cl.changeno between :minChangeNo and :maxChangeNo 
UNION   
SELECT DISTINCT ssp.cmpcode,
                 sdb.distrcode,
                 ssp.schemecode,
                 ssp.slabno,
                 ssp.prodcode,
                 p.prodname,
                 ssp.quantity AS Qty,
                 ssp.ismandatory,
                 ''N''         AS UploadFlag,
                 NOW()    AS ModDt
 FROM   schemedefinition sd
        INNER JOIN schemedistributorbudget sdb
                ON sd.cmpcode = sdb.cmpcode
                   AND sd.schemecode = sdb.schemecode  
 				  AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   				  
        INNER JOIN schemeslabproduct ssp
                ON sdb.cmpcode = ssp.cmpcode
                   AND sdb.schemecode = ssp.schemecode
        INNER JOIN product p
                ON ssp.cmpcode = p.cmpcode
                   AND ssp.prodcode = p.prodcode
        INNER JOIN changelog cl
                ON sd.cmpcode = cl.cmpcode
                   AND sd.cmpcode = cl.key1
                   AND sd.schemecode = cl.key2
 WHERE  cl.objecttype = ''com.botree.csng.domain.SchemeDefinition''
        AND sdb.distrcode IN (:ids)
        AND cl.changeno between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.SchemeSlabProductEntity',
17,
null,
null,
null,
'Y',
'Y');

-- schemecustomer
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'schemecustomer',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode,
                sd.schemecode,
                sc.customercode,
                ''N''       AS UploadFlag,
                NOW() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemecustomer sc
               ON sd.cmpcode = sc.cmpcode
                  AND sd.schemecode = sc.schemecode  
				  AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   				  
       INNER JOIN schemedistributorbudget sdb
               ON sd.cmpcode = sdb.cmpcode
                  AND sd.schemecode = sdb.schemecode
				         AND sdb.distrcode IN (:ids)					  
       INNER JOIN changelog cl
               ON sd.cmpcode = cl.cmpcode
                  AND sd.cmpcode = cl.key1
                  AND sd.schemecode = cl.key2
WHERE  cl.objecttype = ''com.botree.csng.domain.SchemeDefinition''
       AND cl.changeno between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.SchemeCustomerEntity',
18,
null,
null,
null,
'Y',
'Y');



-- schemeretailercategory
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'schemeretailercategory',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode,
                sd.schemecode,
                SUBSTRING_INDEX(channelcode,''/'',1)  AS  channelcode,
                SUBSTRING_INDEX(channelcode,''/'',-1)  AS SubChannelCode,
                groupcode,
                classcode,
                ''N''       AS UploadFlag,
                NOW() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemeretailercategory src
               ON sd.cmpcode = src.cmpcode
                  AND sd.schemecode = src.schemecode  
				  AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   
       INNER JOIN schemedistributorbudget sdb
               ON sd.cmpcode = sdb.cmpcode
                  AND sd.schemecode = sdb.schemecode
				         AND sdb.distrcode IN (:ids)					  
       INNER JOIN changelog cl
               ON sd.cmpcode = cl.cmpcode
                  AND sd.cmpcode = cl.key1
                  AND sd.schemecode = cl.key2
WHERE cl.objecttype = ''com.botree.csng.domain.SchemeDefinition''
       AND cl.changeno between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.SchemeRetailerCategoryEntity',
19,
null,
null,
null,
'Y',
'Y');

-- schemeproduct
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'schemeproduct',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode,
                sd.schemecode,
                sp.prodcode,
                p.prodname,
                ''N''       AS UploadFlag,
                NOW() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemeproduct sp
               ON sd.cmpcode = sp.cmpcode
                  AND sd.schemecode = sp.schemecode  
				  AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   				  
       INNER JOIN product P
               ON sp.cmpcode = P.cmpcode
                  AND sp.prodcode = P.prodcode
       INNER JOIN schemedistributorbudget sdb
               ON sd.cmpcode = sdb.cmpcode
                  AND sd.schemecode = sdb.schemecode
				         AND sdb.distrcode IN (:ids)					  
       INNER JOIN changelog cl
               ON sd.cmpcode = cl.cmpcode
                  AND sd.cmpcode = cl.key1
                  AND sd.schemecode = cl.key2
WHERE   cl.objecttype = ''com.botree.csng.domain.SchemeDefinition''
       AND cl.changeno between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.SchemeProductEntity',
20,
null,
null,
null,
'Y',
'Y');

-- schemeproductcategory
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'schemeproductcategory',
'M',
'Y',
'SELECT DISTINCT sd.CmpCode,
                sd.SchemeCode,
                spc.ProdHierLvlCode,
                spc.ProdHierValCode,
                phv.ProdHierValName,
                ''N''     AS UploadFlag,
                NOW() AS ModDt
FROM   schemedefinition sd
           INNER JOIN schemeproductcategory spc
                      ON sd.cmpcode = spc.cmpcode
                          AND sd.schemecode = spc.schemecode  
						  AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   						  
       INNER JOIN schemedistributorbudget sdb
               ON sd.cmpcode = sdb.cmpcode
                  AND sd.schemecode = sdb.schemecode
				         AND sdb.distrcode IN (:ids)							  
           INNER JOIN ProductHierValue phv
                      ON spc.cmpcode = phv.cmpcode
                          AND spc.ProdHierValCode = phv.ProdHierValCode
                          AND spc.ProdHierLvlCode = phv.ProdHierLvlCode
           INNER JOIN changelog cl
                      ON sd.cmpcode = cl.cmpcode
                          AND sd.cmpcode = cl.key1
                          AND sd.schemecode = cl.key2
WHERE  cl.objecttype = ''com.botree.csng.domain.SchemeDefinition''
       AND cl.changeno between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.SchemeProductCategoryEntity',
21,
null,
null,
null,
'Y',
'Y');

-- schemecombiproduct
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'schemecombiproduct',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode,
                sd.schemecode,
                scp.slabno,
                scp.prodcode,
                p.prodname,
                scp.minvalue,
                scp.ismandatory,
                ''N''     AS UploadFlag,
                NOW() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemecombiproduct scp
               ON sd.cmpcode = scp.cmpcode
                  AND sd.schemecode = scp.schemecode   
				  AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')   				  
       INNER JOIN product p
               ON scp.cmpcode = p.cmpcode
                  AND scp.prodcode = p.prodcode
       INNER JOIN schemedistributorbudget sdb
               ON sd.cmpcode = sdb.cmpcode
                  AND sd.schemecode = sdb.schemecode
				         AND sdb.distrcode IN (:ids)					  
       INNER JOIN changelog cl
               ON sd.cmpcode = cl.cmpcode
                  AND sd.cmpcode = cl.key1
                  AND sd.schemecode = cl.key2
WHERE  cl.objecttype = ''com.botree.csng.domain.SchemeDefinition''
       AND cl.changeno between :minChangeNo and :maxChangeNo',
'',
'com.botree.interdbentity.model.SchemeCombiProductEntity',
23,
null,
null,
null,
'Y',
'Y');

-- distributorstock
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
Incremental)
VALUES
('TGBL',
'distributorstock',
'M',
'Y',
'SELECT temp.CmpCode,
       temp.DistrCode,
       GROUP_CONCAT(temp.ProdCode, ''^'', temp.SaleableQty) AS saleableStock,
       ''N'' AS UploadFlag,
       NOW() AS ModDt
FROM (SELECT CmpCode,
             DistrCode,
             ProdCode,
             SUM(SaleableQty) AS SaleableQty
      FROM stockonhand
      WHERE DistrCode IN (:ids)
	AND SaleableQty > 0
      GROUP BY CmpCode, DistrCode, ProdCode) AS temp
GROUP BY temp.CmpCode, temp.DistrCode',
'',
'com.botree.interdbentity.model.DistributorStockEntity',
24,
null,
null,
null,
'Y',
'N');


-- customerstock
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
Incremental)
VALUES
('TGBL',
'customerstock',
'M',
'Y',
'SELECT CmpCode,
        DistrCode,
        CustomerCode,
        GROUP_CONCAT(ProdCode, ''^'', 0, ''^'', PPQ) AS Stock,
        ''N'' AS UploadFlag,
        NOW() AS ModDt
FROM (SELECT CmpCode,
              DistrCode,
              CustomerCode,
              ProdCode,
              REPLACE(GROUP_CONCAT(InvoiceQty), '','', ''~'') AS PPQ
       FROM (SELECT @row_number := CASE
                                       WHEN @str = ID THEN @row_number + 1
                                       ELSE 1
           END AS row_number,
             @str:=ID AS str,
             CmpCode,
             DistrCode,
             CustomerCode,
             ProdCode,
             InvoiceDt,
             InvoiceQty
             FROM
                 (SELECT
                 CONCAT(ih.CmpCode, ih.DistrCode, ih.CustomerCode, id.ProdCode) AS ID,
                 ih.CmpCode,
                 ih.DistrCode,
                 ih.CustomerCode,
                 id.ProdCode,
                 ih.InvoiceDt,
                 SUM(id.InvoiceQty) AS InvoiceQty
                 FROM
                 invoiceheader ih
                 INNER JOIN invoicedetails id
                 ON ih.CmpCode = id.CmpCode
                 AND ih.DistrCode = id.DistrCode
                 AND ih.DistrBrCode = id.DistrBrCode
                 AND ih.InvoiceNumber = id.InvoiceNumber
                 WHERE ih.DistrCode IN (:ids)   
                 AND ih.CmpCode = ''TGBL''     
                 AND ih.DistrBrCode IN (:ids)   
                 GROUP BY ih.CmpCode, ih.DistrCode, ih.CustomerCode, id.ProdCode, ih.InvoiceDt
                 ORDER BY ih.CmpCode, ih.DistrCode, ih.CustomerCode, id.ProdCode, ih.InvoiceDt DESC) src, (SELECT @row_number := 0, @str := '''') AS t
             ORDER BY CmpCode, DistrCode, CustomerCode, ProdCode, row_number DESC) temp
       WHERE temp.row_number <= 3
       GROUP BY CmpCode, DistrCode, CustomerCode, ProdCode) tmp
 GROUP BY CmpCode, DistrCode, CustomerCode',
'',
'com.botree.interdbentity.model.CustomerStockEntity',
25,
null,
null,
null,
'Y',
'N');

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
Incremental)
VALUES
('TGBL',
'distributorsaleshierarchy',
'M',
'Y',
'SELECT CmpCode,
       DistrCode,
       SalesForceCode,
       LobCode,
	   SalesHierPath,
       ''N'' AS UploadFlag,
       NOW() AS ModDt    
FROM distributorsaleshierarchy  dsh 
      WHERE dsh.distrCode IN(:ids)',
'',
'com.botree.interdbentity.model.DistributorSalesHierarchyEntity',
26,
null,
null,
null,
'Y',
'N');

-- companyuser
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
Incremental)
VALUES
('TGBL',
'companyuser',
'M',
'Y',
'SELECT au.CmpCode,
       au.UserCode AS LoginCode,
       UserName,
       (CASE WHEN au.distrCode IS NULL THEN ''C'' ELSE ''D'' END) AS UserType,
       (CASE WHEN au.distrCode IS NULL THEN (SELECT usm.SalesForceCode FROM UserSalesForceMapping usm WHERE usm.CmpCode = au.CmpCode AND usm.UserCode = au.UserCode) ELSE au.distrCode END) AS MappedCode,
       EmailId,
       (CASE WHEN MobileNo IS NULL THEN (SELECT Phone FROM DistributorBranch db WHERE au.CmpCode = db.CmpCode AND au.distrCode = db.distrCode AND LENGTH(Phone)=10) ELSE MobileNo END) AS MobileNo,
       au.IsActive AS UserStatus,
       ''N'' AS UploadFlag,
       NOW() AS ModDt	   
 FROM appuser au 
 INNER JOIN UserSalesForceMapping usfm    
 INNER JOIN DistributorSalesHierarchy dsh    
 ON au.CmpCode = usfm.CmpCode    
 AND au.UserCode = usfm.UserCode  
 AND au.CmpCode = dsh.CmpCode  
 AND dsh.SalesHierPath LIKE CONCAT(''%/'',usfm.SalesForceCode,''/%'') 
 AND dsh.distrCode IN(:ids)
 UNION
 SELECT CmpCode,
       UserCode AS LoginCode,
       UserName,
       ''D'' AS UserType,
       au.distrCode AS MappedCode,
       EmailId,
       (CASE WHEN MobileNo IS NULL THEN (SELECT Phone FROM DistributorBranch db WHERE au.CmpCode = db.CmpCode AND au.distrCode = db.distrCode AND LENGTH(Phone)=10) ELSE MobileNo END) AS MobileNo,
       au.IsActive AS UserStatus,
       ''N'' AS UploadFlag,
       NOW() AS ModDt	   
 FROM appuser au 
 WHERE au.distrCode IN(:ids)',
'',
'com.botree.interdbentity.model.CompanyUserEntity',
27,
null,
null,
null,
'Y',
'N');

-- smsnotifycustactionapi
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
Incremental,
DistributorWise)
VALUES
('TGBL',
'smsnotifycustactionapi',
'M',
'Y',
'SELECT   CmpCode,
 ActionTime,
 ActionCode,
 CONCAT((CASE RIGHT(PPQ, 1) WHEN ''^'' THEN LEFT(PPQ, LENGTH(PPQ) - 1) ELSE PPQ END) , ''~'', ''Visit delivery man by tomorrow'') AS ActionTemplate
 FROM (SELECT   CmpCode,
                ActionTime,
                ActionCode,
	            REPLACE(GROUP_CONCAT(Mobile), '','', ''^'') AS PPQ
       FROM (SELECT @row_number := CASE
                                       WHEN @str = ID THEN @row_number + 1
                                       ELSE 1
             END AS row_number,
             @str:=ID AS str,
			 CmpCode AS CmpCode,
			 NOW() AS ActionTime,
			''SMS_NOTIFICATION_FOR_CUST'' AS ActionCode,             
             Mobile
             FROM
                 (SELECT
                 CONCAT(cr.CmpCode, cr.DistrCode, cr.CustomerCode) AS ID,
                 c.CmpCode,
                 c.Phone AS Mobile
                 FROM
                 routecoverageplan rcp  
                 INNER JOIN customerroute cr
                 ON rcp.CmpCode = cr.CmpCode
                 AND rcp.DistrCode = cr.DistrCode           
                 AND rcp.DistrBrCode = cr.DistrBrCode                            
                 AND rcp.RouteCode = cr.RouteCode                 
                 INNER JOIN customer c
                 ON cr.CmpCode = c.CmpCode
                 AND cr.CustomerCode = c.CustomerCode
                 INNER JOIN retailerssfaretailer rssf 
                 ON c.CmpCode = rssf.CmpCode
                 AND c.CustomerCode = rssf.CustomerCode                 
                 WHERE rcp.DistrCode IN (:distrCode)   
                 AND rcp.CmpCode = :cmpCode     
                 AND rcp.DistrBrCode IN (:distrCode)   
                 AND rcp.CoverageDt = CURDATE() + INTERVAL 1 DAY) src, (SELECT @row_number := 0, @str := '''') AS t
             ORDER BY  Mobile, row_number DESC) temp
       WHERE temp.row_number <= 3) tmp',
'',
'com.botree.interdbentity.model.ActionAPIEntity',
28,
null,
null,
null,
'N','Y');

-- Transaction
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
('TGBL',
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

-- actionapi
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
Intervals)
VALUES
('TGBL',
'orderstatusactionapi',
'T',
'Y',
'SELECT DISTINCT oh.cmpcode AS CmpCode,
                oh.distrcode AS DistrCode,
                MAX(cl.moddt) AS ActionTime,
                ''ORDER_STATUS'' AS ActionCode,
                CONCAT(oh.orderno,''^R^Order Received'') AS ActionTemplate,
                ''N''     AS UploadFlag,
                NOW() AS ModDt
        FROM   offlineorderheader oh					  
        INNER JOIN changelog cl
               ON oh.cmpcode = cl.cmpcode
			      AND oh.distrcode = cl.distrcode
				  AND oh.distrbrcode = cl.distrbrcode
				  AND oh.cmpcode = cl.key1
                  AND oh.distrcode = cl.key2
                  AND oh.distrbrcode = cl.key3
				  AND oh.orderno = cl.key4
				  AND oh.remarks = ''Order generated from Retailer SSFA''
				  AND oh.cmpcode = :cmpCode
				  AND oh.distrcode IN (:distrCode)				  
        WHERE  cl.objecttype = ''com.botree.csng.domain.OfflineOrderHeader''
             AND cl.eventtype = ''C'' 	
             AND cl.changeno between :minChangeNo and :maxChangeNo
        GROUP BY oh.cmpcode,oh.distrcode,oh.orderno	   
        UNION
        SELECT DISTINCT oh.cmpcode AS CmpCode,
                oh.distrcode AS DistrCode,
                MAX(cl.moddt) AS ActionTime,
                ''ORDER_STATUS'' AS ActionCode,
                CONCAT(oh.orderno, ''^S^Stock Allocated'') AS ActionTemplate,
                ''N''     AS UploadFlag,
                NOW() AS ModDt
        FROM   offlineorderheader oh			
	    INNER JOIN invoiceheader ih
			   ON oh.cmpcode = ih.cmpcode
                  AND oh.distrcode = ih.distrcode
                  AND oh.distrbrcode = ih.distrbrcode
                  AND oh.orderno = ih.orderno			
				  AND oh.remarks = ''Order generated from Retailer SSFA''
        INNER JOIN changelog cl
               ON ih.cmpcode = cl.cmpcode
			      AND ih.distrcode = cl.distrcode
				  AND ih.distrbrcode = cl.distrbrcode
				  AND ih.cmpcode = cl.key1
                  AND ih.distrcode = cl.key2
                  AND ih.distrbrcode = cl.key3
				  AND ih.invoicenumber = cl.key4
				  AND ih.cmpCode = :cmpCode
				  AND ih.distrcode IN (:distrCode)				  
        WHERE  cl.objecttype = ''com.botree.csng.domain.InvoiceHeader''
        AND cl.eventtype = ''C''  	
        AND cl.changeno between :minChangeNo and :maxChangeNo
        GROUP BY oh.cmpcode,oh.distrcode,oh.orderno	  	   
        UNION
		SELECT DISTINCT oh.cmpcode AS CmpCode,
                oh.distrcode AS DistrCode,
                MAX(vad.moddt) AS ActionTime,
                ''ORDER_STATUS'' AS ActionCode,
                CONCAT(oh.orderno, ''^V^Vehicle Allocated'') AS ActionTemplate,
                ''N''     AS UploadFlag,
                NOW() AS ModDt
        FROM   offlineorderheader oh			
	    INNER JOIN invoiceheader ih
			   ON oh.cmpcode = ih.cmpcode
                  AND oh.distrcode = ih.distrcode
                  AND oh.distrbrcode = ih.distrbrcode
                  AND oh.orderno = ih.orderno			
				  AND oh.remarks = ''Order generated from Retailer SSFA''  
	    INNER JOIN vehicleallocationdetails vad
			   ON vad.cmpcode = ih.cmpcode
                  AND vad.distrcode = ih.distrcode
                  AND vad.distrbrcode = ih.distrbrcode
                  AND vad.invoicenumber = ih.invoicenumber						  
        INNER JOIN changelog cl
               ON ih.cmpcode = cl.cmpcode
			      AND ih.distrcode = cl.distrcode
				  AND ih.distrbrcode = cl.distrbrcode
				  AND ih.cmpcode = cl.key1
                  AND ih.distrcode = cl.key2
                  AND ih.distrbrcode = cl.key3
				  AND ih.invoicenumber = cl.key4
				  AND ih.invoicestatus IN(''C'',''V'',''D'')
				  AND ih.cmpCode = :cmpCode
				  AND ih.distrcode IN (:distrCode)				  
        WHERE  cl.objecttype = ''com.botree.csng.domain.InvoiceHeader''
        AND cl.eventtype = ''U''  	
        AND cl.changeno between :minChangeNo and :maxChangeNo
        GROUP BY oh.cmpcode,oh.distrcode,oh.orderno	
		UNION   
        SELECT DISTINCT oh.cmpcode AS CmpCode,
                oh.distrcode AS DistrCode,
                MAX(ih.moddt) AS ActionTime,
                ''ORDER_STATUS'' AS ActionCode,
                CONCAT(oh.orderno, ''^D^Delivered'') AS ActionTemplate,
                ''N''     AS UploadFlag,
                NOW() AS ModDt
        FROM   offlineorderheader oh			
	    INNER JOIN invoiceheader ih
			   ON oh.cmpcode = ih.cmpcode
                  AND oh.distrcode = ih.distrcode
                  AND oh.distrbrcode = ih.distrbrcode
                  AND oh.orderno = ih.orderno			
				  AND oh.remarks = ''Order generated from Retailer SSFA''
        INNER JOIN changelog cl
               ON ih.cmpcode = cl.cmpcode
			      AND ih.distrcode = cl.distrcode
				  AND ih.distrbrcode = cl.distrbrcode
				  AND ih.cmpcode = cl.key1
                  AND ih.distrcode = cl.key2
                  AND ih.distrbrcode = cl.key3
				  AND ih.invoicenumber = cl.key4
				  AND ih.invoicestatus = ''D''
				  AND ih.cmpCode = :cmpCode
				  AND ih.distrcode IN (:distrCode)				  
        WHERE  cl.objecttype = ''com.botree.csng.domain.InvoiceHeader''
        AND cl.eventtype = ''U''  	
        AND cl.changeno between :minChangeNo and :maxChangeNo
        GROUP BY oh.cmpcode,oh.distrcode,oh.orderno
        UNION
        SELECT DISTINCT oh.cmpcode AS CmpCode,
                oh.distrcode AS DistrCode,
                MAX(oh.moddt) AS ActionTime,
                ''ORDER_STATUS'' AS ActionCode,
                CONCAT(oh.orderno, ''^C^Order Cancelled'') AS ActionTemplate,
                ''N''     AS UploadFlag,
                NOW() AS ModDt
        FROM   offlineorderheader oh					  
        INNER JOIN changelog cl
               ON oh.cmpcode = cl.cmpcode
			      AND oh.distrcode = cl.distrcode
				  AND oh.distrbrcode = cl.distrbrcode
				  AND oh.cmpcode = cl.key1
                  AND oh.distrcode = cl.key2
                  AND oh.distrbrcode = cl.key3
				  AND oh.orderno = cl.key4
				  AND oh.status = ''C''
				  AND oh.remarks = ''Order generated from Retailer SSFA''
				  AND oh.cmpcode = :cmpCode
				  AND oh.distrcode IN (:distrCode)				  
        WHERE  cl.objecttype = ''com.botree.csng.domain.OfflineOrderHeader''
	         AND cl.eventtype = ''U'' 	
             AND cl.changeno between :minChangeNo and :maxChangeNo    
        GROUP BY oh.cmpcode,oh.distrcode,oh.orderno
        UNION
        SELECT DISTINCT cl.cmpcode AS CmpCode,
                cl.distrcode AS DistrCode,
                MAX(cl.moddt) AS ActionTime,
                ''ORDER_STATUS'' AS ActionCode,
                CONCAT(cl.Key4, ''^C^Order Cancelled'') AS ActionTemplate,
                ''N''     AS UploadFlag,
                NOW() AS ModDt
        FROM   changelog cl  			  
        WHERE  cl.objecttype = ''com.botree.csng.domain.OfflineOrderHeader''
	         AND cl.eventtype = ''D'' 
		     AND cl.cmpcode = :cmpCode
			 AND cl.distrcode IN (:distrCode)
             AND cl.distrbrcode IN (:distrCode)			 		     
		     AND cl.Key1 = :cmpCode
			 AND cl.Key2 IN (:distrCode)
             AND cl.Key3 IN (:distrCode)			 				     	
             AND cl.changeno between :minChangeNo and :maxChangeNo    
        GROUP BY cl.cmpcode,cl.distrcode,cl.Key4',
'',
'com.botree.interdbentity.model.ActionAPIEntity',
2,
null,
null,
null,
'Y',
'Y',
'N');

UPDATE externalprocessdetail SET Intervals = '1HRS' WHERE ProcessType = 'M';
UPDATE externalprocessdetail SET Intervals = '24HRS' WHERE ProcessType = 'M' AND processsequence = '28';
