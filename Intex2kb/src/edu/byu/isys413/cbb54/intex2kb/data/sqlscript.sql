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
    "ccExpiration" VARCHAR(10),
    "newsletter" SMALLINT,
    "backupSize" VARCHAR(15),
    "backupExpDate" VARCHAR(40)
);

create table "backupservice"
(
    "price" VARCHAR(20) not null primary key
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
    "forsale" smallint
);

create table "conceptual"
(
    "id" varchar(40) not null primary key,
    "name" varchar(60),
    "description" varchar(120),
    "avgCost" double
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

INSERT INTO "customer" VALUES ('00000109123b9144eb018b64001000', 'Cameron', 'Burgon', '851 Wymount Terrace', '', 'Provo', 'Utah', '84602', '8013786198', 'wherever@sd.com');
INSERT INTO "customer" VALUES ('00000109123b925d16688bc2001000', 'Jamie', 'Burgon', '851 Wymount Terrace', '', 'Provo', 'Utah', '84604', '8013786189', 'wherever@sd.com');
INSERT INTO "customer" VALUES ('00000109123b8e75a1ce5a33001000', 'John', 'Doe', '123 Somewhere', '', 'Nowhere', 'Nevada', '87839', '3418394854', 'wherever@sd.com');
INSERT INTO "customer" VALUES ('00000109123b8e75a1ce5a34001000', 'Jane', 'Doe', '123 Somewhere', '', 'Nowhere', 'Nevada', '87839', '3418394854', 'wherever@sd.com');

INSERT INTO "memberinterests" VALUES('00000109123b925d16688bc2001000','0000011105caf55500f1fec0a80204');

INSERT INTO "transactionline" VALUES('00000111728454ec00e60e0a500442','1234','000001117284553c0014af0a500442', 'backup');

INSERT INTO "transaction" VALUES('000001117284553c0014af0a500442','','complete','00000109123b9144eb018b64001000','000001117284553c0014b10a500442','000001117284553c0014b20a500442','');

INSERT INTO "payment" VALUES('000001117284553c0014b00a500442',14.50,'1234-5678-9012-3456','06/07','000001117284553c0014af0a500442',0.0,'Credit Card');

INSERT INTO "employee" VALUES('000001117284553c0014b10a500442','Ray','Thompson','123 East BYU','','Provo','Utah','84604','801-555-5555','thompson@thompson.com','123-45-6789','12/05',123.45,'000001117284553c0014b20a500442');
INSERT INTO "employee" VALUES('000001117284553c0014b30a500442','Steve','Manager','123 East BYU','','Provo','Utah','84604','801-555-5555','thompson@thompson.com','123-45-6789','12/05',123.45,'000001117284553c0014b20a500442');

INSERT INTO "store" VALUES('000001117284553c0014b20a500442','Provo Center','123 Center Street','Suite 1','Provo','Utah','84604','108-333-3333','801-333-3334','000001117284553c0014b30a500442');

INSERT INTO "coupon" VALUES('000001117284553c0014b40a500442',2.00);

INSERT INTO "revenuesource" VALUES('000001117284553c0014b50a500442','Sale');

INSERT INTO "backup" VALUES('1234', 1, 100, .89);

INSERT INTO "rental" VALUES('3245643212342', 4321789, 432143);

INSERT INTO "rentalreturn" VALUES('4432435432543', '3245643212342', 432193);

INSERT INTO "physical" VALUES('321423421', '543254', 'shelf 31', 1);
INSERT INTO "physical" VALUES('54322345', '5543256543565', 'shelf 32', 0);

INSERT INTO "forrent" VALUES('54322345', 3, '3245643212342');

INSERT INTO "forsale" VALUES('321423421', 1);

INSERT INTO "serviceRepair" VALUES('1234', 123145124314, 124125123123, 'What a mess it was fixing that guys widget -- a nightmare', 10, '2342352342354234', 1241, 151.21); 

INSERT INTO "sale" VALUES('903945804395834958ui', 1, '321423421','p');
