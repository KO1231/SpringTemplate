CREATE USER 'ci_user'@'localhost' IDENTIFIED BY 'ci_user_pass';
DROP DATABASE IF EXISTS ci; CREATE DATABASE ci;

GRANT LOCK TABLES ON `ci`.* TO `ci_user`@`localhost`;
GRANT SELECT, INSERT, UPDATE, CREATE, REFERENCES, ALTER ON `ci`.`flyway_schema_history` TO `ci_user`@`localhost`;

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, REFERENCES, ALTER ON `ci`.`user` TO `ci_user`@`localhost`;
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, REFERENCES, ALTER ON `ci`.`user_authority` TO `ci_user`@`localhost`;
