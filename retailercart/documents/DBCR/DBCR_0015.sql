ALTER TABLE salesmanroutemapping
ADD COLUMN CoverageDay VARCHAR(100) DEFAULT NULL AFTER RouteCode;


ALTER TABLE distributor
MODIFY COLUMN DayOff VARCHAR(250) DEFAULT NULL;
