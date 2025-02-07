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
