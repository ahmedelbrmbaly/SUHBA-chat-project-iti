
-- ========== Insert Dummy Data ========== --
-- Users
INSERT INTO `Users` (`phone`, `displayName`, `userEmail`, `password`, `gender`, `country`, `birthday`, `bio`, `userStatus`) 
VALUES
('+966501234567', 'ياسر', 'yasser@example.com', 'password123', 'Male', 'السعودية', '1990-05-15', 'مرحبًا! أنا ياسر.', 'Available'),
('+966502345678', 'ممدوح', 'mamduh@example.com', 'password123', 'Male', 'مصر', '1988-11-22', 'أهلاً بالجميع.', 'Busy'),
('+966503456789', 'غيداء', 'ghaida@example.com', 'password123', 'Female', 'الإمارات', '1995-03-10', 'مساء الخير 🌸', 'Available'),
('+966504567890', 'آية', 'aya@example.com', 'password123', 'Female', 'الكويت', '1993-09-05', 'صباح النور ☀️', 'Away');

-- Contacts
INSERT INTO `Contacts` (`userId1`, `userId2`, `contactStatus`) 
VALUES
(1, 2, 'Accepted'), (2, 1, 'Accepted'), -- ياسر <-> ممدوح
(1, 3, 'Accepted'), (3, 1, 'Accepted'), -- ياسر <-> غيداء
(3, 4, 'Accepted'), (4, 3, 'Accepted'); -- غيداء <-> آية

-- Chats
INSERT INTO `Chats` (`chatType`) 
VALUES ('Direct'), ('Direct'), ('Group'); -- 3 chats

-- Chats_Users
INSERT INTO `Chats_Users` (`userId`, `chatId`) 
VALUES
(1, 1), (2, 1), -- Direct chat 1 (ياسر وممدوح)
(3, 2), (4, 2), -- Direct chat 2 (غيداء وآية)
(1, 3), (2, 3), (3, 3), (4, 3); -- Group chat

-- Messages
INSERT INTO `Messages` (`senderId`, `chatId`, `content`, `messageStatus`) 
VALUES
-- Chat 1 (ياسر وممدوح)
(1, 1, 'مرحبًا ممدوح! كيف الحال؟', 'Delivered'),
(2, 1, 'أهلاً ياسر! كل شيء بخير.', 'Read'),

-- Chat 2 (غيداء وآية)
(3, 2, 'آية، هل نلتقي غدًا؟', 'Delivered'),
(4, 2, 'نعم، تمام الساعة 5 مساءً.', 'Read'),

-- Group Chat
(1, 3, 'مرحبًا جميعًا! كيف الأحوال؟', 'Delivered'),
(3, 3, 'صباح الخير 🌸', 'Delivered'),
(4, 3, 'الجميع هنا؟', 'Delivered');

-- Group
INSERT INTO `Groups` (`groupName`, `groupPhoto`, `groupDescription`, `category`, `chatId`) 
VALUES ('مجموعة الأصدقاء', '/photos/friends.jpg', 'أفضل مجموعة أصدقاء 👫', 'أصدقاء', 3);