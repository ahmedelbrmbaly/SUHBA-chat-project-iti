-- Create Database with UTF-8 Support
DROP DATABASE IF EXISTS SuhbaAppDB;
CREATE DATABASE SuhbaAppDB 
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

USE SuhbaAppDB;

-- Users Table (Phone is mandatory, ENUMs fixed)
CREATE TABLE `Users`(
    `userId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `phone` VARCHAR(255) NOT NULL,
    `displayName` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `userEmail` VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    `password` VARCHAR(255) NOT NULL,
    `gender` ENUM('Male', 'Female') NULL,
    `country` VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    `birthday` DATE NULL,
    `bio` VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    `userStatus` ENUM('Available', 'Busy', 'Away', 'Offline') NOT NULL DEFAULT 'Offline'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `Users` ADD UNIQUE (`phone`);
ALTER TABLE `Users` ADD UNIQUE (`userEmail`);

-- Contacts Table
CREATE TABLE `Contacts`(
    `userId1` INT UNSIGNED NOT NULL,
    `userId2` INT UNSIGNED NOT NULL,
    `contactStatus` ENUM('Pending', 'Accepted', 'Blocked') NOT NULL DEFAULT 'Pending',
    PRIMARY KEY (`userId1`, `userId2`),
    FOREIGN KEY (`userId1`) REFERENCES `Users`(`userId`),
    FOREIGN KEY (`userId2`) REFERENCES `Users`(`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Chats Table
CREATE TABLE `Chats`(
    `chatId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `chatType` ENUM('Direct', 'Group') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Chats_Users Table
CREATE TABLE `Chats_Users`(
    `userId` INT UNSIGNED NOT NULL,
    `chatId` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`userId`, `chatId`),
    FOREIGN KEY (`userId`) REFERENCES `Users`(`userId`),
    FOREIGN KEY (`chatId`) REFERENCES `Chats`(`chatId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Messages Table
CREATE TABLE `Messages`(
    `messageId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `senderId` INT UNSIGNED NOT NULL,
    `chatId` BIGINT UNSIGNED NOT NULL,
    `content` TEXT COLLATE utf8mb4_unicode_ci NOT NULL,
    `timeStamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `messageStatus` ENUM('Sent', 'Delivered', 'Read') NOT NULL DEFAULT 'Sent',
    `attachment` VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    FOREIGN KEY (`senderId`) REFERENCES `Users`(`userId`),
    FOREIGN KEY (`chatId`) REFERENCES `Chats`(`chatId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Groups Table
CREATE TABLE `Groups`(
    `groupId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `groupName` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `groupPhoto` VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    `groupDescription` VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    `category` VARCHAR(50) COLLATE utf8mb4_unicode_ci NULL,
    `chatId` BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (`chatId`) REFERENCES `Chats`(`chatId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Admins Table
CREATE TABLE `Admins`(
    `adminId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `adminName` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `adminEmail` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `isActive` BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `Admins` ADD UNIQUE (`adminEmail`);