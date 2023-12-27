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
