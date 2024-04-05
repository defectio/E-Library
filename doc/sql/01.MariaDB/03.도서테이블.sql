CREATE TABLE tbl_book(
	b_no			INT	AUTO_INCREMENT,
	b_thumbnail		VARCHAR(100),
	b_name			VARCHAR(30)	NOT NULL, 	
	b_author		VARCHAR(20)	NOT NULL, 	
	b_publisher		VARCHAR(20)	NOT NULL,
	b_publish_year		CHAR(4)		NOT NULL, 	
	b_isbn			VARCHAR(30)	NOT NULL, 
	b_call_number		VARCHAR(30)	NOT NULL, 
	b_rental_able		TINYINT	NOT NULL DEFAULT 1, 
	b_reg_date		DATETIME,
	b_mod_date		DATETIME,
	PRIMARY KEY(b_no)
	);
