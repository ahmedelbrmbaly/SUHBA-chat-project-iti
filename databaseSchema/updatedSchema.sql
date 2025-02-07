CREATE TABLE Users(
    userId INT AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(255),
    displayName VARCHAR(255),
    userEmail VARCHAR(255),
    picture BLOB,
    password VARCHAR(255),
    gender ENUM('Male', 'Female'),
    country ENUM(
        'ALGERIA', 'AUSTRALIA', 'BANGLADESH', 'CANADA', 'EGYPT', 'FRANCE', 'GERMANY', 
        'INDONESIA', 'IRAN', 'IRAQ', 'JAPAN', 'JORDAN', 'KUWAIT', 'LEBANON', 'MALAYSIA', 
        'MOROCCO', 'OMAN', 'PAKISTAN', 'QATAR', 'SAUDI_ARABIA', 'SOUTH_KOREA', 'SUDAN', 
        'SWEDEN', 'SWITZERLAND', 'TUNISIA', 'TURKEY', 'UNITED_ARAB_EMIRATES', 
        'UNITED_KINGDOM', 'UNITED_STATES', 'YEMEN'
    ),
    birthday DATE,
    bio VARCHAR(1000),
    userStatus ENUM('Offline', 'Available', 'Busy', 'Away')
);


---------------------

CREATE TABLE UserSessions(
    userId int,
    macAddress varchar(255),
    isActive boolean,
    startDate date,
    lastLoginDate date,
    endDate date null,
    PRIMARY KEY(userId, macAddress)
);

----------------------

create table Chats(
    chatId int primary key auto_increment,
    chatType enum("Direct", "Group") not null 
);

CREATE TABLE Chats_Users(
    userId int NOT NULL,
    chatId int NOT NULL,
    PRIMARY KEY (userId, chatId),
    FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (chatId) REFERENCES Chats(chatId)
);

CREATE TABLE Messages(
    messageId int PRIMARY KEY AUTO_INCREMENT ,
    senderId int NOT NULL,
    chatId int NOT NULL,
    content TEXT NOT NULL,
    timeStamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    messageStatus ENUM('Sent', 'Delivered', 'Read') NOT NULL,
    attachment VARCHAR(255) NULL,
    FOREIGN KEY (senderId) REFERENCES Users(userId),
    FOREIGN KEY (chatId) REFERENCES Chats(chatId)
);

-- Groups Table
CREATE TABLE Groups(
    groupId  int PRIMARY KEY AUTO_INCREMENT ,
    groupName VARCHAR(255) NOT NULL,
    groupPhoto VARCHAR(255) NULL,
    groupDescription VARCHAR(255) NULL,
    category VARCHAR(50) NULL,
    chatId int NOT NULL,
    FOREIGN KEY (chatId) REFERENCES Chats(chatId)
);