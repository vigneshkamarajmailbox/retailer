x`IF EXISTS(SELECT 1 FROM SYSOBJECTS WHERE NAME='ExternalProcessDetail' AND XTYPE='U')
BEGIN
       DROP TABLE ExternalProcessDetail
END;

IF NOT EXISTS(SELECT 1 FROM SYSOBJECTS WHERE NAME='ExternalProcessDetail' AND XTYPE='U')
BEGIN
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
       )
   END;

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
('Perfetti',
'company',
'M',
'Y',
'SELECT c.CmpCode,
       CmpName,
       CmpAddr1,
       CmpAddr2,
       CmpAddr3,
       City,
       GSTStateCode as State,
       Country,
       PostalCode,
       ''N'' AS UploadFlag,
       getDate() AS ModDt
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
('Perfetti',
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
                c.channelcode,
                c.channelcode                   AS SubChannelCode,
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
                Getdate()                       AS ModDt
FROM   customer c
       INNER JOIN changelog cl
               ON c.cmpcode = cl.cmpcode
                  AND c.cmpcode = cl.key1
                  AND c.customercode = cl.key2
       INNER JOIN customershipaddress csa
               ON c.cmpcode = csa.cmpcode
                  AND c.customercode = csa.customercode
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
       AND c.distrcode IN (:ids)
       AND Len(c.phone) = 10
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
'Y','Y');
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
('Perfetti',
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
                Getdate() AS ModDt
FROM   customershipaddress csa
       INNER JOIN customer c
               ON csa.cmpcode = c.cmpcode
                  AND csa.customercode = c.customercode
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
       AND Len(c.phone) = 10
       AND r.routetype = ''S''
       AND r.isactive = ''Y''
       AND s.isactive = ''Y''
       AND c.distrcode IN ( :ids )
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
null,'Y','Y');

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
('Perfetti',
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
       getdate() AS ModDt
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
('Perfetti',
'distributorlobmapping',
'M',
'Y',
'SELECT distinct dsh.CmpCode,
       dsh.DistrCode,
       LOBCode,
       ''N''   	AS UploadFlag,
       getdate() 	AS ModDt
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
('Perfetti',
'lobmaster',
'M',
'Y',
'SELECT CmpCode,
       LOBCode,
       LOBName,
       ''N'' AS UploadFlag,
       getdate() AS ModDt
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
('Perfetti',
'producthierlevel',
'M',
'Y',
'SELECT CmpCode,
       ProdHierLvlCode,
       ProdHierLvlName,
       ''N'' AS UploadFlag,
       getDate() AS ModDt
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
('Perfetti',
'producthiervalue',
'M',
'Y',
'SELECT Distinct phv.CmpCode,
       ProdHierLvlCode,
       ProdHierValCode,
       ProdHierValName,
       ParentCode,
       ''N'' AS UploadFlag,
       getDate() AS ModDt
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
('Perfetti',
'product',
'M',
'Y',
'SELECT distinct p.CmpCode,
                p.ProdCode,
                p.ProdName,
                p.ProdShortName,
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
                       ''/'',div.prodHierValName,
                       ''/'',categ.ProdHierValName,
                       ''/'',brand.ProdHierValName,
                       ''/'',SKU.ProdhierValName,
                       ''/'') as ProductHierPathName,
                ''N''     AS UploadFlag,
                getdate() AS ModDt
         FROM Product p
         INNER JOIN GstProduct gp
            ON p.CmpCode = gp.CmpCode
            AND p.prodCode = gp.prodCode
         INNER JOIN Changelog cl
            ON p.cmpcode=cl.cmpcode
            AND p.cmpCode = cl.key1
            AND p.prodCode = cl.key2
         INNER JOIN LobMaster lm
            ON p.cmpCode=lm.cmpCode
         INNER JOIN ProductHierValue sku
              ON p.cmpCode=sku.cmpCode
              AND p.prodhiervalcode=sku.prodhiervalcode
         INNER JOIN ProductHierValue brand
             ON sku.cmpCode=brand.cmpcode
             AND sku.parentCode=brand.prodhiervalcode
         INNER JOIN ProductHierValue categ
             ON brand.cmpCode=categ.cmpcode
             AND  brand.parentCode=categ.prodhiervalcode
         INNER JOIN ProductHierValue div
             ON categ.cmpCode=div.cmpcode
             AND  categ.parentCode=div.prodhiervalcode
         INNER JOIN ProductHierValue cmp
             ON div.cmpCode=cmp.cmpcode
             AND  div.parentCode=cmp.prodhiervalcode
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
('Perfetti',
'productbatch',
'M',
'Y',
'SELECT distinct pp.CmpCode,
       dp.DistrCode AS BatchLevel,
       pp.ProdCode,
       pp.ProdBatchCode,
       pp.MRP,
       pp.sellPrice as SellRate,
       pt.ptrWithTax as SellRateWithTax,
       pb.ManfDt as ManfDate,
       pb.expiryDt as ExpiryDate,
       (CASE WHEN pp.ModDt = (SELECT Max(ModDt)
                FROM   productpricing ppm
                WHERE  ppm.Cmpcode = pp.CmpCode
                AND ppm.ProdCode = pp.ProdCode
                AND ppm.GeoCode = pp.GeoCode
                AND ppm.EffectiveFromDate <= GETDATE()
                AND ppm.FromLevel = pp.FromLevel
                AND ppm.ToLevel = pp.ToLevel) THEN ''Y'' ELSE ''N'' END)  as LatestBatch,
       ''N''     AS GeoLevelBatch,
       ''N''     AS UploadFlag,
       getdate()     AS ModDt
FROM DistributorProduct dp
        INNER JOIN distributorbranch db
                    ON db.CmpCode = dp.CmpCode
                        AND db.DistrCode = dp.DistrCode
        INNER JOIN supplychainmaster scm
                    ON scm.CmpCode = db.CmpCode
                        AND scm.MemberCode = db.DistrCode
        INNER JOIN productpricing pp
                ON dp.CmpCode = pp.CmpCode
                    AND dp.ProdCode = pp.ProdCode
                    AND db.cmpCode=pp.cmpCode
                    AND db.distrbrstate = pp.geocode
                    AND pp.FromLevel = scm.LevelCode
                    AND pp.ToLevel = CASE
                                         WHEN scm.LevelCode = 1 THEN 5
                                         WHEN scm.LevelCode = 6 THEN 9
                                         WHEN scm.LevelCode = 7 THEN 9
                                         WHEN scm.LevelCode = 15 THEN 9
                                         WHEN scm.LevelCode = 4 THEN 9
                        END
                    AND pp.EffectiveFromDate <= GETDATE()
           INNER JOIN productbatch pb
                   ON dp.CmpCode = pb.CmpCode
                       AND pp.ProdCode = pb.ProdCode
                       AND pp.ProdBatchCode = pb.ProdBatchCode
           INNER JOIN ProductPriceWithTax pt
               ON pp.cmpCode=pt.cmpCode
                AND pp.prodCode=pt.prodCode
    INNER JOIN GeoHierValue ghv
        on pp.cmpCode=ghv.cmpCode
        AND pp.geoCode = ghv.geoCode
    INNER JOIN GSTStateMaster gsm
        on ghv.cmpCode=gsm.cmpCode
        AND ghv.geoName=gsm.gstStateName
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
('Perfetti',
'productuom',
'M',
'Y',
'SELECT DISTINCT pu.CmpCode,
                pu.ProdCode,
                pu.UOMCode,
                uom.UOMName as UOMDescription,
                UOMConvFactor,
                (CASE WHEN (pu.UOMCode=''CAR'' OR pu.UOMCode=''CS'') THEN ''N'' ELSE ''Y'' END)  as BaseUOM,
                (CASE WHEN (pu.UOMCode=''CAR'' OR pu.UOMCode=''CS'') THEN ''N'' ELSE ''Y'' END)  as DefaultUOM,
                ''N'' AS UploadFlag,
                getdate() AS ModDt
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
('Perfetti',
'retailercategory',
'M',
'Y',
'SELECT rcls.CmpCode,
       rc.ChannelCode,
       rc.ChannelName,
       rc.ChannelCode as SubChannelCode,
       rc.ChannelName as SubChannelName,
       rg.retlrgroupcode as GroupCode,
       rg.retlrGroupName as GroupName,
       rcls.ClassCode,
       rcls.ClassName,
       ''N'' AS UploadFlag,
       getdate() AS ModDt
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
('Perfetti',
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
       Getdate()                          AS ModDt
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
                           WHERE  effectivefrom <= Getdate()
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
               INNER JOIN changelog cl
                       ON pt.cmpcode = cl.cmpcode
                          AND pt.cmpcode = cl.key1
                          AND pt.prodcode = cl.key3
                          AND pt.taxstate = cl.key4
                          AND pt.taxtype = cl.key5
        WHERE  pt.taxtype IN ( ''G'', ''I'' )
               AND Cl.changeno between :minChangeNo and :maxChangeNo
               AND cl.objecttype = ''com.botree.csng.domain.ProductTax''
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
null,'Y','N');

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
('Perfetti',
'schemedefinition',
'M',
'Y',
'SELECT distinct sd.CmpCode,
                SchemeCode,
                schemeName as SchemeDescription,
                SchemeBase,
                schemestartdt as SchemeFromDt,
                schemeenddt as SchemeToDt,
                PayOutType,
                (Case WHEN SchemeAtLevel=1000000 then ''Y'' else ''N'' end)   AS IsSkuLevel,
                isopen   AS IsActive,
                IsCombi,
                ''N''   AS UploadFlag,
                getdate() 	AS ModDt
FROM schemedefinition sd
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
null,'Y','N');

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
('Perfetti',
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
                Getdate()       AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemebranchbudget sbb
               ON sd.cmpcode = sbb.cmpcode
                  AND sd.schemecode = sbb.schemecode
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
('Perfetti',
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
                Getdate() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemeslab ss
               ON sd.cmpcode = ss.cmpcode
                  AND sd.schemecode = ss.schemecode
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
'Y','N');


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
('Perfetti',
'schemeslabproduct',
'M',
'Y',
'
SELECT DISTINCT ssp.cmpcode,
                sdb.distrcode,
                ssp.schemecode,
                ssp.slabno,
                ssp.prodcode,
                p.prodname,
                ssp.quantity AS Qty,
                ssp.ismandatory,
                ''N''         AS UploadFlag,
                Getdate()    AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemedistributorbudget sdb
               ON sd.cmpcode = sdb.cmpcode
                  AND sd.schemecode = sdb.schemecode
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
('Perfetti',
'schemecustomer',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode,
                sd.schemecode,
                sc.customercode,
                ''N''       AS UploadFlag,
                Getdate() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemecustomer sc
               ON sd.cmpcode = sc.cmpcode
                  AND sd.schemecode = sc.schemecode
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
'N');



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
('Perfetti',
'schemeretailercategory',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode,
                sd.schemecode,
                channelcode,
                channelcode  AS SubChannelCode,
                groupcode,
                classcode,
                ''N''       AS UploadFlag,
                Getdate() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemeretailercategory src
               ON sd.cmpcode = src.cmpcode
                  AND sd.schemecode = src.schemecode
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
'N');

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
('Perfetti',
'schemeproduct',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode,
                sd.schemecode,
                sp.prodcode,
                p.prodname,
                ''N''       AS UploadFlag,
                Getdate() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemeproduct sp
               ON sd.cmpcode = sp.cmpcode
                  AND sd.schemecode = sp.schemecode
       INNER JOIN product P
               ON sp.cmpcode = P.cmpcode
                  AND sp.prodcode = P.prodcode
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
'N');

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
('Perfetti',
'schemeproductcategory',
'M',
'Y',
'SELECT DISTINCT sd.CmpCode,
                sd.SchemeCode,
                spc.ProdHierLvlCode,
                spc.ProdHierValCode,
                phv.ProdHierValName,
                ''N''     AS UploadFlag,
                Getdate() AS ModDt
FROM   schemedefinition sd
           INNER JOIN schemeproductcategory spc
                      ON sd.cmpcode = spc.cmpcode
                          AND sd.schemecode = spc.schemecode
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
'N');

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
('Perfetti',
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
                Getdate() AS ModDt
FROM   schemedefinition sd
       INNER JOIN schemecombiproduct scp
               ON sd.cmpcode = scp.cmpcode
                  AND sd.schemecode = scp.schemecode
       INNER JOIN product p
               ON scp.cmpcode = p.cmpcode
                  AND scp.prodcode = p.prodcode
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
'N');

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
('Perfetti',
'distributorstock',
'M',
'Y',
'SELECT temp.cmpcode  as CmpCode,
       temp.distrcode as DistrCode,
       SaleableStock=Stuff((SELECT DISTINCT '', ''
                                             + Cast(soh.prodcode + ''^'' +
                                             Cast(Sum(soh.saleableqty) AS
                                                                  VARCHAR(10))
                                             AS
                                             VARCHAR
                                             (max))
                             FROM   stockonhand soh
                             WHERE  soh.distrcode = temp.distrcode
                                    AND soh.cmpcode = temp.cmpcode
                                    AND soh.saleableqty > 0
                             GROUP  BY soh.prodcode
                             FOR xml path('''')), 1, 1, ''''),
       ''N''       AS UploadFlag,
       Getdate() AS ModDt
FROM   stockonhand temp(Nolock)
WHERE  distrcode IN (:ids)
GROUP  BY temp.cmpcode,
          temp.distrcode',
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
('Perfetti',
'customerstock',
'M',
'Y',
'SELECT CmpCode,DistrCode,CustomerCode,CustomerProduct as Stock,
''N'' AS UploadFlag,
Getdate() AS ModDt FROM DistributorCustomerStock
 Where Distrcode IN (:ids)',
'',
'com.botree.interdbentity.model.CustomerStockEntity',
25,
null,
null,
null,
'Y',
'N');


-- Order
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
('Perfetti',
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
