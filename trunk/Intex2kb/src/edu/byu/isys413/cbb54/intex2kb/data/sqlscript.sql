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
    "backupSize" VARCHAR(15),
    "backupExpDate" VARCHAR(40)
);

create table "backupservice"
(
    "price" double not null primary key
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
    "amount" double,
    "ccnumber" varchar(20),
    "ccexpiration" varchar(10),
    "transactionid" varchar(80),
    "change" double,
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
    "salary" double,
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
    "rstype" varchar(40)
);
 
create table "coupon"
(
    "id" varchar(40) not null primary key,
    "amount" double
);

create table "revenuesource"
(
    "id" varchar(40) not null primary key,
    "type" varchar(80)
);

create table "backup"
(
    "id" varchar(40) not null primary key,
    "size" double,
    "lengthofbackup" double,
    "price" double
);

create table "serviceRepair" 
(
    "id" varchar(40) not null primary key,
    "dateStarted" bigint,
    "dateEnded" bigint,
    "description" varchar(150),
    "laborHours" double,
    "employeeID" varchar(30),
    "datePickedUp" bigint,
    "price" double
);

create table "printorder"
(
    "id" varchar(40) not null primary key,
    "poid" varchar(40),
    "quantity" int,
    "photoSet" varchar(30),
    "printformat" varchar(30),
    "price" double
);

create table "printformat"
(
    "id" varchar(30) not null primary key,
    "size" varchar(30),
    "papertype" varchar(30),
    "sourcetype" varchar(30),
    "price" double
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
    "price" double
);

create table "conversiontype"
(
    "id" varchar(30) not null primary key,
    "sourcetype" varchar(30),
    "destinationtype" varchar(30),
    "price" double
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
    "avgCost" double
);

create table "conceptualrental"
(
    "id" varchar(40) not null primary key,
    "price" double,
    "cost" double,
    "late" double,
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
    "quantity" int,
    "productType" varchar(30)
);

create table "login"
(
    "email" varchar(100) not null primary key,
    "password" varchar(100)
);

INSERT INTO "backupservice" VALUES (.99);
INSERT INTO "customer" VALUES ('00000109123b9144eb018b64001000', 'Cameron', 'Burgon', '851 Wymount Terrace', '', 'Provo', 'Utah', '84602', '8013786198', 'wherever@sd.com');
INSERT INTO "customer" VALUES ('00000109123b925d16688bc2001000', 'Jamie', 'Burgon', '851 Wymount Terrace', '', 'Provo', 'Utah', '84604', '8013689248', 'wherever@sd.com');
INSERT INTO "customer" VALUES ('10000109123b925d16688bc2001000', 'Bryan', 'Schader', '267 E. 500 N. #71', '', 'Provo', 'Utah', '84606', '4089529043', 'bryan@trueramerica.org');
INSERT INTO "customer" VALUES ('00000109123b8e75a1ce5a33001000', 'John', 'Doe', '123 Somewhere', '', 'Nowhere', 'Nevada', '87839', '3418394854', 'wherever@sd.com');
INSERT INTO "customer" VALUES ('00000109123b8e75a1ce5a34001000', 'Jane', 'Doe', '123 Somewhere', '', 'Nowhere', 'Nevada', '87839', '3418394854', 'wherever@sd.com');
INSERT INTO "membership" VALUES ('00000109123b8e75a1ce5a34001000', '00000109123b8e75a1ce5a33001000', '23123', '12313', '123132', '12311175098508472',21, '123123', '123123');
INSERT INTO "memberinterests" VALUES('00000109123b925d16688bc2001000','0000011105caf55500f1fec0a80204');

INSERT INTO "transactionline" VALUES('00000111728454ec00e60e0a500442','1234','000001117284553c0014af0a500442', 'backup');

INSERT INTO "transaction" VALUES('000001117284553c0014af0a500442','','complete','00000109123b9144eb018b64001000','000001117284553c0014b10a500442','000001117284553c0014b20a500442','');

INSERT INTO "payment" VALUES('000001117284553c0014b00a500442',14.50,'1234-5678-9012-3456','06/07','000001117284553c0014af0a500442',0.0,'Credit Card');

INSERT INTO "employee" VALUES('000001117284553c0014b10a500442','Ray','Thompson','123 East BYU','','Provo','Utah','84604','801-555-5555','thompson@thompson.com','123-45-6789','12/05',123.45,'000001117284553c0014b20a500442');
INSERT INTO "employee" VALUES('000001117284553c0014b30a500442','Steve','Manager','123 East BYU','','Provo','Utah','84604','801-555-5555','tylerf@byu.edu','123-45-6789','12/05',123.45,'000001117284553c0014b20a500442');
INSERT INTO "employee" VALUES('100001117284553c0014b30a500442','Steve','Director','123 East BYU','','Provo','Utah','84604','801-555-5555','bryan@trueramerica.org','123-45-6789','12/05',123.45,'000001117284553c0014b20a500442');

INSERT INTO "login" VALUES('tylerf@byu.edu','tyler');
INSERT INTO "login" VALUES('bryan@trueramerica.org','bryan');


INSERT INTO "store" VALUES('000001117284553c0014b20a500442','Provo Center','123 Center Street','Suite 1','Provo','Utah','84604','108-333-3333','801-333-3334','000001117284553c0014b30a500442');
INSERT INTO "store" VALUES('111','Provo','111 N 111 W','','Provo','UT','84601','108-333-3333','801-333-3334','000001117284553c0014b30a500442');

INSERT INTO "coupon" VALUES('000001117284553c0014b40a500442',2.00);

INSERT INTO "revenuesource" VALUES('000001117284553c0014b50a500442','Sale');

INSERT INTO "backup" VALUES('1234', 1, 100, .89);

INSERT INTO "rental" VALUES('3245643212342', 4321789, 432143);

INSERT INTO "rentalreturn" VALUES('4432435432543', '3245643212342', 432193);

INSERT INTO "physical" VALUES('321423421', '543254', 'shelf 31', '7432182347891234', 1);
INSERT INTO "physical" VALUES('54322345', '5543256543565', 'shelf 32', '1234waf3q4wrea', 0);
INSERT INTO "physical" VALUES('6543643', '5543256543566', 'shelf 32', '8okij7u6hy', 0);
INSERT INTO "physical" VALUES('5jy4354wet', '5543256543567', 'shelf 32', '1234waf3q4wrea', 0);
INSERT INTO "physical" VALUES('fds890h4oqt', '5543256543568', 'shelf 32', '214ra3q4wrae', 0);
INSERT INTO "physical" VALUES('tgfdsgrewgfdgwe', '5543256543569', 'shelf 32', '6jhy53gtryjmun', 0);
INSERT INTO "physical" VALUES('asdfasdfw4s4', '5543256543570', 'shelf 32', '8okij7u6hy', 0);

INSERT INTO "conceptualrental" VALUES('1234waf3q4wrea', 29.99, 249.99, 14.99, 5, 10);
INSERT INTO "conceptualrental" VALUES('214ra3q4wrae', 19.99, 229.99, 9.99, 5, 10);
INSERT INTO "conceptualrental" VALUES('6jhy53gtryjmun', 9.99, 49.99, 4.99, 5, 10);
INSERT INTO "conceptualrental" VALUES('8okij7u6hy', 99.99, 11249.99, 44.99, 5, 10);

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
