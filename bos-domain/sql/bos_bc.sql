/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017-6-1 20:32:30                            */
/*==============================================================*/

DROP TABLE IF EXISTS bc_subarea;
DROP TABLE IF EXISTS bc_decidedzone;

DROP TABLE IF EXISTS bc_region;

DROP TABLE IF EXISTS bc_staff;



/*==============================================================*/
/* Table: bc_decidedzone                                        */
/*==============================================================*/
CREATE TABLE bc_decidedzone
(
   id                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(30),
   staff_id             INT,
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Table: bc_region                                             */
/*==============================================================*/
CREATE TABLE bc_region
(
   id                   INT NOT NULL AUTO_INCREMENT,
   province             VARCHAR(50),
   city                 VARCHAR(50),
   district             VARCHAR(50),
   postcode             VARCHAR(50),
   shortcode            VARCHAR(50),
   citycode             VARCHAR(50),
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Table: bc_staff                                              */
/*==============================================================*/
CREATE TABLE bc_staff
(
   id                   INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(20),
   telephone            VARCHAR(20),
   haspda               CHAR(1),
   deltag               CHAR(1),
   station              VARCHAR(40),
   standard             VARCHAR(100),
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Table: bc_subarea                                            */
/*==============================================================*/
CREATE TABLE bc_subarea
(
   id                   INT NOT NULL AUTO_INCREMENT,
   decidedzone_id       INT,
   region_id            INT,
   addresskey           VARCHAR(100),
   startnum             VARCHAR(30),
   endnum               VARCHAR(30),
   single               CHAR(1),
   POSITION             VARCHAR(255),
   PRIMARY KEY (id)
);

ALTER TABLE bc_decidedzone ADD CONSTRAINT FK_decidedzone_staff FOREIGN KEY (staff_id)
      REFERENCES bc_staff (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE bc_subarea ADD CONSTRAINT FK_area_decidedzone FOREIGN KEY (decidedzone_id)
      REFERENCES bc_decidedzone (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE bc_subarea ADD CONSTRAINT FK_area_region FOREIGN KEY (region_id)
      REFERENCES bc_region (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE bc_decidedzone AUTO_INCREMENT=100000;
ALTER TABLE bc_subarea AUTO_INCREMENT=100000;
#alter table bc_region AUTO_INCREMENT=10000;
ALTER TABLE bc_staff AUTO_INCREMENT=100000;