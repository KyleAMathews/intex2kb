--
-- Clear database
--

DROP TABLE "customer";
DROP TABLE "membership";
DROP TABLE "interest";
DROP TABLE "backupservice";
DROP TABLE "memberinterests";
DROP TABLE "transaction";
DROP TABLE "payment";
DROP TABLE "employee";
DROP TABLE "store";
DROP TABLE "transactionline";
DROP TABLE "coupon";
DROP TABLE "revenuesource";
DROP TABLE "backup";
DROP TABLE "serviceRepair";
DROP TABLE "rental";
DROP TABLE "photoset";
DROP TABLE "printformat";
DROP TABLE "printorder";
DROP TABLE "sale";
DROP TABLE "physical";
DROP TABLE "forrent";
DROP TABLE "forsale";
DROP TABLE "conversionorder";
DROP TABLE "conversiontype";
DROP TABLE "conceptual";
DROP TABLE "product";
DROP TABLE "rentalreturn";
DROP TABLE "conceptualrental";
DROP TABLE "login";
DROP TABLE "category";
DROP TABLE "vendor";
DROP TABLE "vendoritem";
DROP TABLE "purchaseorder";
DROP TABLE "orderline";
DROP TABLE "storeproduct";
DROP TABLE "photoBackup";

create table "customer"
(
    "id" VARCHAR(40) not null primary key,
    "fname" VARCHAR(40),
    "lname" VARCHAR(40),
    "address1" VARCHAR(80),
    "address2" VARCHAR(80),
    "city" VARCHAR(40),
    "state" VARCHAR(40),
    "zip" VARCHAR(10),
    "phone" VARCHAR(15),
    "email" VARCHAR(40)
);

create table "membership"
(
    "id" VARCHAR(40) not null primary key,
    "custID" VARCHAR(40),
    "startDate" VARCHAR(30),
    "endDate" VARCHAR(30),
    "creditCard" VARCHAR(16),
    "ccExpiration" VARCHAR(45),
    "newsletter" SMALLINT,
    "backupSize" float,
    "backupExpDate" bigint
);

create table "backupservice"
(
    "price" FLOAT primary key
);

create table "interest"
(
    "id" VARCHAR(40) not null primary key,
    "title" VARCHAR(128),
    "description" VARCHAR(256)
);

create table "memberinterests"
(
    "memberID" VARCHAR(40),
    "interestID" VARCHAR(40)
);

create table "transaction"
(
    "id" varchar(40) not null primary key,
    "type" varchar(80),
    "status" varchar(10),
    "custid" varchar(80),
    "empid" varchar(80),
    "storeid" varchar(80),
    "origtx" varchar(40)
);
 
create table "payment"
(
    "id" varchar(40) not null primary key,
    "amount" FLOAT,
    "ccnumber" varchar(20),
    "ccexpiration" varchar(10),
    "transactionid" varchar(80),
    "change" FLOAT,
    "type" varchar(80)
);
 
create table "employee"
(
    "id" varchar(40) not null primary key,
    "fname" varchar (80),
    "lname" varchar(80),
    "address1" varchar(80),
    "address2" varchar(80),
    "city" varchar(80),
    "state" varchar(80),
    "zip" varchar(80),
    "phone" varchar(15),
    "email" varchar(80),
    "ssnumber" varchar(15),
    "hiredate" varchar(80),
    "salary" FLOAT,
    "storeid" varchar(40)
);
 
create table "store"
(
    "id" varchar(40) not null primary key,
    "name" varchar(80),
    "address1" varchar(80),
    "address2" varchar(80),
    "city" varchar(80),
    "state" varchar(80),
    "zip" varchar(80),
    "phone" varchar(15),
    "fax" varchar(15),
    "managerid" varchar(40)
);
 
create table "transactionline"
(
    "id" varchar(40) not null primary key,
    "revenuesourceid" varchar(40),
    "transactionid" varchar(40),
    "rstype" varchar(50)
);
 
create table "coupon"
(
    "id" varchar(40) not null primary key,
    "amount" FLOAT
);

create table "revenuesource"
(
    "id" varchar(40) not null primary key,
    "type" varchar(80)
);

create table "backup"
(
    "id" varchar(40) not null primary key,
    "size" FLOAT,
    "lengthofbackup" FLOAT,
    "price" FLOAT
);

create table "serviceRepair" 
(
    "id" varchar(40) not null primary key,
    "dateStarted" bigint,
    "dateEnded" bigint,
    "description" varchar(150),
    "laborHours" FLOAT,
    "employeeID" varchar(30),
    "datePickedUp" bigint,
    "price" FLOAT
);

create table "printorder"
(
    "id" varchar(40) not null primary key,
    "poid" varchar(40),
    "quantity" int,
    "photoSet" varchar(30),
    "printformat" varchar(30),
    "price" FLOAT
);

create table "printformat"
(
    "id" varchar(30) not null primary key,
    "size" varchar(30),
    "papertype" varchar(30),
    "sourcetype" varchar(30),
    "price" FLOAT
);

create table "photoset"
(
    "id" varchar(30) not null primary key,
    "description" varchar(100),
    "numPhotos" int
);

create table "conversionorder"
(
    "id" varchar(40) not null primary key,
    "conversiontype" varchar(30),
    "quantity" int,
    "price" FLOAT
);

create table "conversiontype"
(
    "id" varchar(30) not null primary key,
    "sourcetype" varchar(30),
    "destinationtype" varchar(30),
    "price" FLOAT
);

create table "sale"
(
    "id" varchar(40) not null primary key,
    "quantity" int,
    "productid" varchar(40),
    "producttype" varchar(30)
);

create table "rental"
(
    "id" varchar(40) not null primary key,
    "datedue" bigint,
    "dateout" bigint

);

create table "rentalreturn"
(
    "id" varchar(40) not null primary key,
    "rentalid" varchar(40),
    "datein" bigint

);

create table "physical"
(
    "id" varchar(40) not null primary key,
    "serialnum" varchar(40),
    "shelflocation" varchar(40),
    "conceptualproduct" varchar(40),
    "forsale" smallint
    
);

create table "conceptual"
(
    "id" varchar(40) not null primary key,
    "name" varchar(60),
    "description" varchar(120),
    "avgCost" FLOAT,
    "categoryID" varchar(40)
);

create table "conceptualrental"
(
    "id" varchar(40) not null primary key,
    "price" FLOAT,
    "cost" FLOAT,
    "late" FLOAT,
    "maxrent" int,
    "maxlate" int
);

create table "forrent"
(
    "id" varchar(40) not null primary key,
    "timesrented" int,
    "currentrental" varchar(40)
);

create table "forsale"
(
    "id" varchar(40) not null primary key,
    "isnew" smallint
);

create table "product"
(
    "id" varchar(40) not null primary key,
    "price" float
);

create table "login"
(
    "email" varchar(100) not null primary key,
    "password" varchar(100),
    "membid" varchar(40)
);

create table "category"
(
    "id" varchar(40) not null primary key,
    "name" varchar(40)
);

create table "vendor"
(
    "id" varchar(40) not null primary key,
    "name" varchar(60),
    "address" varchar(80),
    "phone" varchar(20),
    "contact" varchar(60)
);

create table "vendoritem"
(
    "vendorid" varchar(40) not null,
    "productid" varchar(40) not null,
    "cost" FLOAT
);

create table "purchaseorder"
(
    "id" varchar(40) not null primary key,
    "date" bigint,
    "vendorid" varchar(40),
    "storeid" varchar(40)
);

create table "orderline"
(
    "id" varchar(40) not null primary key,
    "purchaseorderid" varchar(40),
    "productid" varchar(40),
    "quantity" int
);

create table "storeproduct"
(
    "storeid" varchar(40) not null,
    "productid" varchar(40) not null,
    "quantityonhand" int,
    "location" varchar(100),
    "reorderpoint" int,
    "quantitytoorder" int,
    "quantityonorder" int
);
create table "photoBackup"
(
    "id" varchar(40) not null primary key,
    "membid" varchar(40),
    "caption" varchar(60),
    "thumbnail" varbinary(max),
    "mediumpic" varbinary(max),
    "originalpic" varbinary(max),
    "filename" varchar(500),
    "filetype" varchar(100),
    "filesize" varchar(100),
    "status" varchar(2)
);

INSERT INTO "backupservice" VALUES (.99);

INSERT INTO "category" VALUES ('00000110123b9144eb018b64001000', 'digital camera');
INSERT INTO "category" VALUES ('00000111123b9144eb018b64001000', 'digital video camera');
INSERT INTO "category" VALUES ('00000112123b9144eb018b64001000', 'misc equipment');
INSERT INTO "category" VALUES ('00000113123b9144eb018b64001000', 'lighting equipment');
INSERT INTO "category" VALUES ('00000114123b9144eb018b64001000', 'Blank Media');
INSERT INTO "category" VALUES ('00000115123b9144eb018b64001000', 'Film');



INSERT INTO "customer" VALUES ('00000109123b9144eb018b64001000', 'Cameron', 'Burgon', '851 Wymount Terrace', '', 'Provo', 'Utah', '84602', '8013786198', 'tylerfarmer@gmail.com');
INSERT INTO "customer" VALUES ('00000109123b925d16688bc2001000', 'Jamie', 'Burgon', '851 Wymount Terrace', '', 'Provo', 'Utah', '84604', '8013689248', 'wherever@sd.com');
INSERT INTO "customer" VALUES ('10000109123b925d16688bc2001000', 'Bryan', 'Schader', '267 E. 500 N. #71', '', 'Provo', 'Utah', '84606', '4089529043', 'bryan@trueramerica.org');
INSERT INTO "customer" VALUES ('00000109123b8e75a1ce5a33001000', 'Kyle', 'Mathews', '123 Somewhere', '', 'Nowhere', 'Nevada', '87839', '3418394854', 'wherever@sd.com');
INSERT INTO "customer" VALUES ('00000109123b8e75a1ce5a34001000', 'Jane', 'Doe', '123 Somewhere', '', 'Nowhere', 'Nevada', '87839', '3418394854', 'wherever@sd.com');
INSERT INTO "customer" VALUES ('00000111d80891e0015d170a04449d', 'Tyler', 'Farmer', '123 Somewhere', '', 'Nowhere', 'Nevada', '87839', '3418394854', 'tylerfarmer@gmail.com');

INSERT INTO "membership" VALUES ('tylermembid', '00000111d80891e0015d170a04449d', '23123', '12313', '123132', '12311175098508472',21, 1.0, 123123);
INSERT INTO "membership" VALUES ('bryanmembid', '10000109123b925d16688bc2001000', '23123', '12313', '123132', '12311175098508472',21, 13.0, 123123);
INSERT INTO "membership" VALUES ('cameronmembid', '00000109123b9144eb018b64001000', '23123', '12313', '123132', '12311175098508472',21, 23.0, 123123);
INSERT INTO "membership" VALUES ('kylemembid', '00000109123b8e75a1ce5a33001000', '23123', '12313', '123132', '12311175098508472',21, 12.0, 23123);
INSERT INTO "memberinterests" VALUES('00000109123b925d16688bc2001000','0000011105caf55500f1fec0a80204');

INSERT INTO "transactionline" VALUES('00000111728454ec00e60e0a500442','1234','000001117284553c0014af0a500442', 'backup');

INSERT INTO "transaction" VALUES('000001117284553c0014af0a500442','','complete','00000109123b9144eb018b64001000','000001117284553c0014b10a500442','000001117284553c0014b20a500442','na');

INSERT INTO "payment" VALUES('000001117284553c0014b00a500442',14.50,'1234-5678-9012-3456','06/07','000001117284553c0014af0a500442',0.0,'Credit Card');

INSERT INTO "employee" VALUES('000001117284553c0014b10a500442','Ray','Thompson','123 East BYU','','Provo','Utah','84604','801-555-5555','c@b.com','123-45-6789','12/05',123.45,'000001117284553c0014b20a500442');
INSERT INTO "employee" VALUES('000001117284553c0014b30a500442','Steve','Manager','123 East BYU','','Provo','Utah','84604','801-555-5555','tylerf@byu.edu','123-45-6789','12/05',123.45,'000001117284553c0014b20a500442');
INSERT INTO "employee" VALUES('100001117284553c0014b30a500442','Steve','Director','123 East BYU','','Provo','Utah','84604','801-555-5555','bryan@trueramerica.org','123-45-6789','12/05',123.45,'000001117284553c0014b20a500442');
INSERT INTO "employee" VALUES('120001117284553c0014b60a500442','Online','','','','','','','','','','',0.0,'');

INSERT INTO "login" VALUES('tylerf@byu.edu','tyler', 'tylermembid');
INSERT INTO "login" VALUES('bryan@trueramerica.org','bryan', 'bryanmembid');
INSERT INTO "login" VALUES('c@b.com','cb', 'cameronmembid');
INSERT INTO "login" VALUES('k', 'k', 'kylemembid');


INSERT INTO "store" VALUES('000001117284553c0014b20a500442','Provo','123 Center Street','Suite 1','Provo','Utah','84604','108-333-3333','801-333-3334','000001117284553c0014b30a500442');
INSERT INTO "store" VALUES('000001117284553c0014b20a500443','Logan','123 Center Street','Suite 1','Provo','Utah','84604','108-333-3333','801-333-3334','000001117284553c0014b30a500442');
INSERT INTO "store" VALUES('000001117284553c0014b20a500444','Murray','123 Center Street','Suite 1','Provo','Utah','84604','108-333-3333','801-333-3334','000001117284553c0014b30a500442');
INSERT INTO "store" VALUES('010001117284553c0014b20b500444','Online Store','','','','','','','','');

INSERT INTO "coupon" VALUES('000001117284553c0014b40a500442',2.00);

INSERT INTO "revenuesource" VALUES('000001117284553c0014b50a500442','Sale');

INSERT INTO "backup" VALUES('1234', 1, 100, .89);

INSERT INTO "rental" VALUES('3245643212342', 4321789, 432143);

INSERT INTO "rentalreturn" VALUES('4432435432543', '3245643212342', 432193);

INSERT INTO "physical" VALUES('321423421', '543254', 'shelf 31', 'aksfjl3krjlsk3j3ljk', 1);
INSERT INTO "product" VALUES('321423421',4.50);
INSERT INTO "physical" VALUES('54322345', '5543256543565', 'shelf 32', 'kkdsjl2k3lk3kjk3dkj', 0);
INSERT INTO "product" VALUES('54322345',2.25);
INSERT INTO "physical" VALUES('6543643', '5543256543566', 'shelf 32', 'skdfjlkjn23ndlkfjs3', 0);
INSERT INTO "product" VALUES('6543643',6.00);
INSERT INTO "physical" VALUES('5jy4354wet', '5543256543567', 'shelf 32', 'skldfjksdflkdker38f', 0);
INSERT INTO "product" VALUES('5jy4354wet',15.90);
INSERT INTO "physical" VALUES('fds890h4oqt', '5543256543568', 'shelf 32', 'lskdjfk32jfgkge8f7e', 0);
INSERT INTO "product" VALUES('fds890h4oqt',3.86);
INSERT INTO "physical" VALUES('tgfdsgrewgfdgwe', '5543256543569', 'shelf 32', 'skdfjlk32kgjk23k34k', 0);
INSERT INTO "product" VALUES('tgfdsgrewgfdgwe',4.65);
INSERT INTO "physical" VALUES('asdfasdfw4s4', '5543256543570', 'shelf 32', 'sdkfjlk4fg76f784398', 0);
INSERT INTO "product" VALUES('asdfasdfw4s4',6.45);

INSERT INTO "forrent" VALUES('54322345', 3, '3245643212342');
INSERT INTO "forrent" VALUES('6543643', 5, '');
INSERT INTO "forrent" VALUES('5jy4354wet', 4, '');
INSERT INTO "forrent" VALUES('fds890h4oqt', 5, '');
INSERT INTO "forrent" VALUES('tgfdsgrewgfdgwe', 5, '');
INSERT INTO "forrent" VALUES('asdfasdfw4s4', 2, '');

INSERT INTO "forsale" VALUES('321423421', 1);

INSERT INTO "serviceRepair" VALUES('1234', 123145124314, 124125123123, 'What a mess it was fixing that guys widget -- a nightmare', 10, '2342352342354234', 1241, 151.21); 

INSERT INTO "sale" VALUES('903945804395834958ui', 1, '321423421','p');

INSERT INTO "printorder" VALUES ('111','222',3,'111','111',.25);
INSERT INTO "printorder" VALUES ('222','333',5,'222','222',.5);

INSERT INTO "printformat" VALUES ('111','4x6','Matte','Film',.4);
INSERT INTO "printformat" VALUES ('222','4x6','Glossy','Film',.5);
INSERT INTO "printformat" VALUES ('333','4x6','Matte','Digital',.2);
INSERT INTO "printformat" VALUES ('444','4x6','Glossy','Digital',.3);
INSERT INTO "printformat" VALUES ('555','5x7','Matte','Film',.25);
INSERT INTO "printformat" VALUES ('666','5x7','Glossy','Film',.35);
INSERT INTO "printformat" VALUES ('777','5x7','Matte','Digital',.1);
INSERT INTO "printformat" VALUES ('888','5x7','Glossy','Digital',.2);

INSERT INTO "photoset" VALUES ('111','Test Photoset 1',4);
INSERT INTO "photoset" VALUES ('222','Test Photoset 2',6);

INSERT INTO "conversionorder" VALUES ('111','111',4,2.5);
INSERT INTO "conversionorder" VALUES ('222','222',60,6);

INSERT INTO "conversiontype" VALUES ('111','Film','CD',.18);
INSERT INTO "conversiontype" VALUES ('222','Film','DVD',.27);
INSERT INTO "conversiontype" VALUES ('333','VHS','CD',.35);
INSERT INTO "conversiontype" VALUES ('444','VHS','DVD',.45);

INSERT INTO "vendor" VALUES ('l32k452lkj42k4k23','Camera Vendor','123 Camera Street','234-545-5655','Rick');
INSERT INTO "vendor" VALUES ('k23djf45j4k543lk5','Film Vendor','123 Film Street','234-654-7686','Stan');
INSERT INTO "vendor" VALUES ('lkjldj4kk54jk4jk4','DVD Vendor','123 DVD Drive','236-543-5434','Dick');

INSERT INTO "conceptual" VALUES('aksfjl3krjlsk3j3ljk','ISO 100 BW Film - 36 Exp','The best film for you Black and White masterpieces',5.26,'00000115123b9144eb018b64001000');
INSERT INTO "product" VALUES('aksfjl3krjlsk3j3ljk',1.5);
INSERT INTO "conceptual" VALUES('kkdsjl2k3lk3kjk3dkj','ISO 64 BW Film - 36 Exp','The best film for you Black and White masterpieces',7.35,'00000115123b9144eb018b64001000');
INSERT INTO "product" VALUES('kkdsjl2k3lk3kjk3dkj',2.6);
INSERT INTO "conceptual" VALUES('skdfjlkjn23ndlkfjs3','Canon EOS 20D Digital Camera','',945.68,'00000110123b9144eb018b64001000');
INSERT INTO "product" VALUES('skdfjlkjn23ndlkfjs3',3.6);
INSERT INTO "conceptual" VALUES('lskdjfkn323k3k3nlkn','Canon PowerShot A350 Digital Camera','',95.63,'00000110123b9144eb018b64001000');
INSERT INTO "product" VALUES('lskdjfkn323k3k3nlkn',455.2);
INSERT INTO "conceptual" VALUES('skldfjksdflkdker38f','DVD+R DL Media - 100 Pack','Blank Dual Layer DVD Media',20.56,'00000114123b9144eb018b64001000');
INSERT INTO "product" VALUES('skldfjksdflkdker38f',23.4);
INSERT INTO "conceptual" VALUES('skdjf4f8g7dfgwk3rj3','DVD-R DL Media - 100 Pack','Blank Dual Layer DVD Media',23.43,'00000114123b9144eb018b64001000');
INSERT INTO "product" VALUES('skdjf4f8g7dfgwk3rj3',23.43);
INSERT INTO "conceptual" VALUES('lskdjfk32jfgkge8f7e','ISO 100 Color Film - 24 Exp','The best film for you Color masterpieces',4.75,'00000115123b9144eb018b64001000');
INSERT INTO "product" VALUES('lskdjfk32jfgkge8f7e',23.43);
INSERT INTO "conceptual" VALUES('skdfjlk32kgjk23k34k','ISO 300 Color film - 36 Exp','The best film for you Color masterpieces',5.98,'00000115123b9144eb018b64001000');
INSERT INTO "product" VALUES('skdfjlk32kgjk23k34k',23.43);
INSERT INTO "conceptual" VALUES('sdkfjlk4fg76f784398','DVD+R Media - 50 Pack','Blank DVD media',12.39,'00000114123b9144eb018b64001000');
INSERT INTO "product" VALUES('sdkfjlk4fg76f784398',54.32);

INSERT INTO "conceptualrental" VALUES('kkdsjl2k3lk3kjk3dkj', 29.99, 249.99, 14.99, 5, 10);
INSERT INTO "conceptualrental" VALUES('skldfjksdflkdker38f', 19.99, 229.99, 9.99, 5, 10);
INSERT INTO "conceptualrental" VALUES('lskdjfk32jfgkge8f7e', 9.99, 49.99, 4.99, 5, 10);
INSERT INTO "conceptualrental" VALUES('sdkfjlk4fg76f784398', 99.99, 11249.99, 44.99, 5, 10);
INSERT INTO "conceptualrental" VALUES('skdfjlkjn23ndlkfjs3', 19.99, 229.99, 9.99, 5, 10);
INSERT INTO "conceptualrental" VALUES('skdfjlk32kgjk23k34k', 19.99, 229.99, 9.99, 5, 10);


INSERT INTO "vendoritem" VALUES('l32k452lkj42k4k23','skdfjlkjn23ndlkfjs3',1000.00);
INSERT INTO "vendoritem" VALUES('l32k452lkj42k4k23','lskdjfkn323k3k3nlkn',90.00);
INSERT INTO "vendoritem" VALUES('k23djf45j4k543lk5','lskdjfk32jfgkge8f7e',4.50);
INSERT INTO "vendoritem" VALUES('k23djf45j4k543lk5','skdfjlk32kgjk23k34k',5.50);
INSERT INTO "vendoritem" VALUES('k23djf45j4k543lk5','aksfjl3krjlsk3j3ljk',5.15);
INSERT INTO "vendoritem" VALUES('k23djf45j4k543lk5','kkdsjl2k3lk3kjk3dkj',6.95);
INSERT INTO "vendoritem" VALUES('lkjldj4kk54jk4jk4','sdkfjlk4fg76f784398',11.45);
INSERT INTO "vendoritem" VALUES('lkjldj4kk54jk4jk4','skdjf4f8g7dfgwk3rj3',21.65);
INSERT INTO "vendoritem" VALUES('lkjldj4kk54jk4jk4','skldfjksdflkdker38f',20.25);

INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500442','aksfjl3krjlsk3j3ljk',50,'Film Rack',30,50,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500442','kkdsjl2k3lk3kjk3dkj',10,'Film Rack',15,50,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500442','skdfjlkjn23ndlkfjs3',5,'Camera Display',5,10,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500442','lskdjfkn323k3k3nlkn',13,'Point and Shoot Display',15,25,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500442','skldfjksdflkdker38f',21,'Blank Media',20,50,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500442','skdjf4f8g7dfgwk3rj3',32,'Blank Media',20,50,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500442','lskdjfk32jfgkge8f7e',43,'Film Rack',40,60,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500442','skdfjlk32kgjk23k34k',38,'Film Rack',35,50,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500442','sdkfjlk4fg76f784398',29,'Blank Media',30,50,50);

INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500443','skdjf4f8g7dfgwk3rj3',32,'Blank Media',20,50,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500443','lskdjfk32jfgkge8f7e',43,'Film Rack',40,60,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500443','skdfjlk32kgjk23k34k',38,'Film Rack',35,50,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500443','sdkfjlk4fg76f784398',29,'Blank Media',30,50,50);

INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500444','skdjf4f8g7dfgwk3rj3',32,'Blank Media',20,50,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500444','lskdjfk32jfgkge8f7e',43,'Film Rack',40,60,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500444','skdfjlk32kgjk23k34k',38,'Film Rack',35,50,0);
INSERT INTO "storeproduct" VALUES('000001117284553c0014b20a500444','sdkfjlk4fg76f784398',29,'Blank Media',30,50,50);
