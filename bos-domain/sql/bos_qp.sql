/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/6/12 21:59:19                           */
/*==============================================================*/

DROP TABLE IF EXISTS qp_workordermanage;
DROP TABLE IF EXISTS qp_workbill;
DROP TABLE IF EXISTS qp_noticebill;

/*==============================================================*/
/* Table: qp_noticebill                                         */
/*==============================================================*/
CREATE TABLE qp_noticebill
(
   id                   INT(11) NOT NULL AUTO_INCREMENT,
   staff_id             INT(11),
   customer_id          INT(11),
   customer_name        VARCHAR(20),
   delegater            VARCHAR(20),
   telephone            VARCHAR(20),
   pickaddress          VARCHAR(200),
   arrivecity           VARCHAR(20),
   product              VARCHAR(20),
   pickdate             DATE,
   num                  INT,
   weight               DOUBLE,
   volume               VARCHAR(20),
   remark               VARCHAR(255),
   ordertype            VARCHAR(20),
   user_id              VARCHAR(32),
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Table: qp_workbill                                           */
/*==============================================================*/
CREATE TABLE qp_workbill
(
   id                   INT(11) NOT NULL AUTO_INCREMENT,
   noticebill_id        INT(11),
   TYPE                 VARCHAR(20),
   pickstate            VARCHAR(20),
   buildtime            TIMESTAMP,
   attachbilltimes      INT(11),
   remark               VARCHAR(255),
   staff_id             INT(11),
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Table: qp_workordermanage                                    */
/*==============================================================*/
CREATE TABLE qp_workordermanage
(
   id                   INT(11) NOT NULL AUTO_INCREMENT,
   arrivecity           VARCHAR(20),
   product              VARCHAR(20),
   num                  INT,
   weight               DOUBLE,
   floadreqr            VARCHAR(255),
   prodtimelimit        VARCHAR(40),
   prodtype             VARCHAR(40),
   sendername           VARCHAR(20),
   senderphone          VARCHAR(20),
   senderaddr           VARCHAR(200),
   receivername         VARCHAR(20),
   receiverphone        VARCHAR(20),
   receiveraddr         VARCHAR(200),
   feeitemnum           INT,
   actlweit             DOUBLE,
   vol                  VARCHAR(20),
   managerCheck         VARCHAR(1),
   updatetime           DATE,
   PRIMARY KEY (id)
);

ALTER TABLE qp_noticebill ADD CONSTRAINT FK_Reference_2 FOREIGN KEY (user_id)
      REFERENCES t_user(id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE qp_noticebill ADD CONSTRAINT FK_Reference_3 FOREIGN KEY (staff_id)
      REFERENCES bc_staff (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE qp_workbill ADD CONSTRAINT FK_Reference_4 FOREIGN KEY (staff_id)
      REFERENCES bc_staff (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE qp_workbill ADD CONSTRAINT FK_workbill_noticebill_fk FOREIGN KEY (noticebill_id)
      REFERENCES qp_noticebill (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE qp_workordermanage AUTO_INCREMENT=10000001;
ALTER TABLE qp_workbill AUTO_INCREMENT=10000001;
ALTER TABLE qp_noticebill AUTO_INCREMENT=10000001;