ALTER TABLE appuser
ADD COLUMN HostName VARCHAR(50) DEFAULT NULL AFTER DeviceId,
ADD COLUMN LoggedInTime DATETIME DEFAULT NULL AFTER HostName;


INSERT INTO configuration (TableName,Code,Description,GroupName,UsedBy,Component,Input1,Input2,Input3,Input4,Input5, UploadFlag, ModDt) 
VALUES ('LoggerExportFreq','2','Logger exporter frequency in minutes','System', 'System', NULL,NULL,NULL,NULL,NULL,NULL, 'N', NOW());
