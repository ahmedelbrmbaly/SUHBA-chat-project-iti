create table Users(
    userId int primary key auto_increment,
    phone varchar(255) unique not null,
    displayName varchar(255) not null,
    userEmail varchar(255) unique null,
    picture blob null,
    password varchar(255) not null,
    gender enum ("Male", "Female") null,
    country varchar(255) null,
    birthday date null,
    bio varchar(1000) null,
    userStatus enum("Offline", "Available", "Busy", "Away") null
);