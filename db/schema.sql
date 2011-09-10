create table if not exists `users` (
	`id` integer primary key autoincrement, 
  `login` varchar(255) unique not null,
  `password` varchar(255) not null,
	`name` varchar(255),
	`email` varchar(255),
	`admin` integer(1) default 0 not null
);

create table if not exists `projects` (
	`id` integer primary key autoincrement, 
  `name` varchar(255) unique not null,
  `description` text not null
);

create table if not exists `project_assignments` (
  `project_id` integer not null,
  `user_id` integer not null,
  unique (`project_id`, `user_id`)
);

create table if not exists `tasks` (
  `id` integer primary key autoincrement,
  `project_id` integer not null,
  `name` varchar(255) unique not null,
  `description` text not null,
  `iteration_id` integer,
  `time_est` integer,
  `done` integer(1) default 0 not null
);

create table if not exists `iterations` (
  `id` integer primary key autoincrement,
  `project_id` integer not null,
  `name` varchar(255) unique not null,
  `description` text not null,
  `start_date` integer not null,
  `end_date` integer not null
);