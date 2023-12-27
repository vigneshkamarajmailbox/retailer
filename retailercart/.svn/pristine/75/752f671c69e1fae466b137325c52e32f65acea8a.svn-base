ALTER TABLE orderbookingheader
ADD COLUMN TotalGrossValue DECIMAL(22 , 6 ) DEFAULT '0.000000' AFTER OrderStatus;

ALTER TABLE orderbookingdetails
ADD COLUMN GrossValue DECIMAL(22 , 6 ) DEFAULT '0.000000' AFTER SellRate;

ALTER TABLE orderbookingdetails
ADD COLUMN ActualSellRate DECIMAL(22 , 6 ) DEFAULT '0.000000' AFTER SellRate;

UPDATE orderbookingdetails SET GrossValue = OrderQty*SellRate;

UPDATE orderbookingheader oh
INNER JOIN  
(SELECT CmpCode, DistrCode, OrderNo, SUM(GrossValue) AS GrossValue FROM orderbookingdetails
GROUP BY CmpCode, DistrCode, OrderNo) tmp
ON oh.CmpCode = tmp.CmpCode
AND oh.DistrCode = tmp.DistrCode
AND oh.OrderNo = tmp.OrderNo
SET oh.TotalGrossValue = GrossValue;
