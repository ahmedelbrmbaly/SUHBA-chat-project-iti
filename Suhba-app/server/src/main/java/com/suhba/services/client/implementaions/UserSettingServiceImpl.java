package com.suhba.services.client.implementaions;

import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.database.entities.User;
import com.suhba.database.enums.UserStatus;
import com.suhba.exceptions.*;
import com.suhba.services.client.interfaces.UserSettingService;
import com.suhba.utils.Hashing;
import com.suhba.utils.Validation;

import java.security.NoSuchAlgorithmException;

public class UserSettingServiceImpl implements UserSettingService {
    UserDAOImpl myUserDao;
    Validation myValidation;
    Hashing myHashing;

    public UserSettingServiceImpl() {
        this.myUserDao = new UserDAOImpl();
        this.myValidation = new Validation();
        this.myHashing = new Hashing();
    }

    @Override
    public boolean updateUserProfile(User user) throws InvalidPhoneException, RepeatedPhoneException, InvalidEmailException, RepeatedEmailException {
        if (myValidation.validatePhone((user.getPhone())))  user.setPhone(user.getPhone());
        if (myValidation.validateEmail((user.getUserEmail())))  user.setUserEmail(user.getUserEmail());

        return myUserDao.updateUserProfile(user);
    }

    @Override
    public boolean updateUserPassword(long userId, String newPassword) throws InvalidPasswordException, NoSuchAlgorithmException {
        if (myValidation.validatePassword(newPassword))  return myUserDao.updateUserPassword(userId, myHashing.doHashing(newPassword));
        return false;
    }

    @Override
    public boolean updateUserStatus(long userId, UserStatus newStatus) {
        return (myUserDao.updateUserStatus(userId, newStatus));
    }
}
