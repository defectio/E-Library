CREATE TABLE tbl_admin_member
(
	a_m_no 				NUMBER(8),
	a_m_approval 		NUMBER(8)			DEFAULT 0 NOT NULL,
	a_m_id 				VARCHAR2(32) 		NOT NULL,
	a_m_pw				VARCHAR2(100)		NOT NULL,
	a_m_name			VARCHAR(100)		NOT NULL,
	a_m_gender			CHAR(1)				NOT NULL,
	a_m_part			VARCHAR2(100)		NOT NULL,
	a_m_position		VARCHAR2(100)		NOT NULL,
	a_m_mail			VARCHAR2(100)		NOT NULL,
	a_m_phone			VARCHAR2(100)		NOT NULL,
	a_m_reg_date		DATE,
	a_m_mod_date		DATE
);


ALTER TABLE TBL_ADMIN_MEMBER ADD(
	CONSTRAINT XPK_ADMIN_MEMBER PRIMARY KEY (a_m_no));

CREATE SEQUENCE SQ_ADMIN_MEMBER INCREMENT BY 1 START WITH 1;