-- Users Table (Phone is mandatory, ENUMs fixed)
CREATE TABLE `Users`(
    `userId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `phone` VARCHAR(255) NOT NULL,
    `displayName` VARCHAR(255) NOT NULL,
    `userEmail` VARCHAR(255) NULL,
    `password` VARCHAR(255) NOT NULL,
    `gender` ENUM('Male', 'Female', 'Other') NULL,
    `country` VARCHAR(255) NULL,
    `birthday` DATE NULL,
    `bio` VARCHAR(255) NULL,
    `userStatus` ENUM('Available', 'Busy', 'Away', 'Offline') NOT NULL
);
ALTER TABLE `Users` ADD UNIQUE (`phone`); -- Phone is unique identifier
ALTER TABLE `Users` ADD UNIQUE (`userEmail`); -- Email uniqueness

-- Contacts Table (Composite PK, valid ENUM)
CREATE TABLE `Contacts`(
    `userId1` INT UNSIGNED NOT NULL,
    `userId2` INT UNSIGNED NOT NULL,
    `contactStatus` ENUM('Pending', 'Accepted', 'Blocked') NOT NULL,
    PRIMARY KEY (`userId1`, `userId2`),
    FOREIGN KEY (`userId1`) REFERENCES `Users`(`userId`),
    FOREIGN KEY (`userId2`) REFERENCES `Users`(`userId`)
);

-- Chats Table (Chat type definition)
CREATE TABLE `Chats`(
    `chatId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `chatType` ENUM('Direct', 'Group') NOT NULL -- Direct (1-1) or Group
);

-- Chats_Users Table (Composite PK for group chats)
CREATE TABLE `Chats_Users`(
    `userId` INT UNSIGNED NOT NULL,
    `chatId` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`userId`, `chatId`),
    FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
    FOREIGN KEY (`chatId`) REFERENCES `Chats`(`chatId`)
);

-- Messages Table (Status ENUM, formatting support)
CREATE TABLE `Messages`(
    `messageId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `senderId` INT UNSIGNED NOT NULL,
    `chatId` BIGINT UNSIGNED NOT NULL,
    `content` TEXT NOT NULL,
    `formatAttributes` JSON NULL, -- Stores font/style/color as JSON
    `timeStamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `messageStatus` ENUM('Sent', 'Delivered', 'Read') NOT NULL,
    `attachment` VARCHAR(255) NULL,
    FOREIGN KEY (`senderId`) REFERENCES `Users`(`userId`),
    FOREIGN KEY (`chatId`) REFERENCES `Chats`(`chatId`)
);

-- Groups Table (Category support)
CREATE TABLE `Groups`(
    `groupId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `groupName` VARCHAR(255) NOT NULL,
    `groupPhoto` VARCHAR(255) NULL,
    `groupDescription` VARCHAR(255) NULL,
    `category` VARCHAR(50) NULL, -- E.g., "Friends", "Family"
    `chatId` BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (`chatId`) REFERENCES `Chats`(`chatId`)
);

-- Admins Table
CREATE TABLE `Admins`(
    `adminId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `adminName` VARCHAR(255) NOT NULL,
    `adminEmail` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `isActive` BOOLEAN NOT NULL
);
ALTER TABLE `Admins` ADD UNIQUE (`adminEmail`);

