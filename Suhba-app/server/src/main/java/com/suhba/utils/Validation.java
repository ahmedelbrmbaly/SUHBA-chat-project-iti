package com.suhba.utils;

import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.database.enums.Gender;
import com.suhba.database.enums.UserStatus;
import com.suhba.exceptions.*;

import java.util.Objects;
import java.util.regex.Pattern;

public class Validation {
    UserDAOImpl myObj = new UserDAOImpl();

    public boolean validatePhone(String phone) throws RepeatedPhoneException, InvalidPhoneException {
        if (myObj.getUserByPhone(phone) != null)  throw new RepeatedPhoneException("The phone number you entered already exists");
        if (!Pattern.matches("\\d{11}", phone))  throw new InvalidPhoneException("The phone number you entered is invalid");
        return true;
    }

    public boolean validateEmail(String email) throws RepeatedEmailException, InvalidEmailException {
        if (myObj.getUsersByEmail(email) != null)  throw new RepeatedEmailException("The email you entered already exists");
        if (!Pattern.matches("^[a-z][a-z0-9.]*@gmail\\.com", email))  throw new InvalidEmailException("The email you entered is invalid");
        return true;
    }

    public boolean validatePassword(String password) throws InvalidPasswordException {
        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\[\\]{}_\\-=+/.?~,'])[A-Za-z\\d!@#$%^&*()\\[\\]{}_\\-=+/.?~,'\\s]{8,}$", password))  throw new InvalidPasswordException("The password you entered already exists...It must contain at least: one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long");
        return true;
    }
}
