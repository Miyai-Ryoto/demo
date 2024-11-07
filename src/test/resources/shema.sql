CREATE DATABASE IF NOT EXISTS user_list
    CHARACTER SET utf8
    COLLATE utf8_unicode_ci;

USE user_list;

CREATE TABLE IF NOT EXISTS department_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
);

CREATE TABLE IF NOT EXISTS user_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    password VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    department_id BIGINT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department_table(id)
);

CREATE TABLE IF NOT EXISTS request_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    content VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    event_date DATE NOT NULL,
    file_name VARCHAR(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    file_data BLOB DEFAULT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_table(id)
);

CREATE TABLE IF NOT EXISTS task_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    user_id BIGINT DEFAULT NULL,
    task_condition TINYINT(1) DEFAULT 0,
    FOREIGN KEY(request_id) REFERENCES request_table(id),
    FOREIGN KEY(department_id) REFERENCES department_table(id),
    FOREIGN KEY(user_id) REFERENCES user_table(id)
);

CREATE TABLE IF NOT EXISTS answer_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    file_name VARCHAR(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
    file_data BLOB DEFAULT NULL,
    request_id BIGINT NOT NULL,
    user_id BIGINT DEFAULT NULL,
    FOREIGN KEY(request_id) REFERENCES request_table(id),
    FOREIGN KEY(user_id) REFERENCES user_table(id)
);

CREATE TABLE IF NOT EXISTS responsible_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT DEFAULT NULL,
    task_id BIGINT DEFAULT NULL,
    FOREIGN KEY(user_id) REFERENCES user_table(id),
    FOREIGN KEY(task_id) REFERENCES task_table(id)
);