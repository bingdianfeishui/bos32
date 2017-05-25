USE bos32;


/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user
(
   id                   VARCHAR(32) NOT NULL,
   username             VARCHAR(20) NOT NULL,
   `password`           VARCHAR(32) NOT NULL,
   salary               DOUBLE,
   birthday             DATE,
   gender               VARCHAR(10),
   station              VARCHAR(40),
   telephone            VARCHAR(11),
   remark               VARCHAR(255),
   PRIMARY KEY (id)
);
INSERT INTO t_user(id, username, password) VALUES('1', 'admin', md5('1234'));