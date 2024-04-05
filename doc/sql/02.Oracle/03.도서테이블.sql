CREATE TABLE tbl_book
(
	b_no			NUMBER(8),
	b_thumbnail		VARCHAR2(100),
	b_name			VARCHAR2(30)	NOT NULL, 	
	b_author		VARCHAR2(20)	NOT NULL, 	
	b_publisher		VARCHAR2(20)	NOT NULL,
	b_publish_year	CHAR(4)		NOT NULL, 	
	b_isbn			VARCHAR2(30)	NOT NULL, 
	b_call_number	VARCHAR2(30)	NOT NULL, 
	b_rental_able	NUMBER(1)	DEFAULT 1 NOT NULL, 
	b_reg_date		DATE,
	b_mod_date		DATE
);


ALTER TABLE TBL_BOOK ADD(
	CONSTRAINT XPK_BOOK PRIMARY KEY (b_no));

CREATE SEQUENCE SQ_BOOK INCREMENT BY 1 START WITH 1;