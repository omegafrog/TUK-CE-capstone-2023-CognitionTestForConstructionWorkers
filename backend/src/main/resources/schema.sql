CREATE TABLE field (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
   name VARCHAR(255) NULL,
   num_of_workers INT NOT NULL,
   CONSTRAINT pk_field PRIMARY KEY (id)
);

CREATE TABLE subject (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
   name VARCHAR(255) NULL,
   username VARCHAR(255) NULL,
   password VARCHAR(640) NULL,
   `role` INT NULL,
   age INT  NULL,
   detailed_job INT NULL,
   career INT NULL,
   remarks VARCHAR(255) NULL,
   risk INT NULL,
   field_id BIGINT NULL,
   CONSTRAINT pk_subject PRIMARY KEY (id)
);

ALTER TABLE subject ADD CONSTRAINT FK_SUBJECT_ON_FIELD FOREIGN KEY (field_id) REFERENCES field (id);


CREATE TABLE `admin` (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
   name VARCHAR(255) NULL,
   username VARCHAR(255) NULL,
   password VARCHAR(640) NULL,
   `role` INT NULL,
   position VARCHAR(255) NULL,
   field_id BIGINT NULL,
   CONSTRAINT pk_admin PRIMARY KEY (id)
);

ALTER TABLE `admin` ADD CONSTRAINT FK_ADMIN_ON_FIELD FOREIGN KEY (field_id) REFERENCES field (id);

CREATE TABLE test_result (
   id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
   target_id BIGINT NULL,
   `date` DATETIME NULL,
   total_mean_duration DOUBLE NULL,
   total_mean_error DOUBLE NULL,
   meanrt DOUBLE NULL,
   mean1divrt DOUBLE NULL,
   crane_response_time DOUBLE NULL,
   meanlt DOUBLE NULL,
   meanft DOUBLE NULL,
   tova_response_time DOUBLE NULL,
   CONSTRAINT pk_testresult PRIMARY KEY (id)
);

ALTER TABLE test_result ADD CONSTRAINT FK_TESTRESULT_ON_SUBJECT FOREIGN KEY (target_id) REFERENCES subject (id);