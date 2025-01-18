CREATE TABLE `Users`(
    `userId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `phone` VARCHAR(255) NULL,
    `displayName` VARCHAR(255) NOT NULL,
    `userEmail` VARCHAR(255) NULL,
    `password` VARCHAR(255) NOT NULL,
    `gender` ENUM('') NULL,
    `country` VARCHAR(255) NULL,
    `birthday` DATE NULL,
    `bio` VARCHAR(255) NULL,
    `userStatus` ENUM('') NOT NULL
);
ALTER TABLE
    `Users` ADD INDEX `users_userid_index`(`userId`);
ALTER TABLE
    `Users` ADD INDEX `users_phone_index`(`phone`);
ALTER TABLE
    `Users` ADD INDEX `users_useremail_index`(`userEmail`);
ALTER TABLE
    `Users` ADD UNIQUE `users_phone_unique`(`phone`);
ALTER TABLE
    `Users` ADD UNIQUE `users_useremail_unique`(`userEmail`);
CREATE TABLE `Contacts`(
    `userId1` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `userId2` BIGINT NOT NULL,
    `contactStatus` ENUM('') NOT NULL,
    PRIMARY KEY(`userId2`)
);
ALTER TABLE
    `Contacts` ADD INDEX `contacts_userid1_index`(`userId1`);
CREATE TABLE `Messages`(
    `messageId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `senderId` BIGINT NOT NULL,
    `content` MULTILINESTRING NULL,
    `timeStamp` TIMESTAMP NOT NULL,
    `messageStatus` ENUM('') NOT NULL,
    `attachment` VARCHAR(255) NULL
);
ALTER TABLE
    `Messages` ADD INDEX `messages_senderid_index`(`senderId`);
ALTER TABLE
    `Messages` ADD INDEX `messages_messageid_index`(`messageId`);
CREATE TABLE `Receivers`(
    `messageId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `receiverId` BIGINT NOT NULL,
    `groupId` BIGINT NOT NULL,
    PRIMARY KEY(`receiverId`)
);
ALTER TABLE
    `Receivers` ADD INDEX `receivers_messageid_index`(`messageId`);
ALTER TABLE
    `Receivers` ADD PRIMARY KEY(`groupId`);
CREATE TABLE `Groups`(
    `groupId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `groupName` VARCHAR(255) NOT NULL,
    `groupPhoto` VARCHAR(255) NULL,
    `groupDescription` VARCHAR(255) NULL
);
ALTER TABLE
    `Groups` ADD INDEX `groups_groupid_index`(`groupId`);
CREATE TABLE `Groups_Users`(
    `groupId` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `userId` BIGINT NOT NULL,
    `userStatus` ENUM('') NOT NULL,
    PRIMARY KEY(`userId`)
);
ALTER TABLE
    `Groups_Users` ADD INDEX `groups_users_groupid_index`(`groupId`);
ALTER TABLE
    `Groups_Users` ADD INDEX `groups_users_userid_index`(`userId`);
CREATE TABLE `Admins`(
    `adminId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `adminName` VARCHAR(255) NOT NULL,
    `adminEmail` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `isActive` BOOLEAN NOT NULL
);
ALTER TABLE
    `Admins` ADD INDEX `admins_adminid_index`(`adminId`);
ALTER TABLE
    `Admins` ADD INDEX `admins_adminemail_index`(`adminEmail`);
ALTER TABLE
    `Admins` ADD UNIQUE `admins_adminemail_unique`(`adminEmail`);
CREATE TABLE `Admins_Permissions`(
    `adminId` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `permissionId` INT NOT NULL,
    PRIMARY KEY(`permissionId`)
);
ALTER TABLE
    `Admins_Permissions` ADD INDEX `admins_permissions_adminid_index`(`adminId`);
CREATE TABLE `Permissions`(
    `permissionId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `type` ENUM('') NOT NULL
);
ALTER TABLE
    `Permissions` ADD INDEX `permissions_permissionid_index`(`permissionId`);
ALTER TABLE
    `Permissions` ADD UNIQUE `permissions_type_unique`(`type`);
ALTER TABLE
    `Contacts` ADD CONSTRAINT `contacts_userid2_foreign` FOREIGN KEY(`userId2`) REFERENCES `Users`(`userId`);
ALTER TABLE
    `Messages` ADD CONSTRAINT `messages_senderid_foreign` FOREIGN KEY(`senderId`) REFERENCES `Users`(`userId`);
ALTER TABLE
    `Groups` ADD CONSTRAINT `groups_groupid_foreign` FOREIGN KEY(`groupId`) REFERENCES `Groups_Users`(`groupId`);
ALTER TABLE
    `Messages` ADD CONSTRAINT `messages_messageid_foreign` FOREIGN KEY(`messageId`) REFERENCES `Receivers`(`messageId`);
ALTER TABLE
    `Admins` ADD CONSTRAINT `admins_adminid_foreign` FOREIGN KEY(`adminId`) REFERENCES `Admins_Permissions`(`adminId`);
ALTER TABLE
    `Contacts` ADD CONSTRAINT `contacts_userid1_foreign` FOREIGN KEY(`userId1`) REFERENCES `Users`(`userId`);
ALTER TABLE
    `Users` ADD CONSTRAINT `users_userid_foreign` FOREIGN KEY(`userId`) REFERENCES `Receivers`(`receiverId`);
ALTER TABLE
    `Users` ADD CONSTRAINT `users_userid_foreign` FOREIGN KEY(`userId`) REFERENCES `Groups_Users`(`userId`);
ALTER TABLE
    `Permissions` ADD CONSTRAINT `permissions_permissionid_foreign` FOREIGN KEY(`permissionId`) REFERENCES `Admins_Permissions`(`permissionId`);
ALTER TABLE
    `Groups` ADD CONSTRAINT `groups_groupid_foreign` FOREIGN KEY(`groupId`) REFERENCES `Receivers`(`groupId`);