CREATE TABLE IF NOT EXISTS tasks
(
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    description VARCHAR(255),
    deadline_date DATE,
    done  BOOLEAN,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(255),
    name VARCHAR(50),
    surname VARCHAR(50),
    email VARCHAR(70),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tasks_users (
    task_id int NOT NULL,
    user_id int NOT NULL,
    PRIMARY KEY (task_id, user_id),
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (user_id) REFERENCES users(id));

SET SQL_SAFE_UPDATES = 0;
DELETE FROM tasks_users;
DELETE FROM users;
DELETE FROM tasks;
SET SQL_SAFE_UPDATES = 1;



INSERT INTO users (id, username, password, name, surname, email)
VALUES (1, 'admin','21232f297a57a5a743894a0e4a801fc3','Matvey','Androsyuk','matveyandrosyuk2002@mail.ru'),
       (2, 'm', '6f8f57715090da2632453988d9a1501b', 'Ruslan', 'Pipot', 'ruslanpipot2002@mail.ru');

INSERT INTO tasks (id, title, description, deadline_date, done)
VALUES  (1, 'Spring Boot', 'Cоздать проект и внедрить в все нужные для него зависимости','2022-08-12', false),
        (2, 'Spring Security', 'Создать login страницу и страницу регистрации, добавить юзеров и роли','2022-08-13', false),
        (3, 'Spring MVC', 'Cоздать контроллеры для страниц логина и регистрации, связать все с базой данных','2022-08-22', false),
        (4, 'Thymeleaf', 'Добавить шаблонизатор для вывода данных из модели на html-страницу','2022-08-25', false),
        (5, 'Spring MVC', 'Cоздать контроллеры для страниц логина и регистрации, связать все с базой данных','2022-08-22', false);

INSERT INTO tasks_users(task_id,user_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 2),
       (5, 2);