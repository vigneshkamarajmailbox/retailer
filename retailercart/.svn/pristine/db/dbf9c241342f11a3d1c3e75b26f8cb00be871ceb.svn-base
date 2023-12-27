INSERT INTO screen(ModuleNo,ScreenNo,ModuleName,ScreenName,ScreenType,Sequence,UploadFlag,ModDt)
VALUES (1,10,'Landing','Change Password','T',10,'N',NOW());

INSERT INTO screenaccess
SELECT 'DEFAULT','DEFAULT',ModuleNo,ScreenNo,'Y','N',NOW()
FROM screen WHERE ModuleNo = 1 AND ScreenNo = 10;
