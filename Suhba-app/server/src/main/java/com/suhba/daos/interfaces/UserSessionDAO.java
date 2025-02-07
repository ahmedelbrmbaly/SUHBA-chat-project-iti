package com.suhba.daos.interfaces;

import com.suhba.database.entities.UserSession;

public interface UserSessionDAO {
    UserSession getUserSessionByUserIdAndMacAddress(long userId, String macAddress);

    boolean getIsActiveByUserIdAndMacAddress(long userId, String macAddress);

    boolean updateIsActiveByUserIdAndMacAddress(long userId, String macAddress, boolean isActive);
}
