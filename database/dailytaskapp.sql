CREATE DATABASE IF NOT EXISTS DailyTaskApp;
use DailyTaskApp;

CREATE TABLE IF NOT EXISTS users(
    user_id int not null AUTO_INCREMENT,
    username varchar(20) NOT NULL,
    password varchar(20) NOT NULL,
    PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS tasks(
    task_id int not null AUTO_INCREMENT,
    user_id int not null,
    task_name varchar(40) NOT NULL,
    task_daily_count int not null,
    task_complete_count int not null,
    task_rest_bonus int not null,
    task_schedule varchar(30) NOT NULL,
    PRIMARY KEY(task_id)
);

