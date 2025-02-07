package com.suhba.services.client.implementation;

import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.database.entities.User;
import com.suhba.services.client.interfaces.UserSettingService;

public class UserSettingServiceImpl implements UserSettingService {
    UserDAOImpl myUserDao;

    public UserSettingServiceImpl() {
        this.myUserDao = new UserDAOImpl();
    }

    @Override
    public boolean updateUserProfile(User user) {
        return myUserDao.updateUserProfile(user);
    }

    @Override
    public boolean updateUserPassword(long userId, String newPassword) {
        return myUserDao.updateUserPassword(userId, newPassword);
    }
}
