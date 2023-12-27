UPDATE screenaccess SET IsAccess = 'Y' WHERE ModuleNo = 1 AND ScreenNo = 5;


INSERT INTO screen(ModuleNo,ScreenNo,ModuleName,ScreenName,ScreenType,Sequence,UploadFlag,ModDt)
VALUES (1,11,'Landing','My Profile','M',11,'N',NOW());


INSERT INTO screenaccess
SELECT 'DEFAULT','DEFAULT',ModuleNo,ScreenNo,'Y','N',NOW()
FROM screen WHERE ModuleNo = 1 AND ScreenNo = 11;
