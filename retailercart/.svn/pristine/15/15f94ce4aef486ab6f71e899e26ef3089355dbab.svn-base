-- 05/08/2020
CREATE INDEX index_system_generated_sms_useractivation_1
ON useractivation (LoginCode, ActivationDt);

CREATE INDEX index_system_generated_sms_useractivation_2
ON useractivation (LoginCode, ActivationDt, MsgRequestDt);

CREATE INDEX index_system_generated_sms_customer_1
ON customer (MobileNo, RetailerStatus);

CREATE INDEX index_system_generated_sms_orderbookingheader_1
ON customer (CmpCode, DistrCode, CustomerCode);

CREATE INDEX index_update_latest_batch_productbatch_1
ON productbatch (CmpCode, BatchLevel, ProdCode, ProdBatchCode, LatestBatch);



-- 18/05/2021
CREATE INDEX index_download_master_1
ON keygenerator (LoginCode);

CREATE INDEX index_download_master_1
ON distributor (CmpCode, DistrCode, DistrStatus);

CREATE INDEX index_download_master_2
ON distributor (CmpCode, DistrCode, GSTStateCode);

CREATE INDEX index_download_master_1
ON distributorlobmapping (CmpCode, DistrCode);

CREATE INDEX index_download_master_2
ON distributorlobmapping (CmpCode, DistrCode, LOBCode);

CREATE INDEX index_download_master_1
ON customershipaddress (CmpCode, DistrCode, CustomerCode);

CREATE INDEX index_download_master_1
ON producthierlevel (CmpCode);

CREATE INDEX index_download_master_1
ON producthiervalue (CmpCode);

CREATE INDEX index_download_master_1
ON product (CmpCode, ProdCode, LOBCode, ProdStatus);

CREATE INDEX index_download_master_1
ON productuom (CmpCode, ProdCode);

CREATE INDEX index_download_master_1
ON productbatch (CmpCode, BatchLevel, ProdCode, GeoLevelBatch, LatestBatch, ExpiryDate);

CREATE INDEX index_download_master_1
ON orderbookingheader (CmpCode, DistrCode, OrderNo, CustomerCode, OrderDt);

CREATE INDEX index_download_master_1
ON orderbookingdetails (CmpCode, DistrCode, OrderNo, ProdCode);

CREATE INDEX index_download_scheme_1
ON schemedefinition (CmpCode, SchemeCode, IsActive, SchemeFromDt, SchemeToDt);

CREATE INDEX index_download_scheme_1
ON schemedistributorbudget (CmpCode, SchemeCode, IsActive);

CREATE INDEX index_download_scheme_1
ON schemeslab (CmpCode, SchemeCode);

CREATE INDEX index_download_scheme_1
ON schemeslabproduct (CmpCode, SchemeCode);

CREATE INDEX index_download_scheme_1
ON schemecombiproduct (CmpCode, SchemeCode);

CREATE INDEX index_download_scheme_1
ON customer (CmpCode, DistrCode, CustomerCode, ChannelCode, SubChannelCode, GroupCode, ClassCode);

CREATE INDEX index_download_scheme_1
ON schemeproduct (CmpCode, SchemeCode);

CREATE INDEX index_download_scheme_1
ON schemeproductcategory (CmpCode, SchemeCode);

CREATE INDEX index_download_report_1
ON orderbookingheader (CmpCode, DistrCode, CustomerCode, OrderDt);

CREATE INDEX index_download_report_1
ON action_order_status (CmpCode, DistrCode, OrderNo);

-- 24/05/2021
CREATE INDEX index_appuser_logincode
ON appuser (LoginCode); 

CREATE INDEX index_appuser_log_exporter
ON appuser (LoginStatus, LoggedInTime);
