CREATE TABLE UserSessions(
    userId int,
    macAddress varchar(255),
    isActive boolean,
    startDate date,
    lastLoginDate date,
    endDate date null,
    PRIMARY KEY(userId, macAddress)
);