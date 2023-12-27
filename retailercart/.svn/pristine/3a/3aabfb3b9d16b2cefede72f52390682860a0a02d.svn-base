DROP TABLE externalprocessdetail;

CREATE TABLE externalprocessdetail(
CmpCode varchar(10) NOT NULL,
InterDBProcess varchar(50) NOT NULL,
ProcessType char(1) NOT NULL,
ProcessEnable char(1) NOT NULL,
InterDBQuery LONGTEXT NOT NULL,
InterDBUpdateQuery LONGTEXT DEFAULT NULL,
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

-- Company

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
('IN14',
'company',
'M',
'Y',
'SELECT c.cmpcode, 
       c.cmpname, 
       c.cmpaddr1, 
       c.cmpaddr2, 
       c.cmpaddr3, 
       ( 
              SELECT a.geoname 
              FROM   geohiervalue a 
              WHERE  c.cmpcode = a.cmpcode 
              AND    c.city = a.geocode) AS city, 
       ( 
              SELECT a.geoname 
              FROM   geohiervalue a 
              WHERE  c.cmpcode = a.cmpcode 
              AND    c.cmpstate = a.geocode) AS state, 
       ( 
              SELECT a.geoname 
              FROM   geohiervalue a 
              WHERE  c.cmpcode = a.cmpcode 
              AND    c.country = a.geocode) AS country, 
       postalcode, 
       ''N'' as uploadflag, 
       now() AS moddt 
FROM   company c;',
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

-- Customer

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
('IN14',
'customer',
'M',
'Y',
'SELECT DISTINCT c.cmpcode, 
                c.distrcode, 
                c.customercode, 
                c.customername, 
                c.phone         AS Mobileno, 
                
                 COALESCE( ( 
                           SELECT cs.postalCode 
                           FROM   customershipaddress cs 
                           WHERE  c.cmpcode = cs.cmpcode 
                           AND    c.customercode = cs.customercode 
                           AND    cs.isdefault = ''Y'' Limit 1), '''')         AS pincode, 
                c.phone         AS phoneno, 
                c.ContactPerson AS contactperson, 
                c.emailid       AS emailid, 
                c.isactive AS retailerstatus, 
                ''''          AS fssaino, 
                c.creditbills AS creditbills, 
                c.creditdays, 
                c.creditlimit, 
                0.0 AS cashdiscperc, 
                c.channelcode, 
                c.channelcode             AS subchannelcode, 
                c.retlrgroupcode AS groupcode, 
                c.classcode, 
                c.storetype AS storetype, 
                ''''        AS parentcustomercode, 
                c.latitude, 
                c.longitude, 
                gs.RetailerType AS customertype, 
                COALESCE( 
                           ( 
                           SELECT cs.gsttinno 
                           FROM   customershipaddress cs 
                           WHERE  c.cmpcode = cs.cmpcode 
                           AND    c.customercode = cs.customercode 
                           AND    cs.isdefault = ''Y'' Limit 1), '''') AS gsttinno, 
                gs.panno                                       AS panno,
		COALESCE((SELECT oa.attrInputValues from OtherAttributes oa     
    		   		    						WHERE  oa.cmpCode = c.cmpCode    
    		   		    						AND oa.refNo = c.customercode    
    		   		    						AND oa.moduleNo = ''1200''    
    		   		    						AND oa.screenNo = ''5''    
    		   		    						AND oa.attributeCode = ''District Name''),'' '') AS District, 
		COALESCE( os.OSAmount,0)  					AS OutstandingAmt,
                ''N''                                          AS uploadflag, 
                Now()                                          AS moddt 
FROM            customer c
INNER JOIN      gstcustomermaster gs 
ON              c.cmpCode = gs.cmpCode 
AND             c.customerCode = gs.customerCode 
left outer join (
select h.CmpCode,h.DistrCode,h.DistrBrCode, h.CustomerCode,sum(ic.BalanceOS) as OSAmount from invoiceheader h inner join invoicecollection ic on h.CmpCode =ic.CmpCode and h.DistrCode =ic.DistrCode and h.DistrBrCode =ic.DistrBrCode and h.InvoiceNumber =ic.InvoiceNumber where h.DistrBrCode IN ( :ids ) and h.DistrCode IN ( :ids )
and h.InvoiceStatus=''D'' group by h.CmpCode,h.DistrCode,h.DistrBrCode, h.CustomerCode) os on os.CmpCode=c.cmpCode
and  os.DistrCode =c.distrcode and os.DistrBrCode =c.distrcode and os.CustomerCode = c.customercode
WHERE           c.distrcode IN ( :ids )
          AND c.cmpCode = ''IN14''
AND Length(c.phone) = 10
           AND c.phone NOT IN (SELECT cust.phone
                         FROM   customer Cust
                         WHERE  Cust.cmpcode = ''IN14''
                                AND cust.distrcode IN (:ids)
                         GROUP  BY cust.phone
                         HAVING Count(DISTINCT cust.customercode) > 1);',
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
('IN14',
'customershipaddress',
'M',
'Y',
'SELECT DISTINCT c.cmpcode, 
                c.distrcode, 
                c.customercode, 
                cs.customerShipCode, 
                cs.customerShipAddr1, 
                cs.customerShipAddr2, 
                cs.customerShipAddr3, 
                (SELECT a.geoname 
                 FROM   geohiervalue a 
                 WHERE  c.cmpcode = a.cmpcode 
                        AND cs.city = a.geocode) AS city, 
                cs.GSTStateCode                  AS gstStateCode, 
                c.phone                          AS phoneNo, 
                cs.IsDEFAULT AS defaultShipAddr, 
                ''N''                            AS uploadflag, 
                Now()                            AS moddt 
FROM   customer c
       INNER JOIN customershipaddress cs 
               ON c.cmpCode = cs.cmpCode 
                  AND c.customerCode = cs.customerCode 
       INNER JOIN Changelog cl 
               ON cs.cmpCode = cl.cmpCode 
                  AND cs.cmpCode = cl.key1 
                  AND cs.customerCode = cl.key2 
                  AND cs.customershipcode = cl.key3 
WHERE  cl.Objecttype = ''com.botree.csng.domain.CustomerShipAddress'' 
       AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo 
       AND c.distrcode IN (:ids);',
'',
'com.botree.interdbentity.model.CustomerShipAddressEntity',
3,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'retailercategory',
'M',
'Y',
'SELECT DISTINCT rc.cmpcode, 
                rch.ChannelCode   AS ''channelCode'', 
                rch.ChannelName   AS ''channelName'', 
                rch.ChannelCode   AS ''subChannelCode'', 
                rch.ChannelName   AS ''subChannelName'', 
                rg.RetlrGroupCode AS ''groupCode'', 
                rg.RetlrGroupName AS ''groupName'', 
                rc.ClassCode      AS ''classCode'', 
                rc.ClassName      AS ''className'', 
                ''N''               AS uploadflag, 
                Now()             AS moddt 
FROM   retailerclass rc 
       INNER JOIN retailergroup rg 
               ON rc.cmpCode = rg.cmpCode 
                  AND rc.RetlrGroupCode = rg.RetlrGroupCode 
       INNER JOIN retailerchannel rch 
               ON rch.cmpCode = rg.cmpCode 
                  AND rch.ChannelCode = rg.ChannelCode 
                  AND rch.ChannelCode = rc.ChannelCode 
       INNER JOIN Changelog cl 
               ON rc.cmpCode = cl.cmpCode 
                  AND rc.ChannelCode = cl.key1 
                  AND rc.ClassCode = cl.key2 
                  AND rc.cmpcode = cl.key3 
                  AND rc.RetlrGroupCode = cl.key4 
WHERE  cl.Objecttype = ''com.botree.csng.domain.RetailerClass'' 
       AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo; ',
'',
'com.botree.interdbentity.model.RetailerCategoryEntity',
4,
null,
null,
null,
'N',
'Y',
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
('IN14',
'distributor',
'M',
'Y',
'SELECT DISTINCT d.cmpcode, 
                d.distrcode      AS ''distrCode'', 
                d.distrname      AS ''distrName'', 
                db.distrbraddr1  AS ''distrAddr1'', 
                db.distrbraddr2  AS ''distrAddr2'', 
                db.distrbraddr3  AS ''distrAddr3'', 
                db.postalcode    AS ''pinCode'', 
                db.phone         AS ''phoneNo'', 
                db.phone         AS ''mobileNo'', 
                db.contactperson AS ''contactPerson'', 
                db.emailid       AS ''emailID'', 
                gd.gststatecode  AS ''gstStateCode'', 
                concat(case when dw.Sunday =''Y'' then ''Sunday'' else '''' end,case when (dw.Sunday=''Y'' and dw.Monday =''Y'') then '',Monday''  when (dw.Monday =''Y'') then ''Monday'' else '''' end,
                case when ((dw.Sunday=''Y'' or dw.Monday=''Y'') and dw.Tuesday =''Y'') then '',Tuesday'' when dw.Tuesday =''Y'' then ''Tuesday''  else '''' end,case when ((dw.Sunday=''Y'' or dw.Monday=''Y'' or dw.Tuesday=''Y'') and dw.Wednesday =''Y'') then '',Wednesday'' when dw.Wednesday =''Y'' then ''Wednesday''  else '''' end,case when ((dw.Sunday=''Y'' or dw.Monday=''Y'' or dw.Tuesday=''Y'' or dw.Wednesday =''Y'') and dw.Thursday =''Y'') then '',Thursday'' when dw.Thursday =''Y'' then ''Thursday'' else '''' end
                ,case   when ((dw.Sunday=''Y'' or dw.Monday=''Y'' or dw.Tuesday=''Y'' or dw.Wednesday =''Y'' or dw.Thursday =''Y'') and dw.Friday =''Y'') then '',Friday''when dw.Friday =''Y'' then ''Friday''  else '''' end,case when ((dw.Sunday=''Y'' or dw.Monday=''Y'' or dw.Tuesday=''Y'' or dw.Wednesday =''Y'' or dw.Thursday =''Y'' or  dw.Friday =''Y'') and dw.Saturday =''Y'') then '',Saturday'' when dw.Saturday =''Y'' then ''Saturday''  else '''' end)                AS ''dayOff'', 
                d.isactive       AS ''distrStatus'', 
                ''N''              AS loadStockProd, 
                ''N''              AS uploadflag, 
                Now()            AS moddt 
FROM   distributor d 
       INNER JOIN DistributorBranch db 
               ON d.cmpCode = db.cmpCode 
                  AND d.distrCode = db.distrCode 
       INNER JOIN GstDistributor gd 
               ON d.cmpCode = gd.cmpCode 
                  AND d.distrCode = gd.distrCode 
	left outer join distributorweeklyoff dw on
        	dw.CmpCode = d.CmpCode and
        	dw.DistrCode = d.DistrCode
       INNER JOIN Changelog cl 
               ON d.cmpCode = cl.cmpCode 
                  AND d.cmpCode = cl.key1 
                  AND d.distrCode = cl.key2 
				  AND d.distrCode = cl.distrCode
WHERE  cl.Objecttype = ''com.botree.csng.domain.Distributor'' 
       AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo 
       AND d.distrCode in (:ids);',
'',
'com.botree.interdbentity.model.DistributorEntity',
5,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'distributorlobmapping',
'M',
'Y',
'SELECT DISTINCT d.cmpcode, 
                d.distrCode       AS ''distrCode'', 
                l.ProdHierValCode AS ''lobCode'', 
                ''N''               AS uploadflag, 
                Now()             AS moddt 
FROM   distributorsaleshierarchy d 
       INNER JOIN lobmaster l 
               ON d.cmpCode = l.cmpCode 
                  AND l.LOBCode = ''Default'' 
       INNER JOIN Changelog cl 
               ON d.cmpCode = cl.cmpCode 
                  AND d.cmpCode = cl.key1 
                  AND d.distrCode = cl.key2 
                  AND d.LOBCode = cl.key3 
                  AND d.SalesForceCode = cl.key4 
WHERE  cl.Objecttype = ''com.botree.csng.domain.DistributorSalesHierarchy'' 
       AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo 
       AND d.distrCode IN ( :ids ) 
UNION 
SELECT DISTINCT d.cmpcode, 
                d.distrCode       AS ''distrCode'', 
                l.ProdHierValCode AS ''lobCode'', 
                ''N''               AS uploadflag, 
                Now()             AS moddt 
FROM   distributorsaleshierarchy d 
       INNER JOIN lobmaster l 
               ON d.cmpCode = l.cmpCode 
                  AND d.LOBCode = l.LOBCode 
       INNER JOIN Changelog cl 
               ON d.cmpCode = cl.cmpCode 
                  AND d.cmpCode = cl.key1 
                  AND d.distrCode = cl.key2 
                  AND d.LOBCode = cl.key3 
                  AND d.SalesForceCode = cl.key4 
WHERE  cl.Objecttype = ''com.botree.csng.domain.DistributorSalesHierarchy'' 
       AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo 
       AND d.distrCode IN ( :ids ); ',
'',
'com.botree.interdbentity.model.DistributorLOBMappingEntity',
6,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'lobmaster',
'M',
'Y',
'SELECT DISTINCT d.cmpcode, 
                d.ProdHierValCode   AS ''lobCode'', 
                phv.ProdHierValName AS ''lobName'', 
                ''N''                 AS uploadflag, 
                Now()               AS moddt 
FROM   lobmaster d 
       INNER JOIN producthiervalue phv 
               ON d.cmpCode = phv.cmpCode 
                  AND d.ProdHierLvlCode = phv.ProdHierLvlCode 
                  AND d.ProdHierValCode = phv.ProdHierValCode 
       INNER JOIN Changelog cl 
               ON d.cmpCode = cl.cmpCode 
                  AND d.cmpCode = cl.key1 
                  AND d.LOBCode = cl.key2 
                  AND d.ProdHierLvlCode = cl.key3 
                  AND d.ProdHierValCode = cl.key4 
WHERE  cl.Objecttype = ''com.botree.csng.domain.LOBMaster'' 
       AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.LOBMasterEntity',
7,
null,
null,
null,
'N',
'Y',
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
('IN14',
'producthierlevel',
'M',
'Y',
'SELECT DISTINCT d.cmpcode, 
                d.ProdHierLvlCode AS ''prodHierLvlCode'', 
                d.ProdHierLvlName AS ''prodHierLvlName'', 
                ''N''               AS uploadflag, 
                Now()             AS moddt 
FROM   producthierlevel d 
       INNER JOIN Changelog cl 
               ON d.cmpCode = cl.cmpCode 
                  AND d.cmpCode = cl.key1 
                  AND d.ProdHierLvlCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.ProductHierLevel'' 
       AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.ProductHierLevelEntity',
12,
null,
null,
null,
'N',
'Y',
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
('IN14',
'producthiervalue',
'M',
'Y',
'SELECT DISTINCT d.cmpcode, 
                d.ProdHierLvlCode AS ''prodHierLvlCode'', 
                d.ProdHierValCode AS ''prodHierValCode'', 
                d.ProdHierValName AS ''prodHierValName'', 
                d.ParentCode      AS ''parentCode'', 
                ''N''               AS uploadflag, 
                Now()             AS moddt 
FROM   producthiervalue d 
       INNER JOIN Changelog cl 
               ON d.cmpCode = cl.cmpCode 
                  AND d.cmpCode = cl.key1 
                  AND d.ProdHierLvlCode = cl.key2 
                  AND d.ProdHierValCode = cl.key3 
WHERE  cl.Objecttype = ''com.botree.csng.domain.ProductHierValue'' 
       AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.ProductHierValueEntity',
13,
null,
null,
null,
'N',
'Y',
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
('IN14',
'product',
'M',
'Y',
'SELECT DISTINCT p.cmpcode, 
                p.prodCode AS ''prodCode'', 
                Replace (p.prodName, "/", "-") AS ''prodName'', 
                CASE 
                  WHEN p.prodShortName IS NULL 
                        OR p.prodShortName = '''' THEN 
                  Replace (p.prodName, "/", "-") 
                  ELSE Replace (p.prodShortName, "/", "-") 
                END AS ''prodShortName'', 
                p.IsActive AS ''prodStatus'', 
                p.ProdType AS ''prodType'', 
                p.ShelfLife AS ''prodShelfLife'', 
                p.ProdNetWgt AS ''prodNetWgt'', 
                p.ProdWgtType AS ''prodWgtType'', 
                p6.ProdHierValCode AS ''lobCode'', 
                phv.ProdHierValName AS ''lobName'', 
                gp.HSNCode AS ''hsnCode'', 
                gp.HSNName AS ''hsnName'', 
                CONCAT(p.ProductHierPath, p.prodCode, "/") AS ''productHierPathCode'', 
                CONCAT(finaltbl.ProductHierPathName, REPLACE (p.prodName, "/", "-"), "/") AS ''productHierPathName'', 
                ''N'' AS uploadflag, 
                Now() AS moddt 
FROM   Product p 
       INNER JOIN GstProduct gp 
               ON p.cmpCode = gp.cmpCode 
                  AND p.prodCode = gp.prodCode 
       INNER JOIN lobmaster p6 
               ON p.cmpCode = p6.cmpCode 
                  AND p6.ProdHierLvlCode != ''10'' 
                  AND p.ProductHierPath like concat(''%/'',p6.ProdHierValCode,''/%'') 
    INNER JOIN producthiervalue phv 
               ON p6.cmpCode = phv.cmpCode 
                  AND p6.ProdHierLvlCode = phv.ProdHierLvlCode 
                  AND p6.ProdHierValCode = phv.ProdHierValCode 
       INNER JOIN (SELECT 
       GROUP_CONCAT(IF (n<(SELECT COUNT(*)+1 from 
       producthierlevel) 
       , CONCAT("/", REPLACE(phv.ProdHierValName, "/", "-")), IF (n=(SELECT 
       COUNT(*)+1 
                                                     from 
producthierlevel), CONCAT("/", REPLACE(phv.ProdHierValName, "/", "-"), "/"), REPLACE(phv.ProdHierValName, "/", "-"))) 
ORDER BY temp.iterator SEPARATOR 
'''') AS ProductHierPathName, 
temp.iterator, 
temp.prodCode, 
temp.ProdHierValCode, 
phv.ProdHierValName, 
temp.n 
 FROM   (SELECT @i := @i + 1 AS iterator, 
                a.prodCode, 
                SUBSTRING_INDEX(SUBSTRING_INDEX(a.ProductHierPath, "/", 
                                numbers.n), "/", 
                -1) 
                             ProdHierValCode, 
                n 
         FROM   (SELECT @rownum := @rownum + 1 AS n 
                 FROM   producthierlevel aphv 
                        CROSS JOIN (SELECT @rownum := 1) r) numbers, 
                product a, 
                (SELECT @i := 0) AS foo) temp 
        INNER JOIN producthiervalue phv 
                ON temp.ProdHierValCode = phv.ProdHierValCode 
 GROUP  BY temp.prodCode 
 ORDER  BY temp.prodCode ASC) finaltbl 
        ON p.prodCode = finaltbl.ProdCode
INNER JOIN Changelog cl 
        ON p.cmpCode = cl.cmpCode 
           AND p.cmpCode = cl.key1 
           AND p.prodCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.Product'' 
AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.ProductEntity',
14,
null,
null,
null,
'N',
'Y',
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
('IN14',
'productbatch',
'M',
'Y',
'SELECT DISTINCT 	pb.cmpcode,
	       		 pp.geocode                               				 AS ''batchLevel'',
			p.prodcode                                               AS ''prodCode'',
			pb.prodbatchcode                                         AS ''prodBatchCode'',
			pb.mrp                                                   AS ''mrp'',
			pp.sellprice                                             AS ''sellRate'',
			COALESCE((pb.mrp / ( 1 + pp.dealermargin / 100 )), 0.0)  AS ''sellRateWithTax'',
			pp.effectivefromdate                                     AS ''manfDate'',
			pb.expirydt                                              AS ''expiryDate'',
			''Y''							AS ''latestBatch'',	
			''N''							AS ''geoLevelBatch'',	
			''N''							AS ''uploadflag'',
			Now()                                                    AS ''modDt''
		FROM    
			product p,
			productbatch pb,
			productpricing pp,
			geoproductbatch gpb,
			distributor d,
			changelog cl
		WHERE   
			p.cmpcode = pb.cmpcode
			AND p.prodcode = pb.prodcode
			AND pb.cmpcode = pp.cmpcode
			AND pb.prodcode = pp.prodcode
			AND pb.prodbatchcode = pp.prodbatchcode
			AND pp.cmpcode = gpb.cmpcode
			AND pp.prodcode = gpb.prodcode
			AND pp.prodbatchcode = gpb.prodbatchcode
			AND pp.geocode = gpb.geocode
			AND d.cmpcode = pp.cmpcode
			AND d.distrcode = pp.geocode
			AND pp.CmpCode = cl.CmpCode
			AND pp.cmpCode = cl.Key1
			AND pp.FromLevel = cl.Key2
			AND pp.geoCode = cl.Key3
			AND pp.prodBatchCode = cl.Key4
			AND pp.prodCode = cl.Key5
			AND pp.ToLevel = cl.Key6
			AND cl.ObjectType = ''com.botree.csng.domain.ProductPricing''
			AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo 
			AND cl.CmpCode = ''IN14''
			AND cl.Key1 = ''IN14''
			AND cl.DistrCode = ''none''
			AND cl.DistrBrCode = ''none''
			AND cl.EventType = ''C''

			AND p.cmpcode = ''In14''
			AND d.isdecentralized = ''Y''
			AND p.isactive = ''Y''
			AND d.distrcode in (:ids)
			AND p.producthierpath LIKE ''%/Nestle/%''; ',
'',
'com.botree.interdbentity.model.ProductBatchEntity',
15,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'productuom',
'M',
'Y',
'SELECT DISTINCT p.cmpcode, 
                p.prodCode      AS ''prodCode'', 
                p.UOMCODE       AS ''uomCode'', 
                uom.UOMName     AS ''uomDescription'', 
                p.UomConvFactor AS ''uomConvFactor'', 
                ugm.BaseUom     AS ''baseUOM'', 
                ugm.BaseUom     AS ''defaultUOM'', 
                ''N''             AS uploadflag, 
                Now()           AS moddt 
FROM   ProductUom p  
       INNER JOIN UOMMaster uom 
               ON p.cmpCode = uom.cmpCode 
                  AND p.UOMCODE = uom.UOMCODE 
       INNER JOIN productuomgroup pug 
               ON pug.cmpCode = p.cmpCode 
                  AND pug.prodCode = p.prodCode 
       INNER JOIN uomgroupmaster ugm 
               ON ugm.cmpCode = pug.cmpCode 
                  AND ugm.UomGroupCode = pug.UomGroupCode 
                  AND ugm.uomCode = uom.uomCode 
       INNER JOIN Changelog cl 
                  ON p.cmpCode = cl.cmpCode 
                  AND p.cmpCode = cl.key1 
                  AND p.ProdCode = cl.key2 
                  AND p.UOMCODE = cl.key3 
                  AND cl.DistrCode =''none''
                  AND cl.DistrBrCode =''none'' 
WHERE  cl.Objecttype = ''com.botree.csng.domain.ProductUom'' 
       AND cl.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.ProductUomEntity',
17,
null,
null,
null,
'N',
'Y',
0,
'24HRS');


-- taxstructure

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
('IN14',
'taxstructure',
'M',
'Y',
'SELECT A.CmpCode, 
       A.TaxStateCode, 
       A.ProdCode, 
       COALESCE(Sum(cgst), 0.00)          AS CGST, 
       COALESCE(Sum(sgst), 0.00)          AS SGST, 
       COALESCE(Sum(igst), 0.00)          AS IGST, 
       COALESCE(Sum(cess), 0.00)          AS CESS, 
       COALESCE(Sum(additionaltax), 0.00) AS AdditionalTax, 
       ''N''                                AS UploadFlag, 
       NOW()                              AS ModDt 
FROM   (SELECT DISTINCT pt.cmpcode, 
                        pt.taxstate                          AS TaxStateCode, 
                        pt.prodcode, 
                        COALESCE(tdcsgt.OutputTaxPerc, 0.00) AS CGST, 
                        COALESCE(tdsgst.OutputTaxPerc, 0.00) AS SGST, 
                        COALESCE(tdigst.OutputTaxPerc, 0.00) AS IGST, 
                        COALESCE(tdaddl.OutputTaxPerc, 0.00) AS CESS, 
                        COALESCE(tdcess.OutputTaxPerc, 0.00) AS AdditionalTax 
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
                         AND tdsgst.taxname in ( ''SGST'', ''UTGST'' ) 
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
                  tdcsgt.OutputTaxPerc, 
                  tdsgst.OutputTaxPerc, 
                  tdigst.OutputTaxPerc, 
                  tdcess.OutputTaxPerc, 
                  tdaddl.OutputTaxPerc) A 
GROUP  BY A.cmpcode, 
          A.prodcode, 
          A.taxstatecode;',
'',
'com.botree.interdbentity.model.TaxStructureEntity',
16,
null,
null,
null,
'N',
'Y',
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
('IN14',
'schemedefinition',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode    AS ''schemeCode'', 
                sd.SchemeName    AS ''schemeDescription'', 
                sd.SchemeBase    AS ''schemeBase'', 
                sd.SchemeStartDt AS ''schemeFromDt'', 
                sd.SchemeEndDt   AS ''schemeToDt'', 
                sd.PayOutType    AS ''payOutType'', 
                CASE 
                  WHEN sd.SchemeAtLevel = 1000000 THEN ''Y'' 
                  ELSE ''N'' 
                END              AS ''isSkuLevel'', 
                sd.IsOpen        AS ''isActive'', 
                sd.IsCombi       AS ''combi'', 
                ''N''              AS uploadflag, 
                Now()            AS moddt 
FROM   SchemeDefinition sd
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
		AND sd.IsClaimable =''Y''
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.distrCode in ( :ids ) 
	AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.SchemeDefinitionEntity',
18,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'schemedistributorbudget',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode   AS ''schemeCode'', 
                sdb.distrCode   AS ''distrCode'', 
                sdb.IsUnlimited AS ''isUnlimited'', 
                sdb.Budget      AS ''budget'', 
                sdb.IsActive    AS ''isActive'', 
                sdb.UtilizedAmt AS ''utilizedAmt'', 
                sdb.BudgetOs    AS ''budgetOs'', 
                ''N''             AS uploadflag, 
                Now()           AS moddt 
FROM   SchemeDefinition sd 
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'') 
		AND sd.IsClaimable =''Y''
       INNER JOIN (select c.Key1,c.Key2 as Key2,'''' as key3 from  changelog c where c.ObjectType=''com.botree.csng.domain.SchemeDefinition'' and c.cmpcode=''IN14'' AND c.changeno BETWEEN :minChangeNo AND :maxChangeNo
group by c.Key1,c.Key3,c.DistrCode
union all
select c.Key1,c.Key3 as Key2,c.DistrCode as key3 from  changelog c where c.ObjectType=''com.botree.csng.domain.SchemeDistributorBudget'' and c.cmpcode=''IN14''
and c.DistrCode in (:ids) AND c.changeno BETWEEN :minChangeNo AND :maxChangeNo
group by c.Key1,c.Key3,c.DistrCode) cl 
               ON  sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
		and (sdb.distrCode =cl.key3 or cl.key3='''')
WHERE   sdb.distrCode in ( :ids ) ;',
'',
'com.botree.interdbentity.model.SchemeDistributorBudgetEntity',
19,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'schemeslab',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode AS ''schemeCode'', 
                sb.SlabNo     AS ''slabNo'', 
                COALESCE(sb.SlabFrom,0.00)   AS ''slabFrom'', 
                COALESCE(sb.Slabto,0.00)     AS ''slabTo'', 
                COALESCE(sb.Payout,0.00)     AS ''payout'', 
                sb.SlabRange  AS ''slabRange'', 
                COALESCE(sb.ForEvery,0.00)   AS ''forEvery'', 
                sb.UOM        AS ''uomCode'', 
                ''N''           AS uploadflag, 
                Now()         AS moddt 
FROM   SchemeDefinition sd 
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'') 
		AND sd.IsClaimable =''Y''
 and sd.IsOpen=''Y''
       INNER JOIN SchemeSlab sb 
               ON sb.cmpCode = sd.cmpCode 
                  AND sb.schemeCode = sd.schemeCode 
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.isActive = ''Y'' 
       AND sdb.distrCode in ( :ids ) 
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.SchemeSlabEntity',
20,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'schemeslabproduct',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode  AS ''schemeCode'', 
                sb.SlabNo      AS ''slabNo'', 
                sb.ProdCode    AS ''prodCode'', 
                p.ProdName     AS ''prodName'', 
                sb.Quantity    AS ''qty'', 
                sb.isMandatory AS ''isMandatory'', 
                ''N''            AS uploadflag, 
                Now()          AS moddt 
FROM   SchemeDefinition sd 
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode
 AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'') 
		AND sd.IsClaimable =''Y''
 and sd.IsOpen=''Y''
       INNER JOIN schemeslabproduct sb 
               ON sb.cmpCode = sd.cmpCode 
                  AND sb.schemeCode = sd.schemeCode 
       INNER JOIN Product p 
               ON sb.cmpCode = p.cmpCode 
                  AND sb.prodCode = p.prodCode 
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.isActive = ''Y'' 
       AND sdb.distrCode in ( :ids ) 
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo; ',
'',
'com.botree.interdbentity.model.SchemeSlabProductEntity',
21,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'schemecustomer',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode   AS ''schemeCode'', 
                sc.CustomerCode AS ''customerCode'', 
                ''N''             AS uploadflag, 
                Now()           AS moddt 
FROM   SchemeDefinition sd 
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'') 
		AND sd.IsClaimable =''Y''
 and sd.IsOpen=''Y''
       INNER JOIN SchemeCustomer sc 
               ON sc.cmpCode = sd.cmpCode 
                  AND sc.schemeCode = sd.schemeCode 
       INNER JOIN Customer c 
               ON sc.cmpCode = c.CmpCode 
                  AND sc.customerCode = c.customerCode 
                  AND sdb.distrCode = c.distrCode 
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.isActive = ''Y'' 
       AND sdb.distrCode in ( :ids ) 
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.SchemeCustomerEntity',
22,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'schemeretailercategory',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode  AS ''schemeCode'', 
                sc.ChannelCode AS ''channelCode'', 
                sc.ChannelCode AS ''subChannelCode'', 
                sc.GroupCode   AS ''groupCode'', 
                sc.ClassCode   AS ''classCode'', 
                ''N''            AS uploadflag, 
                Now()          AS moddt 
FROM   SchemeDefinition sd  
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'') 
		AND sd.IsClaimable =''Y''
 and sd.IsOpen=''Y''
       INNER JOIN schemeretailercategory sc 
               ON sc.cmpCode = sd.cmpCode 
                  AND sc.schemeCode = sd.schemeCode 
	AND (sd.SchemeIdentifier ='N' or sd.SchemeIdentifier ='')
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.isActive = ''Y'' 
       AND sdb.distrCode in ( :ids ) 
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo 
	   ; ',
'',
'com.botree.interdbentity.model.SchemeRetailerCategoryEntity',
23,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'schemeproduct',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode AS ''schemeCode'', 
                sc.prodCode   AS ''prodCode'', 
                p.prodName    AS ''prodName'', 
                ''N''           AS uploadflag, 
                Now()         AS moddt 
FROM   SchemeDefinition sd 
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'') 
		AND sd.IsClaimable =''Y''
 and sd.IsOpen=''Y''
       INNER JOIN schemeproduct sc 
               ON sc.cmpCode = sd.cmpCode 
                  AND sc.schemeCode = sd.schemeCode 
       INNER JOIN Product p 
               ON sc.cmpCode = p.cmpCode 
                  AND p.prodCode = sc.prodCode 
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.isActive = ''Y'' 
	   AND sd.SchemeAtLevel = ''1000000''
       AND sdb.distrCode in ( :ids ) 
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.SchemeProductEntity',
24,
null,
null,
null,
'Y',
'Y',
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
('IN14',
'schemeproductcategory',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode                         AS ''schemeCode'', 
                Cast(spc.ProdHierLvlCode AS CHAR(10)) AS ''prodHierLvlCode'', 
                spc.ProdHierValCode                   AS ''prodHierValCode'', 
                phv.ProdHierValName                   AS ''prodHierValName'', 
                ''N''                                   AS uploadflag, 
                Now()                                 AS moddt 
FROM   SchemeDefinition sd  
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'') 
		AND sd.IsClaimable =''Y''
 and sd.IsOpen=''Y''
AND sd.schemeCode not in(select sd1.SchemeCode  FROM   SchemeDefinition sd1 
       INNER JOIN SchemeDistributorBudget sdb1 
               ON sd1.cmpCode = sdb1.cmpCode 
                  AND sd1.schemeCode = sdb1.schemeCode 
AND sd1.schemeType NOT IN(''QPS'')  AND   sd1.schemeBase NOT IN(''WD'')
		AND sd1.IsClaimable =''Y'' 
 and sd1.IsOpen=''Y''
       INNER JOIN schemeproduct sc1
               ON sc1.cmpCode = sd1.cmpCode 
                  AND sc1.schemeCode = sd1.schemeCode 
       INNER JOIN Product p1 
               ON sc1.cmpCode = p1.cmpCode 
                  AND p1.prodCode = sc1.prodCode 
       INNER JOIN Changelog cl1 
               ON sd1.cmpCode = cl1.cmpCode 
                  AND sd1.cmpCode = cl1.key1 
                  AND sd1.SchemeCode = cl1.key2 
WHERE  cl1.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb1.isActive = ''Y'' 
	   AND sd1.SchemeAtLevel = ''1000000''
       AND sdb1.distrCode in ( :ids ) 
       AND cl1.changeno BETWEEN :minChangeNo AND :maxChangeNo)
       INNER JOIN schemeproductcategory spc 
               ON spc.cmpCode = sd.cmpCode 
                  AND spc.schemeCode = sd.schemeCode
 
       INNER JOIN producthiervalue phv 
               ON spc.cmpCode = phv.cmpCode 
                  AND spc.ProdHierValCode = phv.ProdHierValCode 
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.isActive = ''Y'' 
	   AND sd.SchemeAtLevel != ''1000000''
       AND sdb.distrCode in ( :ids ) 
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.SchemeProductCategoryEntity',
25,
null,
null,
null,
'Y',
'Y',
0,
'24HRS');


-- schemeattributes

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
('IN14',
'schemeattributes',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                oa.AttributeCode                        AS ''attributeCode'', 
                Cast(oa.AttributeValueCode AS CHAR(10)) AS ''attributeValueCode'', 
                oa.AttrInputValues                      AS ''attrInputValues'', 
                sd.SchemeCode                           AS ''refNo'', 
                oa.SNo                                  AS ''sNo'', 
                ''N''                                     AS uploadflag, 
                Now()                                   AS moddt 
FROM   SchemeDefinition sd 
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'')
		AND sd.IsClaimable =''Y'' 
 and sd.IsOpen=''Y''
       INNER JOIN otherattributes oa 
               ON oa.cmpCode = sd.cmpCode 
                  AND oa.RefNo = sd.schemeCode 
                  AND oa.ModuleNo = ''500'' 
                  AND oa.ScreenNo = ''1'' 
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.isActive = ''Y'' 
       AND sdb.distrCode in ( :ids ) 
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo;',
'',
'com.botree.interdbentity.model.SchemeAttributesEntity',
26,
null,
null,
null,
'Y',
'Y',
0,
'24HRS');


-- schemecombiproduct

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
('IN14',
'schemecombiproduct',
'M',
'Y',
'SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode                AS ''schemeCode'', 
                Cast(scp.SlabNo AS CHAR(10)) AS ''slabNo'', 
                scp.ProdCode                 AS ''prodCode'', 
                p.prodName                   AS ''prodName'', 
                scp.MinValue                 AS ''minValue'', 
                scp.isMandatory              AS ''isMandatory'', 
                ''N''                          AS uploadflag, 
                Now()                        AS moddt 
FROM   SchemeDefinition sd 
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'') 
		AND sd.IsClaimable =''Y''
 and sd.IsOpen=''Y''
       INNER JOIN schemecombiproduct scp 
               ON scp.cmpCode = sd.cmpCode 
                  AND scp.schemeCode = sd.schemeCode 
       INNER JOIN Product p 
               ON p.cmpCode = scp.cmpCode 
                  AND p.prodCode = scp.prodCode 
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.isActive = ''Y'' 
       AND sdb.distrCode in ( :ids ) 
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo 
UNION 
SELECT DISTINCT sd.cmpcode, 
                sd.SchemeCode                AS ''schemeCode'', 
                Cast(scp.SlabNo AS CHAR(10)) AS ''slabNo'', 
                p.ProdCode                   AS ''prodCode'', 
                p.prodName                   AS ''prodName'', 
                scp.MinValue                 AS ''minValue'', 
                scp.isMandatory              AS ''isMandatory'', 
                ''N''                          AS uploadflag, 
                Now()                        AS moddt 
FROM   SchemeDefinition sd 
       INNER JOIN SchemeDistributorBudget sdb 
               ON sd.cmpCode = sdb.cmpCode 
                  AND sd.schemeCode = sdb.schemeCode 
AND sd.schemeType NOT IN(''QPS'')  AND   sd.schemeBase NOT IN(''WD'') 
		AND sd.IsClaimable =''Y''
 and sd.IsOpen=''Y''
       INNER JOIN schemecombiproducthierarchy scp 
               ON scp.cmpCode = sd.cmpCode 
                  AND scp.schemeCode = sd.schemeCode 
       INNER JOIN Product p 
               ON p.cmpCode = scp.cmpCode 
                  AND p.productHierPath like CONCAT(''%/'', scp.ProdHierValCode, ''/%'') 
       INNER JOIN Changelog cl 
               ON sd.cmpCode = cl.cmpCode 
                  AND sd.cmpCode = cl.key1 
                  AND sd.SchemeCode = cl.key2 
WHERE  cl.Objecttype = ''com.botree.csng.domain.SchemeDefinition'' 
       AND sdb.isActive = ''Y'' 
       AND sdb.distrCode in ( :ids ) 
       AND cl.changeno BETWEEN :minChangeNo AND :maxChangeNo; ',
'',
'com.botree.interdbentity.model.SchemeCombiProductEntity',
27,
null,
null,
null,
'Y',
'Y',
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
('IN14',
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
28,
null,
null,
null,
'Y',
'N',
0,
'24HRS');

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
Incremental,
MaxChangeNo,
Intervals)
VALUES
('IN14',
'customerstock',
'M',
'Y',
'SELECT 
    temp.CmpCode,
    temp.DistrCode,
    temp.CustomerCode,
    GROUP_CONCAT(DISTINCT (temp.prodcode)) AS Stock,
    UploadFlag,
    ModDt
FROM
    (SELECT 
        ih.cmpCode,
            ih.distrcode AS distrcode,
            ih.customerCode,
            CONCAT(id.prodcode, ''^'', 0, ''^'', '''', ''^'', ''Y'', ''^'', CASE
                WHEN (soh.saleableqty - soh.resvsaleqty) > 0 THEN ''#A5D6A7''
                ELSE ''#FFF59D''
            END) AS prodcode,
            ''N'' AS UploadFlag,
            NOW() AS ModDt
    FROM
        invoiceheader ih
    INNER JOIN invoicedetails id ON id.CmpCode = ih.CmpCode
        AND id.DistrCode = ih.DistrCode
        AND id.DistrBrCode = ih.DistrBrCode
        AND id.InvoiceNumber = ih.InvoiceNumber
    INNER JOIN distributor d ON d.cmpcode = ih.cmpcode
        AND d.distrcode = ih.distrcode
    INNER JOIN customer c ON ih.cmpcode = c.cmpcode
        AND ih.distrcode = c.distrcode
        AND ih.distrbrcode = c.distrbrcode
        AND ih.CustomerCode = c.CustomerCode
    LEFT OUTER JOIN (SELECT 
        soh1.cmpcode,
            soh1.distrcode,
            soh1.distrBrcode,
            soh1.prodcode,
            soh1.saleableqty,
            soh1.resvsaleqty
    FROM
        stockonhand soh1
    WHERE
        soh1.DistrCode IN (:ids)
            AND soh1.distrBrcode IN (:ids)
            AND (soh1.saleableqty - soh1.resvsaleqty) > 0
            AND soh1.CmpCode = ''IN14''
    GROUP BY soh1.cmpcode , soh1.distrcode , soh1.distrBrcode , soh1.prodcode) soh ON soh.CmpCode = ih.CmpCode
        AND soh.DistrBrCode = ih.DistrBrCode
        AND soh.DistrCode = ih.DistrCode
        AND soh.ProdCode = id.ProdCode
        AND (soh.saleableqty - soh.resvsaleqty) > 0
    WHERE
        d.isdecentralized = ''Y''
            AND c.isActive = ''Y''
            AND ih.cmpCode = ''IN14''
            AND ih.DistrCode IN (:ids)
            AND ih.invoicedt BETWEEN (DATE_SUB(CURRENT_DATE(), INTERVAL 90 DAY)) AND CURRENT_DATE()
            AND (c.DistrCode , c.ChannelCode, c.RetlrGroupCode, c.ClassCode, id.prodcode) NOT IN (SELECT DISTINCT
                distrCode,
                    rtrGroupCode,
                    rtrChannelCode,
                    excludedRtrSubTypeCode,
                    excludedPrdHierarchyLevelValue
            FROM
                (SELECT 
                nd.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    nsp.prodCode AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN nonsellingskudistributor nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
                AND nd.distrCode IN (:ids)
            INNER JOIN nonsellingskuproduct nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN product p ON nsp.cmpCode = p.cmpCode
                AND nsp.prodCode = p.prodCode
                AND p.isActive = ''Y''
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType UNION ALL SELECT 
                d.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    nsp.prodCode AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN nonsellingskudistributorcategory nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN distributor d ON d.cmpCode = nd.cmpCode
                AND d.geoHierPath LIKE CONCAT(''%'', nd.categoryCode, ''%'')
                AND d.distrCode IN (:ids)
            INNER JOIN nonsellingskuproduct nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN product p ON nsp.cmpCode = p.cmpCode
                AND nsp.prodCode = p.prodCode
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            WHERE
                nd.categoryType = ''g''
                    AND p.isActive = ''Y'' UNION ALL SELECT 
                d.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    nsp.prodCode AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN nonsellingskudistributorcategory nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN distributorSalesHierarchy d ON d.cmpCode = nd.cmpCode
                AND d.salesHierPath LIKE CONCAT(''%'', nd.categoryCode, ''%'')
                AND d.distrCode IN (:ids)
            INNER JOIN nonsellingskuproduct nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN product p ON nsp.cmpCode = p.cmpCode
                AND nsp.prodCode = p.prodCode
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            WHERE
                nd.categoryType = ''s''
                    AND p.isActive = ''Y'' UNION ALL SELECT 
                nd.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    COALESCE(t.prod_code, t1.prod_code) AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
            INNER JOIN nonsellingskudistributor nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
                AND nd.distrCode IN (:ids)
            INNER JOIN nonsellingskuproductcategory nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            LEFT OUTER JOIN RPT_PRODUCT_T t ON t.cmp_code = nsc.cmpCode
                AND t.product_hier5_code = nsp.prodHierValCode
            LEFT OUTER JOIN RPT_PRODUCT_T t1 ON t1.cmp_code = nsc.cmpCode
                AND t1.product_hier7_code = nsp.prodHierValCode UNION ALL SELECT 
                d.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    COALESCE(t.prod_code, t1.prod_code) AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
            INNER JOIN nonsellingskudistributorcategory nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN distributor d ON d.cmpCode = nd.cmpCode
                AND d.geoHierPath LIKE CONCAT(''%/'', nd.categoryCode, ''/%'')
                AND d.distrCode IN (:ids)
            INNER JOIN nonsellingskuproductcategory nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            LEFT OUTER JOIN RPT_PRODUCT_T t ON t.cmp_code = nsc.cmpCode
                AND t.product_hier5_code = nsp.prodHierValCode
            LEFT OUTER JOIN RPT_PRODUCT_T t1 ON t1.cmp_code = nsc.cmpCode
                AND t1.product_hier7_code = nsp.prodHierValCode
            WHERE
                nd.categoryType = ''g'' UNION ALL SELECT 
                d.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    COALESCE(t.prod_code, t1.prod_code) AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
            INNER JOIN nonsellingskudistributorcategory nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN distributorSalesHierarchy d ON d.cmpCode = nd.cmpCode
                AND d.salesHierPath LIKE CONCAT(''%/'', nd.categoryCode, ''/%'')
                AND d.distrCode IN (:ids)
            INNER JOIN nonsellingskuproductcategory nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            LEFT OUTER JOIN RPT_PRODUCT_T t ON t.cmp_code = nsc.cmpCode
                AND t.product_hier5_code = nsp.prodHierValCode
            LEFT OUTER JOIN RPT_PRODUCT_T t1 ON t1.cmp_code = nsc.cmpCode
                AND t1.product_hier7_code = nsp.prodHierValCode
            WHERE
                nd.categoryType = ''s'') temptbl)
    GROUP BY ih.distrcode , ih.customerCode , id.ProdCode UNION SELECT 
        soh.CmpCode,
            soh.distrcode AS distrcode,
            c.customerCode,
            CONCAT(soh.prodcode, ''^'', 0, ''^'', '''', ''^'', ''Y'', ''^'', CASE
                WHEN (soh.saleableqty - soh.resvsaleqty) > 0 THEN ''#A5D6A7''
                ELSE ''#FFF59D''
            END) AS prodcode,
            ''N'' AS UploadFlag,
            NOW() AS ModDt
    FROM
        distributor d
    INNER JOIN (SELECT 
        soh1.cmpcode,
            soh1.distrcode,
            soh1.distrBrcode,
            soh1.prodcode,
            soh1.saleableqty,
            soh1.resvsaleqty
    FROM
        stockonhand soh1
    WHERE
        soh1.DistrCode IN (:ids)
            AND soh1.distrBrcode IN (:ids)
            AND (soh1.saleableqty - soh1.resvsaleqty) > 0
            AND soh1.cmpcode = ''IN14''
    GROUP BY soh1.cmpcode , soh1.distrcode , soh1.distrBrcode , soh1.prodcode) soh ON d.cmpcode = soh.cmpcode
        AND d.distrcode = soh.distrcode
        AND d.distrcode = soh.distrBrcode
        AND d.isdecentralized = ''Y''
        AND d.DistrCode IN (:ids)
        AND (soh.saleableqty - soh.resvsaleqty) > 0
    INNER JOIN (SELECT 
        cu.cmpcode,
            cu.distrcode,
            cu.distrbrcode,
            cu.customercode,
            cu.ChannelCode,
            cu.RetlrGroupCode,
            cu.ClassCode
    FROM
        Customer cu
    WHERE
        cu.DistrCode IN (:ids)
            AND cu.CmpCode = ''IN14''
            AND cu.isActive = ''Y''
    GROUP BY cu.cmpcode , cu.distrcode , cu.distrbrcode , cu.customercode) c ON soh.cmpcode = c.cmpcode
        AND soh.distrcode = c.distrcode
        AND soh.distrbrcode = c.distrbrcode
        AND c.DistrCode IN (:ids)
        AND c.CmpCode = ''IN14''
    WHERE
        (soh.cmpcode , soh.distrcode, c.customerCode, soh.prodcode) NOT IN (SELECT 
                ih.cmpCode, ih.distrcode, ih.customerCode, id.prodcode
            FROM
                invoiceheader ih
            INNER JOIN invoicedetails id ON id.CmpCode = ih.CmpCode
                AND id.DistrCode = ih.DistrCode
                AND id.DistrBrCode = ih.DistrBrCode
                AND id.InvoiceNumber = ih.InvoiceNumber
                AND ih.cmpCode = ''IN14''
                AND ih.DistrCode IN (:ids)
                AND ih.invoicedt BETWEEN (DATE_SUB(CURRENT_DATE(), INTERVAL 90 DAY)) AND CURRENT_DATE()
            INNER JOIN distributor d ON d.cmpcode = ih.cmpcode
                AND d.distrcode = ih.distrcode
                AND d.isdecentralized = ''Y''
            INNER JOIN customer c ON ih.cmpcode = c.cmpcode
                AND ih.distrcode = c.distrcode
                AND ih.distrbrcode = c.distrbrcode
                AND ih.CustomerCode = c.CustomerCode
                AND c.isActive = ''Y''
            GROUP BY ih.distrcode , ih.customerCode , id.ProdCode)
            AND (c.DistrCode , c.ChannelCode, c.RetlrGroupCode, c.ClassCode, soh.prodcode) NOT IN (SELECT DISTINCT
                distrCode,
                    rtrGroupCode,
                    rtrChannelCode,
                    excludedRtrSubTypeCode,
                    excludedPrdHierarchyLevelValue
            FROM
                (SELECT 
                nd.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    nsp.prodCode AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN nonsellingskudistributor nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
                AND nd.distrCode IN (:ids)
            INNER JOIN nonsellingskuproduct nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN product p ON nsp.cmpCode = p.cmpCode
                AND nsp.prodCode = p.prodCode
                AND p.isActive = ''Y''
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType UNION ALL SELECT 
                d.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    nsp.prodCode AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN nonsellingskudistributorcategory nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN distributor d ON d.cmpCode = nd.cmpCode
                AND d.geoHierPath LIKE CONCAT(''%'', nd.categoryCode, ''%'')
                AND d.distrCode IN (:ids)
            INNER JOIN nonsellingskuproduct nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN product p ON nsp.cmpCode = p.cmpCode
                AND nsp.prodCode = p.prodCode
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            WHERE
                nd.categoryType = ''g''
                    AND p.isActive = ''Y'' UNION ALL SELECT 
                d.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    nsp.prodCode AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN nonsellingskudistributorcategory nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN distributorSalesHierarchy d ON d.cmpCode = nd.cmpCode
                AND d.salesHierPath LIKE CONCAT(''%'', nd.categoryCode, ''%'')
                AND d.distrCode IN (:ids)
            INNER JOIN nonsellingskuproduct nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN product p ON nsp.cmpCode = p.cmpCode
                AND nsp.prodCode = p.prodCode
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            WHERE
                nd.categoryType = ''s''
                    AND p.isActive = ''Y'' UNION ALL SELECT 
                nd.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    COALESCE(t.prod_code, t1.prod_code) AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
            INNER JOIN nonsellingskudistributor nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
                AND nd.distrCode IN (:ids)
            INNER JOIN nonsellingskuproductcategory nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            LEFT OUTER JOIN RPT_PRODUCT_T t ON t.cmp_code = nsc.cmpCode
                AND t.product_hier5_code = nsp.prodHierValCode
            LEFT OUTER JOIN RPT_PRODUCT_T t1 ON t1.cmp_code = nsc.cmpCode
                AND t1.product_hier7_code = nsp.prodHierValCode UNION ALL SELECT 
                d.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    COALESCE(t.prod_code, t1.prod_code) AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
            INNER JOIN nonsellingskudistributorcategory nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN distributor d ON d.cmpCode = nd.cmpCode
                AND d.geoHierPath LIKE CONCAT(''%/'', nd.categoryCode, ''/%'')
                AND d.distrCode IN (:ids)
            INNER JOIN nonsellingskuproductcategory nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            LEFT OUTER JOIN RPT_PRODUCT_T t ON t.cmp_code = nsc.cmpCode
                AND t.product_hier5_code = nsp.prodHierValCode
            LEFT OUTER JOIN RPT_PRODUCT_T t1 ON t1.cmp_code = nsc.cmpCode
                AND t1.product_hier7_code = nsp.prodHierValCode
            WHERE
                nd.categoryType = ''g'' UNION ALL SELECT 
                d.distrCode,
                    rc.channelCode AS rtrGroupCode,
                    nsc.channelCode AS rtrChannelCode,
                    nsc.channelSubType AS excludedRtrSubTypeCode,
                    COALESCE(t.prod_code, t1.prod_code) AS excludedPrdHierarchyLevelValue
            FROM
                nonsellingskudefinition nsd
            INNER JOIN nonsellingskuretailercategory nsc ON nsd.cmpCode = nsc.cmpCode
                AND nsd.nonSellingRefNo = nsc.nonSellingRefNo
                AND DATE(NOW()) BETWEEN nsd.fromDate AND nsd.toDate
            INNER JOIN nonsellingskudistributorcategory nd ON nd.cmpCode = nsc.cmpCode
                AND nd.nonSellingRefNo = nsc.nonSellingRefNo
            INNER JOIN distributorSalesHierarchy d ON d.cmpCode = nd.cmpCode
                AND d.salesHierPath LIKE CONCAT(''%/'', nd.categoryCode, ''/%'')
                AND d.distrCode IN (:ids)
            INNER JOIN nonsellingskuproductcategory nsp ON nsp.cmpCode = nd.cmpCode
                AND nsp.nonSellingRefNo = nd.nonSellingRefNo
            INNER JOIN retailerClass rc ON rc.cmpCode = nsc.cmpCode
                AND rc.retlrGroupCode = nsc.channelCode
                AND rc.classCode = nsc.channelSubType
            LEFT OUTER JOIN RPT_PRODUCT_T t ON t.cmp_code = nsc.cmpCode
                AND t.product_hier5_code = nsp.prodHierValCode
            LEFT OUTER JOIN RPT_PRODUCT_T t1 ON t1.cmp_code = nsc.cmpCode
                AND t1.product_hier7_code = nsp.prodHierValCode
            WHERE
                nd.categoryType = ''s'') temptbl)
    GROUP BY soh.distrcode , c.customerCode , soh.prodcode) temp
GROUP BY temp.cmpCode , temp.distrcode , temp.customerCode;',
'',
'com.botree.interdbentity.model.CustomerStockEntity',
29,
null,
null,
null,
'Y',
'N',
0,
'24HRS');


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
('IN14',
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
('IN14',
'orderstatusactionapi',
'T',
'Y',
'SELECT DISTINCT oh.cmpcode AS CmpCode,
                oh.distrcode AS DistrCode,
                MAX(cl.moddt) AS ActionTime,
                ''ORDER_STATUS'' AS ActionCode,
                CONCAT(oh.orderno,''^R^Order Submitted'') AS ActionTemplate,
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
	AND ih.InvoiceStatus != ''C'' 	
        AND cl.changeno between :minChangeNo and :maxChangeNo
        GROUP BY oh.cmpcode,oh.distrcode,oh.orderno	  	   
        UNION
		SELECT DISTINCT oh.cmpcode AS CmpCode,
                oh.distrcode AS DistrCode,
                MAX(vad.moddt) AS ActionTime,
                ''ORDER_STATUS'' AS ActionCode,
                case when ih.invoicestatus =''C'' then CONCAT(oh.orderno, ''^C^Order Cancelled'') else CONCAT(oh.orderno, ''^V^Vehicle Allocated'') end AS ActionTemplate,
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
        AND cl.eventtype = ''C''  	
        AND cl.changeno between :minChangeNo and :maxChangeNo
        GROUP BY oh.cmpcode,oh.distrcode,oh.orderno	
		UNION   
        SELECT DISTINCT oh.cmpcode AS CmpCode,
                oh.distrcode AS DistrCode,
                MAX(ih.moddt) AS ActionTime,
                ''ORDER_STATUS'' AS ActionCode,
                CONCAT(oh.orderno, ''^D^Invoice Generated'') AS ActionTemplate,
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
                MAX(cl.moddt) AS ActionTime,
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

-- Salesman

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
('IN14',
'salesman',
'M',
'Y',
'SELECT 
s.CmpCode AS CmpCode,
s.DistrCode AS DistrCode,
s.SalesmanCode AS SalesmanCode,
s.SalesmanName AS SalesmanName,
s.PhoneNo AS MobileNo,
s.EmailID AS EmailID,
s.IsActive AS Status,
''N'' AS UploadFlag,
ifnull(s.moddt,now()) AS ModDt
        FROM   salesman s
               INNER JOIN (SELECT Key1,
                                  Key2,
                                  Key3,
                                  Key4
                           FROM   changelog c
                           WHERE  c.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo 
                                  AND ObjectType = ''com.botree.csng.domain.Salesman''
                                  AND  c.Key2 in (:ids)) sc
                       ON s.CmpCode = sc.Key1
                          AND s.DistrCode = sc.Key2
                          AND s.DistrBrCode = sc.Key3
                          AND s.SalesmanCode = sc.Key4
                          AND s.DistrCode in (:ids)',
'',
'com.botree.interdbentity.model.SalesmanEntity',
9,
null,
null,
null,
'Y',
'Y',
0,
'24HRS');


-- Salesman Route

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
('IN14',
'salesmanroutemapping',
'M',
'Y',
'SELECT 
 s.CmpCode AS CmpCode,
 s.DistrCode AS DistrCode,
 s.SalesmanCode AS SalesmanCode,
 s.RouteCode AS RouteCode,
 group_concat(distinct DAYNAME(rcp.coverageDt)) as CoverageDay,
 ''N'' AS UploadFlag,
 ifnull(s.moddt,now()) AS ModDt
         FROM   salesmanroute s
         left outer join routecoverageplan rcp on rcp.CmpCode=s.CmpCode
         and rcp.DistrBrCode = s.DistrBrCode and 
         rcp.DistrCode = s.DistrCode and
         rcp.SalesmanCode = s.SalesmanCode and 
         rcp.RouteCode = s.RouteCode and
         month(rcp.CoverageDt) =month(now()) and
         year(rcp.coverageDt) =year(now())
		INNER JOIN (SELECT Key1,
                                   Key2,
                                   Key3,
                                   Key5
                            FROM   changelog c
                            WHERE  c.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo 
                                   AND ObjectType = ''com.botree.csng.domain.SalesmanRoute''
                                   AND  c.Key2 in (:ids)
                                   group by Key1,Key2,Key3,Key5
                             union all
                             SELECT Key1,
                                   Key3 as Key2,
                                   Key4 as Key3,
                                   Key5
                            FROM   changelog c
                            WHERE  c.ChangeNo  BETWEEN :minChangeNo AND :maxChangeNo 
                                   AND ObjectType = ''com.botree.csng.domain.RouteCoveragePlan''
                                   AND  c.Key3 in (:ids)
                                   group by Key1,Key3,Key4,Key5
                                   ) sc
                        ON s.CmpCode = sc.Key1
                           AND s.DistrCode = sc.Key2
                           AND s.DistrBrCode = sc.Key3
                           AND s.SalesmanCode = sc.Key5
                           where s.DistrCode in (:ids)
                           group by s.CmpCode,s.DistrCode, s.SalesmanCode,s.RouteCode',
'',
'com.botree.interdbentity.model.SalesmanRouteMappingEntity',
11,
null,
null,
null,
'Y',
'Y',
0,
'24HRS');


-- Customer Route

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
('IN14',
'customerroutemapping',
'M',
'Y',
'SELECT 
s.CmpCode AS CmpCode,
s.DistrCode AS DistrCode,
s.CustomerCode AS CustomerCode,
s.RouteCode AS RouteCode,
''N'' AS UploadFlag,
ifnull(s.moddt,now()) AS ModDt
        FROM   customerroute s
               INNER JOIN (SELECT Key1,
                                  Key2,
                                  Key3,
                                  Key4
                           FROM   changelog c
                           WHERE  c.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo 
                                  AND ObjectType = ''com.botree.csng.domain.CustomerRoute''
                                  AND  c.Key3 in (:ids)
                                  ) sc
                       ON s.CmpCode = sc.Key1
                          AND s.DistrCode = sc.Key3
                          AND s.DistrBrCode = sc.Key4
                          AND s.CustomerCode = sc.Key2
                          AND s.DistrCode in (:ids);',
'',
'com.botree.interdbentity.model.CustomerRouteMappingEntity',
10,
null,
null,
null,
'Y',
'Y',
0,
'24HRS');

-- Route

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
('IN14',
'route',
'M',
'Y',
'SELECT 
s.CmpCode AS CmpCode,
s.DistrCode AS DistrCode,
s.RouteCode AS RouteCode,
s.RouteName AS RouteName,
s.RouteType AS RouteType,
s.IsActive AS RouteStatus,
''N'' AS UploadFlag,
ifnull(s.moddt,now()) AS ModDt
        FROM   route s
               INNER JOIN (SELECT Key1,
                                  Key2,
                                  Key3,
                                  Key4
                           FROM   changelog c
                           WHERE  c.ChangeNo BETWEEN :minChangeNo AND :maxChangeNo 
                                  AND ObjectType = ''com.botree.csng.domain.Route''
                                   AND  c.Key2 in (:ids)
                                  ) sc
                       ON s.CmpCode = sc.Key1
                          AND s.DistrCode = sc.Key2
                          AND s.DistrBrCode = sc.Key3
                          AND s.routeCode = sc.Key4
                           AND s.DistrCode in (:ids);',
'',
'com.botree.interdbentity.model.RouteEntity',
8,
null,
null,
null,
'Y',
'Y',
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
Incremental)
VALUES
('IN14',
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
30,
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
('IN14',
'companyuser',
'M',
'Y',
'SELECT au.CmpCode,
       au.UserCode AS LoginCode,
       UserName,
       (CASE WHEN au.distrCode IS NULL THEN ''C'' ELSE ''D'' END) AS UserType,
       (CASE WHEN au.distrCode IS NULL THEN usfm.SalesForceCode ELSE au.distrCode END) AS MappedCode,
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
31,
null,
null,
null,
'Y',
'N');


