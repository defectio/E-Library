
-- 스키마(데이터베이스) 생성
CREATE schema `elibrary` DEFAULT CHARACTER SET utf8mb4  DEFAULT COLLATE utf8mb4_0900_ai_ci;

-- 계정 생성
CREATE USER 'defectio'@'%' IDENTIFIED BY 'defectio!23';

-- 스키마(데이터베이스)의 사용자에게 모든 권한 부여
GRANT ALL PRIVILEGES ON elibrary.* TO 'defectio'@'%';

-- deprecated
-- SET GLOBAL log_bin_trust_function_creators = ON; 

-- 권한 적용
flush privileges;



-- 생성된 스키마 목록 조회
show databases;

-- 계정 목록 조회
select user, host from mysql.user;