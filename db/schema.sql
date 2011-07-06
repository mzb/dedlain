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